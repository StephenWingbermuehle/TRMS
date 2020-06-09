package com.revature.services;

import java.util.List;

import com.revature.beans.Employee;
import com.revature.beans.RequestForComment;
import com.revature.data.RequestForCommentDAO;
import com.revature.data.RequestForCommentOracle;

public class RequestForCommentServiceOracle implements RequestForCommentService{

	//add that DAO object and make it take on the Oracle methods
	//then import the DAO and the Oracle
	RequestForCommentDAO rfcd = new RequestForCommentOracle();
	
	@Override
	public RequestForComment addComment(RequestForComment rfc) {
		// TODO Auto-generated method stub
		return rfcd.addRequestForComment(rfc);
	}

	@Override
	public RequestForComment getRequestForComment(int id) {
		// TODO Auto-generated method stub
		return rfcd.getRequestForComment(id);
	}

	@Override
	public List<RequestForComment> getAllCommentsTo(Employee emp) {
		// TODO Auto-generated method stub
		return rfcd.getAllCommentsTo(emp);
	}

	@Override
	public void updateComment(RequestForComment rfc) {
		// TODO Auto-generated method stub
		rfcd.updateRequestForComment(rfc);
	}

}
