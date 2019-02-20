 
function getCostType(){
	if(constansItem.costType.length==0){
		$.ajax({
			type: "POST",
		    url: baseURL + "jc/feeSett/select",
	        contentType: "application/json",
		    success: function(r){
		    	if(r.code === 0){
		    		var costTypeItem = [],list = r.list;
		    		for(var i=0;i<list.length;i++){
		    			constansItem.costType.push({
		    				id:list[i].code,
		    				name:list[i].name+"("+list[i].subject+"-"+list[i].subjectName+")",
		    				subject:list[i].subject,
		    				subjectName:list[i].subjectName});
		    		}
				}else{
					alert(r.msg);
				}
			}
		});
	}
}