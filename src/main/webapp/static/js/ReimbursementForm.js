window.onload = ()=>{
	//baseURL = '/TRMS/';
    addNavBar();
    let xhttp = new XMLHttpRequest;
    xhttp.onreadystatechange = addEventTypes;
    //get eventTypes from the request mapper
    xhttp.open('GET', baseURL + 'eventTypes')
    xhttp.send();
console.log(baseURL);
    //in the CreateForm.html we need methods for the two drop down types: eventType and gradeFormat
    //so this is where the methods will get called when the user selects the dropdowns
    //...
    //well, to be more specific, the XMLHttpRequest is what gets called
    //from there, the eventTpe or gradeFormat method will be the response
    function addEventTypes(){
        if(xhttp.readyState == 4 && xhttp.status === 200){
        	console.log(xhttp.responseText);
            let eventTypes = JSON.parse(xhttp.responseText);
            for(let eventType of eventTypes){
                let option = document.createElement('option');
                option.text = eventType.eventType;
                option.value = eventType.id;
                let eventSelect = document.getElementById('eventType');
                eventSelect.appendChild(option);
            }
        }
    }

    xhttp2 = new XMLHttpRequest;
    xhttp2.onreadystatechange = addGradeFormat;
    xhttp2.open('GET', baseURL + 'gradeType')
    xhttp2.send();

    function addGradeFormat(){
        if(xhttp2.readyState == 4 && xhttp2.status === 200){
        	console.log(xhttp.responseText);
            let gradeTypes = JSON.parse(xhttp2.responseText);
            for(let gradeType of gradeTypes){
                let option = document.createElement('option');
                option.text = gradeType.gradeType;
                option.value = gradeType.id;
                let eventSelect = document.getElementById('gradeType');
                eventSelect.appendChild(option);
            }
        }
    }

}
