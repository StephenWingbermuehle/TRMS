# TRMS
Tuition Reimbursement Management System - this Java project links an Oracle DB with some front end code
so that I could make a system where employees are able to submit job-relevant expenses to their 
corresponding supervisor, department head, and HR manager in order to have expenses approved/rejected. 
Employees can login, submit forms, view their pending forms, and (if they are a supervisor) view forms pending their approval
to be accepted/rejected.

I added a Star Wars theme to it for my own amusement and it has nothing to do with any of the logic in the code.
-Approving/Rejected a reimbursment request is based on supervisory authority level, department matching, and passing grade requirements
-Employees are permitted $1000 in reimbursments annually by default.
-Multiple requests can be submitted at a time while other requests are pending.
-There is a reimbursment scale depending on the type of reimbursemnt (i.e. tuition, a seminar, licensing, etc.)
