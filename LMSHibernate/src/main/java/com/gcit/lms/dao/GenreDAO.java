package com.gcit.lms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.Genre;

public class GenreDAO extends BaseDAO implements ResultSetExtractor<List<Genre>>{
	
	public void insertGenre(Genre g) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_genre (genre_name) values (?)", new Object[] {g.getGenre_name()});
	}
	
	public List<Genre> readAll() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_genre", this);
	}
	
	public List<Genre> readGenresByBook(int bookId) throws ClassNotFoundException, SQLException{
		List<Genre> genres = template.query("select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = ?)", new Object[]{bookId}, this);
		if( genres.isEmpty() ){
			Genre g = new Genre();
			g.setGenre_name("N/A");
			genres.add(g);
		}
		return genres;
	}
	
	public List<Genre> readAllFirstLevel() throws ClassNotFoundException, SQLException{
		return template.query("select * from tbl_genre", this);
	}

	@Override
	public List<Genre> extractData(ResultSet rs) throws SQLException {
		List<Genre> genres = new ArrayList<Genre>();
		while(rs.next()){
			Genre g = new Genre();
			g.setGenre_id(rs.getInt("genre_id"));
			g.setGenre_name(rs.getString("genre_name"));
			genres.add(g);
		}
		return genres;
	}
}
