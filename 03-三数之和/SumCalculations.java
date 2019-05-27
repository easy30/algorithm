import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class SumCalculations {

	private int nSum = 10;
	
	public void func() {
		//,7,11,-13,16 
		Integer[] interger = { 2,6,9,1,4,0,-5};
		//define a list to store the sorted array items.
		List<ItemEntity> itemList = new ArrayList();
		//change the array to item entity;
		for( int i = 0; i < interger.length; i++ ) {
			itemList.add(new ItemEntity(i, interger[i]));
		}
		//sort the value - index pairs.
		itemList.sort(Comparator.naturalOrder());
		//print the sorted list.
//		 for( int i = 0; i < itemList.size(); i++ ) {
//			 System.out.println(itemList.get(i).nNum + " : " + itemList.get(i).nIndex);
//		 }
		//define a list to store the result items.
		List<ResultEntity> resultList = new ArrayList();
		List<ResultEntity> temList = new ArrayList();
		for( int i = 0; i < itemList.size(); i++ ) {
			ItemEntity item = itemList.get(i);
			if( resultList.size() == 0 ) {
				resultList.add(new ResultEntity(item));
				continue;
			}
			
			for( int j = 0; j < resultList.size(); j++) {
				ResultEntity entity = resultList.get(j);
				if( entity.getSum() + item.nNum > nSum ) {
					continue;
				} else {
					temList.add(entity.copy().setNum(item));
				}
			}
			
			if( item.getNum() < nSum ) {
				temList.add(new ResultEntity(item));
			}
			if( temList.size() > 0 ) {
				resultList.addAll(temList);
				temList.clear();
			}
		}
		//print the result array.
//		for(int i = 0; i < resultList.size(); i++ ) {
//			ResultEntity entity = resultList.get(i);
//			if( entity.nTag == 2 && entity.getSum() == nSum)
//				resultList.get(i).print();
//		}
		
		//print the result index.
//		for(int i = 0; i < resultList.size(); i++ ) {
//			ResultEntity entity = resultList.get(i);
//			if( entity.nTag == 2 && entity.getSum() == nSum)
//				resultList.get(i).printIndex();
//		}
		
		//print the sorted result index.
		for(int i = 0; i < resultList.size(); i++ ) {
			ResultEntity entity = resultList.get(i);
			if( entity.nTag == 2 && entity.getSum() == nSum)
				resultList.get(i).printSortedIndex();
		}
	}
	
	public class ItemEntity implements Comparable<ItemEntity>{

		int nIndex;
		int nNum;
		
		public ItemEntity(int index,int num) {
			nIndex = index;
			nNum = num;
		}
		
		public int getNum() {
			return nNum;
		}
		
		public int getIndex() {
			return nIndex;
		}
		
		@Override
		public int compareTo(ItemEntity o) {
			if( nNum > o.nNum)
				return 1;
			return -1;
		}
	}
	

public class ResultEntity {

	private ItemEntity nFirstNum = null;
	private ItemEntity nSecondNum = null;
	private ItemEntity nThirdNum = null;
	//identify the num set.
	// -1 no num set;
	// 0 num 1 set;
	// 1 num 2 set;
	// 2 num 3 set;
	int nTag = -1;
	
	public ResultEntity() {
	}
	public ResultEntity(ItemEntity nFirstNum) {
		this.nFirstNum = nFirstNum;
		nTag = 0;
	}
	
	public ResultEntity setNum( ItemEntity nNum) {
		switch( nTag) {
		case -1:
			nFirstNum = nNum;
			break;
		case 0:
			nSecondNum = nNum;
			break;
		case 1:
			nThirdNum = nNum;
			break;
		}
		nTag = ++nTag > 2 ? 2: nTag;
		
		return this;
	}
	
	public int getSum() {
		return (nFirstNum==null? 0 : nFirstNum.getNum()) + (nSecondNum==null? 0 :nSecondNum.getNum()) + (nThirdNum==null? 0 :nThirdNum.getNum());
	}
	
	public ResultEntity copy() {
		ResultEntity copyEntity = new ResultEntity();
		if( nTag > -1)
			copyEntity.setNum(nFirstNum);
		if( nTag > 0 )
			copyEntity.setNum(nSecondNum);
		if( nTag > 1)
			copyEntity.setNum(nThirdNum);
		return copyEntity; 
	}
	
	public void print() {
		String strResult = "NTAG = " + nTag + " : ";
		if( nTag > -1 )
			strResult += " " + nFirstNum.getNum();
		if( nTag > 0 ) 
			strResult += " " + nSecondNum.getNum();
		if( nTag > 1) 
			strResult += " " + nThirdNum.getNum();
		
		strResult += " : SUM is " + getSum();
		
		System.out.println(strResult);
	}
	
	public void printIndex() {
		if( nTag != 2 ) {
			System.out.println("ERROR WRONG num COUNT FOUND");
			return;
		}
		String strResult = "[" + nFirstNum.nIndex + " , " + nSecondNum.nIndex + " , " + nThirdNum.nIndex + "]";
		System.out.println(strResult);
	}
	
	public void printSortedIndex() {
		if( nTag != 2 ) {
			System.out.println("ERROR WRONG num COUNT FOUND");
			return;
		}
		int first = nFirstNum.nIndex;
		int second = nSecondNum.nIndex;
		int third = nThirdNum.nIndex;
		String strResult = "[";
		// swap a & b let a = the minus one.
		if( first > second ) {
			first += second;
			second = first - second;
			first = first - second;
		}
		// swap a & c let a = the minus one.
		if( first > third ) {
			first += third;
			third = first - third;
			first = first - third;
		}
		
		// swap b & c let b = the minus one.
		if( second > third ) {
			second += third;
			third = second - third;
			second = second - third;
		}
		
		strResult += "" + first + " , " + second + " , " + third;
		strResult += "]";
		System.out.println(strResult);
	}
}
}

