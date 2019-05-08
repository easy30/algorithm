function findTwo(a,target){
  if(a==null || target==null) return null;
  var map={};  
  for(var i=0;i<a.length;i++){
      two=target-a[i];
      index=map[two];
      if(index!=null){
        return [i,index];
      }
      map[a[i]]=i;
      
  }
  return null;
}