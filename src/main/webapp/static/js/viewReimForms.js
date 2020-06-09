window.onload = () =>{
	
	console.log(employee)
	//employee is null for now when we navigate from login page to viewForms page
	getSubmittedForms();
	 console.log(baseURL+ 'ReimbursementForm');
	 //it would be nice if we remembered to call the method we create...
	 getPendingForms();
	 //oh! and maybe don't forget the Nav Bar...shameful
	 addNavBar();
	 console.log(employee + "after navbar");
}
function getSubmittedForms(){
	console.log(employee +"in getSubmitted Form")
    let table = document.getElementById('submittedForms');
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = getReimForm;
    xhttp.open('GET', baseURL + 'ReimbursementForm');
    console.log(baseURL+ 'ReimbursementForm');
    xhttp.send();
    console.log(xhttp.responseText);
    //don't modularize this because then we'd have to pass it the ...
    //the xhttp request and that gets screwy
    function getReimForm(){
        if(xhttp.readyState === 4 && xhttp.status === 200){
            let forms = xhttp.responseText;
            forms = JSON.parse(forms);
            forms.forEach(form => {
            	//the false here says this is will not get the approval button
                addTableRow(form, table, false);
            });
        }
    }

}


function addField(tr, data){
	//make a new html element for the table data
	//use createElement() method to make HTML elements
    let td = document.createElement('td');
    //set td to the data
    td.innerHTML = data;
    //add it to the table row
    tr.appendChild(td);
}


function addTableRow(form, table, fromPending){
    let tr = document.createElement('tr');
    let td;
    addField(tr, form.id);
    addField(tr, form.employee);
    //pulls EDT buuuuut who cares?
    //needs to be a new Date object
    //use toString because somehow that method will turn....
    //this: 1602339300000
    //to this: 	Sat Oct 10 2020 10:15:00 GMT-0400 (Eastern Daylight Time)
    addField(tr, new Date(form.dateAndTime).toString());
    addField(tr, form.locationPlace);
    addField(tr, form.descriptionAnecdote);
    addField(tr, form.costOfTraining);
    addField(tr, form.gradingFormat);
    addField(tr, form.eventType);
    addField(tr, form.passingGrade);
    addField(tr, form.approval);
    addField(tr, form.urgency);
    //make the button for approvals
    //but we only want to see this button on appending forms
    //so we need a boolean to see if it is for our submitted forms
    //or the forms we are approving
    
    //this is such a hack
    //if the forms from the subordinates exist...
    // then if the title is jedi knight and approval status is 1
    // or
    // if the title is jedi master and the approval status is less than 3...(1 or 2)
    // or 
    //if the title is benCo and the approval status is less than 4 (not yet fully approved)
    //THEN display the buttons
    if( fromPending == true) {
    	if ((employee.title=="jedi knight" && form.approval==1) || (employee.title=="jedi master" && form.approval < 3) || (employee.title=="benCo" && form.approval < 4)){
    		let btn = document.createElement("BUTTON");
        	btn.innerHTML = "Approve";
        	
        	//btn.onclick = "approval/"+form.id;
        	//btn.onclick = approveForm(form.id);
        	btn.addEventListener('click', function () { approveForm(form.id)});
        	tr.append(btn);
        	let rejbtn = document.createElement("BUTTON");
        	rejbtn.innerHTML = "Reject";
        	rejbtn.addEventListener('click', function () {rejectForm(form.id)});
        	tr.append(rejbtn);
    	}
//    	let btn = document.createElement("BUTTON");
//    	btn.innerHTML = "Approve";
//    	
//    	//btn.onclick = "approval/"+form.id;
//    	//btn.onclick = approveForm(form.id);
//    	btn.addEventListener('click', function () { approveForm(form.id)});
//    	tr.append(btn);
//    	let rejbtn = document.createElement("BUTTON");
//    	rejbtn.innerHTML = "Reject";
//    	rejbtn.addEventListener('click', function () {rejectForm(form.id)});
//    	tr.append(rejbtn);
    }
    
    table.appendChild(tr);
}
function approveForm(id) {
	//write approveForm fucntion in here
	console.log(employee);
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = emptyFunction;
	//'approval/ NOT PendingForms, you dope
	xhttp.open('PUT', baseURL + 'approval/' + id);
    xhttp.send();
    
    	function emptyFunction() {
    		//gotta do something with this
    		//orrrrrrr maybe not
    		//eclipse says it took the PUT request
    		
    		//let's reload the webpage though to show changes in the browser
    		if(xhttp.readyState === 4 && xhttp.status === 200) {
    			location.reload();
    		}
    	}
}

function rejectForm(id) {
	let xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = emptierFunction;
	xhttp.open('DELETE', baseURL + 'approval/' +id);
	xhttp.send();
	
		function emptierFunction() {
			if(xhttp.readyState === 4 && xhttp.status === 200) {
				location.reload();
			}
		}
}
//trying the hack
function getPendingForms(){
    let table = document.getElementById('pendingForms');
    let xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = getReimForm;
    /////////////////////
    //come back to this
    /////////////////////
    ///need a new delegate for approvals
    /////////////////////////
    //alright, new delegate is PendingForms
    //fingers crossed the SQL join statement works...
    xhttp.open('GET', baseURL + 'PendingForms');
    xhttp.send();
    
    function getReimForm(){
        if(xhttp.readyState === 4 && xhttp.status === 200){
            let forms = xhttp.responseText;
            forms = JSON.parse(forms);
            forms.forEach(form => {
            	//the true here means this will get the approval button
                //trying more of the hack
            	
            	addTableRow(form, table, true);
            });
        }
    }

}