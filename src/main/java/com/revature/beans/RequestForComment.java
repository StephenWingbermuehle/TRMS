package com.revature.beans;

public class RequestForComment {
	private Integer id;
	private Integer requestId;
	private Integer senderId;
	private Integer receiverId;
	private String commentText;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public Integer getSenderId() {
		return senderId;
	}
	public void setSenderId(Integer senderId) {
		this.senderId = senderId;
	}
	public Integer getReceiverId() {
		return receiverId;
	}
	public void setReceiverId(Integer receiverId) {
		this.receiverId = receiverId;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	
	
	public RequestForComment(Integer id, Integer requestId, Integer senderId, Integer receiverId, String commentText) {
		super();
		this.id = id;
		this.requestId = requestId;
		this.senderId = senderId;
		this.receiverId = receiverId;
		this.commentText = commentText;
	}
	
	
	public RequestForComment() {
		super();
	}
	
	
}
