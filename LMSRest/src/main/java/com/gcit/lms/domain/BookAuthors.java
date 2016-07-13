package com.gcit.lms.domain;

import java.io.Serializable;

public class BookAuthors implements Serializable{
	private int authorId;
	private int bookId;
	
	public int getAuthorId() {
		return authorId;
	}
	public void setAuthorId(int authorId) {
		this.authorId = authorId;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
}
