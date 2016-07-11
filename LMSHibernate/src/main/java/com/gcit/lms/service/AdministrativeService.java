package com.gcit.lms.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.gcit.lms.dao.AuthorDAO;
import com.gcit.lms.dao.BookAuthorsDAO;
import com.gcit.lms.dao.BookCopiesDAO;
import com.gcit.lms.dao.BookDAO;
import com.gcit.lms.dao.BookGenresDAO;
import com.gcit.lms.dao.BorrowerDAO;
import com.gcit.lms.dao.GenreDAO;
import com.gcit.lms.dao.LibraryBranchDAO;
import com.gcit.lms.dao.PublisherDAO;
import com.gcit.lms.domain.Author;
import com.gcit.lms.domain.Book;
import com.gcit.lms.domain.BookCopies;
import com.gcit.lms.domain.Borrower;
import com.gcit.lms.domain.Genre;
import com.gcit.lms.domain.LibraryBranch;
import com.gcit.lms.domain.Publisher;
import com.gcit.lms.library.hbm.entities.TblAuthor;
import com.gcit.lms.library.hbm.entities.TblBook;
import com.gcit.lms.library.hbm.entities.TblBookLoans;
import com.gcit.lms.library.hbm.entities.TblBorrower;
import com.gcit.lms.library.hbm.entities.TblGenre;
import com.gcit.lms.library.hbm.entities.TblPublisher;

import javassist.bytecode.Descriptor.Iterator;


public class AdministrativeService {
	
	@Autowired
	AuthorDAO adao;
	
	@Autowired
	BookDAO bdao;
	
	@Autowired
	PublisherDAO pdao;
	
	@Autowired
	GenreDAO gdao;
	
	@Autowired
	BorrowerDAO bodao;
	
	@Autowired
	BookAuthorsDAO badao;
	
	@Autowired
	BookGenresDAO bgdao;
	
	@Autowired
	BookCopiesDAO bcdao;
	
	@Autowired
	LibraryBranchDAO ldao;
	
	@Transactional
	public void createAuthor(Author author) throws ClassNotFoundException, SQLException{
		//adao.insertAuthor(author);
		Session session = getSession();
		TblAuthor a = new TblAuthor(author.getAuthorName());
		Transaction tx = session.beginTransaction();
		session.save(a);
		tx.commit();
		session.close();
	}
	
	@Transactional
	public void createBook(Book book) throws ClassNotFoundException, SQLException{
//		Integer bookId = bdao.saveBookWithID(book);
//		book.setBookId(bookId);
//		badao.insertBookAuthors(book);
//		bgdao.insertBookGenres(book);
		Session session = getSession();
		TblBook b = new TblBook(book.getTitle());
		TblPublisher p = new TblPublisher();
		p.setPublisherId(book.getPublisher().getPublisherId());
		b.setTblPublisher(p);
		Transaction tx = session.beginTransaction();
		Integer bookId = (Integer) session.save(b);
		Set<TblAuthor> authors = new HashSet<TblAuthor>();
		for( Author a : book.getAuthors() ){
			TblAuthor au = new TblAuthor(a.getAuthorId());
			authors.add(au);
		}
		b.setTblAuthors(authors);
		Set<TblGenre> genres = new HashSet<TblGenre>();
		for( Genre g : book.getGenres() ){
			TblGenre ge = new TblGenre(g.getGenre_id());
			genres.add(ge);
		}
		b.setTblGenres(genres);
		session.save(b);
		tx.commit();
		session.close();
	}
	
	@Transactional
	public void createBorrower(Borrower bo) throws SQLException, ClassNotFoundException{
		Session session = getSession();
		Set<TblBookLoans> bl = new HashSet<TblBookLoans>();
		TblBorrower b = new TblBorrower(bo.getName(), bo.getAddress(), bo.getPhone(), bl);
		Transaction tx = session.beginTransaction();
		session.save(b);
		tx.commit();
		session.close();
		//bodao.insertBorrower(bo);
	}
	
	@Transactional
	public void editBorrower(Borrower bo) throws ClassNotFoundException, SQLException{
		Session session = getSession();
		TblBorrower b = new TblBorrower();
		Transaction tx = session.beginTransaction();
		b = (TblBorrower) session.get("TblBorrower", bo.getCardNo());
		b.setName(bo.getName());
		b.setAddress(bo.getAddress());
		b.setPhone(bo.getPhone());
		tx.commit();
		session.close();
		//bodao.updateBorrower(bo);
	}
	
	@Transactional
	public void editAuthor(Author author) throws ClassNotFoundException, SQLException{
		//adao.updateAuthor(author);
		Session session = getSession();
		TblAuthor a = new TblAuthor();
		Transaction tx = session.beginTransaction();
		a = (TblAuthor) session.get("TblAuthor", author.getAuthorId());
		a.setAuthorName(author.getAuthorName());
		tx.commit();
		session.close();
	}
	
	@Transactional
	public void editBook(Book b) throws ClassNotFoundException, SQLException {
		Session session = getSession();
		TblBook book = new TblBook();
		Transaction tx = session.beginTransaction();
		book = (TblBook) session.get("TblBook", b.getBookId());
		book.setTitle(b.getTitle());
//		TblPublisher p = new TblPublisher(b.getPublisher().getPublisherId());
//		book.setTblPublisher(p);
//		Set<TblAuthor> authors = new HashSet<TblAuthor>();
//		for( Author a : b.getAuthors() ){
//			TblAuthor au = new TblAuthor(a.getAuthorId());
//			authors.add(au);
//		}
//		book.setTblAuthors(authors);
//		Set<TblGenre> genres = new HashSet<TblGenre>();
//		for( Genre g : b.getGenres() ){
//			TblGenre ge = new TblGenre(g.getGenre_id());
//			genres.add(ge);
//		}
//		book.setTblGenres(genres);
		tx.commit();
		session.close();
//		bdao.updateBook(b);
//		badao.deleteBookAuthors(b);
//		badao.insertBookAuthors(b);
//		bgdao.deleteBookGenres(b);
//		bgdao.insertBookGenres(b);
	}
	
	public List<Author> viewAuthors(int pageNo) throws ClassNotFoundException, SQLException{
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		String sql = "SELECT * FROM tbl_author";
		SQLQuery query = session.createSQLQuery(sql);
		if( pageNo != 0 ){
			query.setFirstResult((pageNo-1)*10);
			query.setMaxResults(10);
		}
		List<TblAuthor> authors = query.addEntity(TblAuthor.class).list();
		tx.commit();
		session.close();
		List<Author> as = new ArrayList<Author>();
		for( int i = 0; i < authors.size(); i++ ){
			Author a = new Author();
			a.setAuthorName(authors.get(i).getAuthorName());
			a.setAuthorId(authors.get(i).getAuthorId());
			as.add(a);
		}
		return as;
		//return adao.readAll(pageNo);
	}
	
	public List<Author> viewAuthorsByBook(int pageNo, int bookId) throws ClassNotFoundException, SQLException{
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		String sql = "select * from tbl_author where authorId in (select bookId from tbl_book_authors where bookId = :bookId)";
		SQLQuery query = session.createSQLQuery(sql).addEntity(TblAuthor.class);
		query.setParameter("bookId", bookId);
		if( pageNo != 0 ){
			query.setFirstResult((pageNo-1)*10);
			query.setMaxResults(10);
		}
		List<TblAuthor> authors = query.list();
		tx.commit();
		session.close();
		List<Author> as = new ArrayList<Author>();
		if( authors.isEmpty() ){
			Author a = new Author();
			a.setAuthorName("N/A");
			as.add(a);
		}
		else{
			for( int i = 0; i < authors.size(); i++ ){
				Author a = new Author();
				a.setAuthorName(authors.get(i).getAuthorName());
				a.setAuthorId(authors.get(i).getAuthorId());
				as.add(a);
			}
		}
		return as;
		//return adao.readAuthorsByBook(0, bookId);
	}
	
	public List<Genre> viewGenresByBook(int pageNo, int bookId) throws ClassNotFoundException, SQLException{
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		String sql = "select * from tbl_genre where genre_id in (select genre_id from tbl_book_genres where bookId = :bookId)";
		SQLQuery query = session.createSQLQuery(sql).addEntity(TblGenre.class);
		query.setParameter("bookId", bookId);
		if( pageNo != 0 ){
			query.setFirstResult((pageNo-1)*10);
			query.setMaxResults(10);
		}
		List<TblGenre> genres = query.list();
		tx.commit();
		session.close();
		List<Genre> gs = new ArrayList<Genre>();
		if( genres.isEmpty() ){
			Genre g = new Genre();
			g.setGenre_name("N/A");
			gs.add(g);
		}
		else{
			for( int i = 0; i < genres.size(); i++ ){
				Genre g = new Genre();
				g.setGenre_name(genres.get(i).getGenreName());
				gs.add(g);
			}
		}
		return gs;
		//return gdao.readGenresByBook(bookId);
	}
	
	public List<Publisher> viewPublishersByBook(int pageNo, int bookId) throws ClassNotFoundException, SQLException{
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		String sql = "select * from tbl_publisher where publisherId in (select pubId from tbl_book where bookId = :bookId)";
		SQLQuery query = session.createSQLQuery(sql).addEntity(TblPublisher.class);
		query.setParameter("bookId", bookId);
		if( pageNo != 0 ){
			query.setFirstResult((pageNo-1)*10);
			query.setMaxResults(10);
		}
		List<TblPublisher> pubs = query.list();
		tx.commit();
		session.close();
		List<Publisher> ps = new ArrayList<Publisher>();
		if( pubs.isEmpty() ){
			Publisher p = new Publisher();
			p.setPublisherName("N/A");
			ps.add(p);
		}
		else{
			for( int i = 0; i < pubs.size(); i++ ){
				Publisher p = new Publisher();
				p.setPublisherName(pubs.get(i).getPublisherName());
				ps.add(p);
			}
		}
		return ps;
		//return pdao.readPublisherByBook(bookId);
	}
	
	public List<Book> viewBooks(int pageNo) throws ClassNotFoundException, SQLException{
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		String sql = "Select * from tbl_book";
		SQLQuery query = session.createSQLQuery(sql);
		if( pageNo != 0 ){
			query.setFirstResult((pageNo-1)*10);
			query.setMaxResults(10);
		}
		List<TblBook> books = query.addEntity(TblBook.class).list();
		tx.commit();
		session.close();
		List<Book> bks = new ArrayList<Book>();
		for( TblBook b : books ){
			Book bk = new Book();
			bk.setTitle(b.getTitle());
			bk.setBookId(b.getBookId());
			Publisher p = new Publisher();
			p.setPublisherId(b.getTblPublisher().getPublisherId());
			bk.setPublisher(p);
			bks.add(bk);
		}
		return bks;
		//return bdao.readAll(pageNo);
	}	
	
	public List<Book> viewBooksBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException {
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		if( searchString.equals("") )
			searchString = "%%";
		else
			searchString = "%" + searchString + "%";
		String sql = "Select * from tbl_book where title like :name";
		SQLQuery query = session.createSQLQuery(sql).addEntity(TblBook.class);
		query.setParameter("name", searchString);
		if( pageNo != 0 ){
			query.setFirstResult((pageNo-1)*10);
			query.setMaxResults(10);
		}
		List<TblBook> books = query.list();
		tx.commit();
		session.close();
		List<Book> bks = new ArrayList<Book>();
		for( TblBook b : books ){
			Book bk = new Book();
			bk.setTitle(b.getTitle());
			bk.setBookId(b.getBookId());
			Publisher p = new Publisher();
			p.setPublisherId(b.getTblPublisher().getPublisherId());
			bk.setPublisher(p);
			bks.add(bk);
		}
		return bks;
		//return bdao.readBySearchString(searchString, pageNo);
	}
	
	public List<Author> viewAuthorsBySearchString(String searchString, int pageNo) throws ClassNotFoundException, SQLException{
		Session session = getSession();
		Transaction tx = session.beginTransaction();
		if( searchString.equals("") )
			searchString = "%%";
		else
			searchString = "%" + searchString + "%";
		String sql = "select * from tbl_author where authorName like :name";
		SQLQuery query = session.createSQLQuery(sql).addEntity(TblAuthor.class);
		query.setParameter("name", searchString);
		if( pageNo != 0 ){
			query.setFirstResult((pageNo-1)*10);
			query.setMaxResults(10);
		}
		List<TblAuthor> authors = query.list();
		tx.commit();
		session.close();
		List<Author> as = new ArrayList<Author>();
		for( int i = 0; i < authors.size(); i++ ){
			Author a = new Author();
			a.setAuthorName(authors.get(i).getAuthorName());
			a.setAuthorId(authors.get(i).getAuthorId());
			as.add(a);
		}
		return as;
		//return adao.readBySearchString(searchString, pageNo);
	}
	
	@Transactional
	public void createGenre(Genre g) throws ClassNotFoundException, SQLException{
		gdao.insertGenre(g);
	}
	
	@Transactional
	public void createPublisher(Publisher p) throws ClassNotFoundException, SQLException{
		pdao.insertPublisher(p);
	}
	
	@Transactional
	public void editBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		bcdao.updateBookCopies(bc);			
	}
	
	@Transactional
	public void createBookCopies(BookCopies bc) throws ClassNotFoundException, SQLException{
		bcdao.insertBookCopies(bc);			
	}
	
	@Transactional
	public void editBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		ldao.updateLibraryBranch(lib);
	}

	@Transactional
	public void createLibraryBranch(LibraryBranch lib) throws ClassNotFoundException, SQLException{
		ldao.insertLibraryBranch(lib);
	}
	
	public List<LibraryBranch> viewLibraryBranch() throws ClassNotFoundException, SQLException{
		return ldao.readAll();
	}
	
	public BookCopies viewBookCopiesByID(Integer bookId, Integer branchId) throws ClassNotFoundException, SQLException{
		BookCopies bc = new BookCopies();
		bc.setBookId(bookId);
		bc.setBranchId(branchId);
		return bcdao.readOne(bc);
	}
	
	public Book viewBookByID(Integer bookId) throws ClassNotFoundException, SQLException{
		Book b = new Book();
		b.setBookId(bookId);
		return bdao.readOne(b);
	}
	
	public List<Book> viewBooksNotInBranch(int branchId) throws ClassNotFoundException, SQLException{
		return bdao.readBooksNotInBranch(branchId);
	}
	
	public List<Book> viewBooksByAuthor(int pageNo, int authorId) throws ClassNotFoundException, SQLException{
		return bdao.readBooksByAuthor(pageNo, authorId);
	}
	
	public List<Book> viewBooksByBranch(int pageNo, int branchId) throws ClassNotFoundException, SQLException{
		return bdao.readBooksByBranch(pageNo, branchId);
	}
	
	public List<Publisher> viewPublishers() throws ClassNotFoundException, SQLException{
		return pdao.readAll();	
	}
	
	public List<Genre> viewGenres() throws ClassNotFoundException, SQLException{
		return gdao.readAll();	
	}
	
//	public List<Genre> viewGenresFirstLevel() throws ClassNotFoundException, SQLException{
//		return gdao.readAllFirstLevel();	
//	}
	
	public List<Borrower> viewBorrowers() throws ClassNotFoundException, SQLException{
		return bodao.readAll();
	}	
	
//	public List<Author> viewAuthorsFirstLevel(int pageNo) throws ClassNotFoundException, SQLException{
//		return adao.readAllFirstLevel(pageNo);
//	}
	
	public Integer getAuthorsCount() throws ClassNotFoundException, SQLException{
		return adao.getCount();
	}
	
	public Integer getBooksCount() throws ClassNotFoundException, SQLException{
		return bdao.getCount();
	}
	
	public Author viewAuthorByID(Integer authorID) throws ClassNotFoundException, SQLException{
		Author a = new Author();
		a.setAuthorId(authorID);
		return adao.readOne(a);
	}
	
	public LibraryBranch viewBranchById(Integer branchID) throws ClassNotFoundException, SQLException {
		LibraryBranch l = new LibraryBranch();
		l.setBranchId(branchID);
		return ldao.readOne(l);
	}
	
	public Session getSession() {
		Configuration configuration = new Configuration();
		configuration.configure("hibernate.cfg.xml");
		StandardServiceRegistryBuilder ssrb = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties());

		SessionFactory sessionFactory = configuration.buildSessionFactory(ssrb.build());
		Session session = sessionFactory.openSession();
		return session;
	}
}
