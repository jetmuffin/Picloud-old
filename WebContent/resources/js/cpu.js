/**
 * 
 */

$(document).ready(function(){
	var url_ganglia = "http://localhost/ganglia/graph.php";
	
	var url = getDataUrl('Picloud','hour','cpu_report');
	$.getJSON(url, function(data){
        console.log(data);
});
	
	function getDataUrl(node,timestep,type){
		return url_ganglia + '?r=' + timestep + '&c=' + node + '&g=' + type + '&json=1'; 
	}
});