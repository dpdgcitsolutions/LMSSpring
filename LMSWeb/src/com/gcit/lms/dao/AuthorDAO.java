package com.gcit.lms.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;

@SuppressWarnings("unchecked")
public class AuthorDAO extends BaseDAO{

	public AuthorDAO(Connection conn) {
		super(conn);
	}

	public void insertAuthor(Author author) throws ClassNotFoundException, SQLException{
		save("insert into tbl_author (authorName) values (?)", new Object[] {author.getAuthorName()});
	}
	
	public void deleteAuthor(Author author) throws ClassNotFoundException, SQLException{
		save("delete from tbl_author where authorId=?", new Object[] {author.getAuthorId()});
	}
	
	public void deleteAll() throws ClassNotFoundException, SQLException{
		save("delete * from tbl_author", null);
	}
	
	public void updateAuthor(Author author) throws ClassNotFoundException, SQLException{
		save("update tbl_author set authorName = ? where authorId = ?", new Object[] {author.getAuthorName(), author.getAuthorId()});
	}
	
	
	public List<Author> readAll(int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return read("select * from tbl_author", null);
	}
	
	public List<Author> readAllFirstLevel(int pageNo) throws ClassNotFoundException, SQLException{
		setPageNo(pageNo);
		return readFirstLevel("select * from tbl_author", null);
	}
	
	public Author readOne(Author author) throws ClassNotFoundException, SQLException{
		List<Author> authors = read("select * from tbl_author where authorId =?", new Object[] {author.getAuthorId()});
		for(Author a: authors){
			return a;
		}
		return null;
	}
	
	public Integer getCount() throws ClassNotFoundException, SQLException{
		return readCount("select count(*) as count from tbl_author", null);
	}
	
	public List<Author> readBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException {
		setPageNo(pageNo);
		if( searchString.equals("") )
			searchString = "%%";
		else
			searchString = "%" + searchString + "%";
		return read("select * from tbl_author where authorName like ?", new Object[] {searchString});
	}

	@Override
	public List<Author> extractData(ResultSet rs) throws SQLException {
		List<Author> authors = new ArrayList<Author>();
		BookDAO bdao = new BookDAO(connection);
		while(rs.next()){
			Author a = new Author();
			a.setAuthorId(rs.getInt("authorId"));
			a.setAuthorName(rs.getString("authorName"));
			try {
				List<Book> books = bdao.readFirstLevel("select * from tbl_book where bookId IN(select bookId from tbl_book_authors where authorId = ?)", new Object[]{a.getAuthorId()});
				if( books.isEmpty() ){
					Book b = new Book();
					b.setTitle("N/A");
					books.add(b);
				}
				a.setBooks(books);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			authors.add(a);
		}
		return authors;
	}

	@Override
	public List<?> extractDataFirstLevel(ResultSet rs) throws SQLException {
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
