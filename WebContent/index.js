function showValue(value) {
    document.getElementById('tutionvalue').value = value+'$';
}

function getColleges(){
    var areaOfInterest = document.getElementById('area-of-int').value;
    var location = document.getElementById('location').value;
    var tution = document.getElementById('tution').value;

    //alert(areaOfInterest+" - "+location+" - "+tution)

    var xhttp;
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function(){
        if(this.readyState == 4 && this.status == 200){
        	var obj = JSON.parse(this.responseText);
        	var table_body = document.getElementById("table-body");
        	table_body.innerHTML = '';
        	for(var i = 0; i < obj.length ; i++){
        		
        	    var gsonbits = JSON.stringify(obj[i]);
        	    
        	    var cols = JSON.parse(gsonbits);
        	    var trnode = document.createElement("tr");
        	    var snode = document.createElement("td");
        	    var snotext = document.createTextNode(i);
        	    snode.appendChild(snotext);
        	    trnode.appendChild(snode);
        	    for(var j = 0; j < 5 ; j++){
        	    	if(j==2){
        	    		continue;
        	    	}
        	    	
            	    var tdnode = document.createElement("td");
            	    var result = Object.keys(cols)
            	    var textnode = document.createTextNode(cols[result[j]]);
            	    tdnode.appendChild(textnode);
            	    trnode.appendChild(tdnode);
            	    
        	    	
        	    }
        	    table_body.appendChild(trnode);
        	}
        	
//            document.getElementById("show-colleges").innerHTML = this.responseText;
            window.scrollTo(0, document.body.scrollHeight);
        }
    };
    xhttp.open("POST", "getcolleges", true);
    xhttp.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhttp.send("aoi="+areaOfInterest+"&loc="+location+"&tut="+tution);
}