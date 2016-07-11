package com.gcit.lms.library.hbm.entities;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class TblBookGenresId {
	private int bookId;
	private int genreId;
	
	public TblBookGenresId(){
		
	}
	
	public TblBookGenresId(int bookId, int genreId){
		this.bookId = bookId;
		this.genreId = genreId;
	}

	@Column(name = "bookId", nullable = false)
	public int getBookId() {
		return bookId;
	}

	public void setBookId(int bookId) {
		this.bookId = bookId;
	}

	@Column(name = "genre_id", nullable = false)
	public int getGenreId() {
		return genreId;
	}

	public void setGenreId(int genreId) {
		this.genreId = genreId;
	}
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TblBookGenresId))
			return false;
		TblBookGenresId castOther = (TblBookGenresId) other;

		return (this.getBookId() == castOther.getBookId())
				&& (this.getGenreId() == castOther.getGenreId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getBookId();
		result = 37 * result + this.getGenreId();
		return result;
	}
}
