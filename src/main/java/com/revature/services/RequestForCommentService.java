package com.revature.services;

import java.util.List;

import com.revature.beans.Employee;
import com.revature.beans.RequestForComment;

public interface RequestForCommentService {
	public RequestForComment addComment(RequestForComment rfc);
	public RequestForComment getRequestForComment(int id);
	public List<RequestForComment> getAllCommentsTo(Employee emp);
	public void updateComment(RequestForComment rfc);
}
