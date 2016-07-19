package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

@SuppressWarnings("unchecked")
public class AuthorDAO extends BaseDAO implements ResultSetExtractor<List<Author>>{

	public void insertAuthor(Author author) throws ClassNotFoundException, SQLException{
		template.update("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException{
		template.update("delete from tbl_author where authorId=?", new Object[] {author.getAuthorId()});
	}
	
	public void deleteAll() throws ClassNotFoundException, SQLException{
		template.update("delete * from tbl_author");
	}
	
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
		template.update("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}
	
	public List<Author> readAuthorsByBook(int pageNo, int bookId) throws ClassNotFoundException, SQLException{
		String query = "select * from tbl_author where authorId in (select authorId from tbl_book_authors where bookId = ?)";
		if(pageNo > 0){
			int index = (pageNo-1)*10;
			query += " LIMIT "+index+" , "+getPageSize();
		};
		List<Author> authors = template.query(query, new Object[]{bookId}, this);
		if( authors.isEmpty() ){
			Author a = new Author();
			a.setAuthorName("N/A");
			authors.add(a);
		}
		return authors;
	}
	
	public List<Author> readAll(int pageNo) throws ClassNotFoundException, SQLException{
		String query = "select * from tbl_author";
		if(pageNo > 0){
			int index = (pageNo-1)*10;
			query += " LIMIT "+index+" , "+getPageSize();
		};
		return template.query(query, this);
	}
	
//	public List<Author> readAllFirstLevel(int pageNo) throws ClassNotFoundException, SQLException{
//		setPageNo(pageNo);
//		return template.query("select * from tbl_author", this);
//	}
	
	public Author readOne(Author author) throws ClassNotFoundException, SQLException{
		List<Author> authors = template.query("select * from tbl_author where authorId =?", new Object[] {author.getAuthorId()}, this);
		for(Author a: authors){
			return a;
		}
		return null;
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		return template.queryForObject("select count(*) as count from tbl_author", Integer.class);
	}
	
	public List<Author> readBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		if( searchString.equals("") )
			searchString = "%%";
		else
			searchString = "%" + searchString + "%";
		String query = "select * from tbl_author where authorName like ?";
		if(pageNo > 0){
			int index = (pageNo-1)*10;
			query += " LIMIT "+index+" , "+getPageSize();
		}
		return template.query(query, new Object[] {searchString}, this);
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			
			authors.add(a);
		}
		return authors;
	}
}
