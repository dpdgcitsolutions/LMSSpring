package com.gcit.lms.library.hbm.entities;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbl_book_genres", catalog = "library")
public class TblBookGenres {
	private TblBookGenresId id;
	
}
