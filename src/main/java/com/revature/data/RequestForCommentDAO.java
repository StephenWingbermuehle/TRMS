package com.revature.data;

import java.util.List;

import com.revature.beans.Employee;
import com.revature.beans.RequestForComment;

public interface RequestForCommentDAO {
	
	public RequestForComment addRequestForComment(RequestForComment requestForComment);
	
	public RequestForComment getRequestForComment(Integer id);
	
	public void updateRequestForComment(RequestForComment requestForComment);

	List<RequestForComment> getAllCommentsTo(Employee emp);
}
