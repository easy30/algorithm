const { once } = require('events');
const readline = require('readline');
const fs = require('fs');
const os = require('os');

// cpu一个核心的线程数。
let cpuSize = os.cpus().length;

const {
  Worker, isMainThread, parentPort, workerData
} = require('worker_threads');



// 子线程任务，
if (!isMainThread){
	parentPort.on('message',(page)=>{
		if (page.verticalLine < 0 ){
			// 子任务结束。
			parentPort.close();
			return ;
		}
		var array =  Array();
		for (var i = page.indexPage * page.sizePage ;  
			i <  (page.indexPage + 1 ) * page.sizePage 
			&& i < workerData.datas.length; i++ ) {
			if (i == 0 ){
				continue;
			}
			let row =  workerData.datas[i];
			if (row[page.verticalLine] == workerData.fieldValue){ array.push(row); }
		}
		// 发送数据
		parentPort.postMessage(array);
		// 子任务结束。
		parentPort.close();
	});
	// 子任务 关闭
	parentPort.on('close',()=>{
		// console.log('child_thread close');
	});
	return ;
}

main('cphone','13618106855');



/// 主方法
function main(fieldName,fieldValue){
	let filePath = "/Users/m/Documents/GitHub/algorithm/04-CSV查找/sample.csv";
	if (!fieldName || !fieldValue){
		return 
	}
	var callback = (result) => {
		console.log(result);
	};

	readCSVToArray(filePath ,(datas)=>{
		startWorker(datas, fieldName, fieldValue,callback)
	});
}
 

/// 开启子任务。
function startWorker(datas, fieldName, fieldValue,callback){
	let sizePage = parseInt(datas.length / cpuSize) + (datas.length % cpuSize == 0 ? 0 : 1);
	let verticalLine = -1 ;
	let headerArray = datas[0]
	for (var i = 0; i <= headerArray.length; i++) {
		if (headerArray[i] == fieldName){
			verticalLine = i;
			break;
		}
	}
	console.log("header:::::::" , headerArray.toString());

	var promises =  Array();
	for (var i = 0; i < cpuSize ; i++) {
		let promise = new Promise((resolve, reject) => {
			let worker = new Worker(__filename,{
				workerData: {
					datas: datas,
					fieldName: fieldName,
					fieldValue: fieldValue
				}
			});
			// 绑定监听子任务消息。
			worker.on('message',(resultDatas)=>{
				// console.log(result.toString());
				resolve(resultDatas);

			}).on('exit',(exitCode)=>{ /* console.log("worker exit",exitCode);  */ });

         	// reject(error);
         	// 开始子任务运行。
			worker.postMessage({
				indexPage: i,
				sizePage: sizePage,
				verticalLine: verticalLine
			});
        });
        promises.push(promise);
	}
	
	Promise.all(promises).then(function (result) {
		// 聚合结果数据。
		var datas = Array();
		for (var i = 0; i < result.length; i++) {
			result[i].forEach((data)=>{
				datas.push(data);
			});
		}
		// console.log(datas);
		callback(datas);
	});

}


// 读文件。
async function readCSVToArray(filePath,callback){	
	// 数据
	var datas = Array();
	// 打开流
	const stream = fs.createReadStream(filePath,{
		encoding:'utf8',
		crlfDelay: Infinity
	});
	// 读行
	const rl = readline.createInterface({
	  input: stream
	});
	// 每读一行的事件。
	rl.on('line',(line) =>{
		let array = line.split(',');
		datas.push(array);
	}).on('close',()=>{
		if (callback) {
			callback(datas);
		}
		
	});
	// await once(rl, 'line');
	// console.log(datas);
}





