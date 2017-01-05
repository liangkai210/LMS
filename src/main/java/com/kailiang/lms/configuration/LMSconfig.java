package com.kailiang.lms.configuration;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.kailiang.lms.dao.AuthorDao;
import com.kailiang.lms.dao.BookAuthorsDao;
import com.kailiang.lms.dao.BookCopiesDao;
import com.kailiang.lms.dao.BookDao;
import com.kailiang.lms.dao.BookGenresDao;
import com.kailiang.lms.dao.BookLoansDao;
import com.kailiang.lms.dao.BorrowerDao;
import com.kailiang.lms.dao.BranchDao;
import com.kailiang.lms.dao.GenreDao;
import com.kailiang.lms.dao.PublisherDao;
import com.kailiang.lms.service.AuthorService;
import com.kailiang.lms.service.BookAutService;
import com.kailiang.lms.service.BookCopyService;
import com.kailiang.lms.service.BookGenService;
import com.kailiang.lms.service.BookLoanService;
import com.kailiang.lms.service.BookService;
import com.kailiang.lms.service.BorService;
import com.kailiang.lms.service.GenreService;
import com.kailiang.lms.service.PublisherService;

@Configuration
@EnableTransactionManagement
public class LMSconfig {

	private String driverName = "com.mysql.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/library";
	private String uname = "root";
	private String pwd = "1234";

	public BasicDataSource dataSource() {

		BasicDataSource ds = new BasicDataSource();
		ds.setDriverClassName(driverName);
		ds.setUrl(url);
		ds.setUsername(uname);
		ds.setPassword(pwd);

		return ds;
	}

	@Bean
	public JdbcTemplate template() {
		JdbcTemplate template = new JdbcTemplate();
		template.setDataSource(dataSource());

		return template;
	}

	@Bean
	public PlatformTransactionManager txManager() {
		DataSourceTransactionManager txManager = new DataSourceTransactionManager();
		txManager.setDataSource(dataSource());

		return txManager;
	}

	@Bean
	public AuthorDao authorDao() {
		return new AuthorDao();
	}

	@Bean
	public BookDao bookDao() {
		return new BookDao();
	}

	@Bean
	public BookAuthorsDao bADao() {
		return new BookAuthorsDao();
	}

	@Bean
	public BookCopiesDao bCDao() {
		return new BookCopiesDao();
	}

	@Bean
	public BookGenresDao bGDao() {
		return new BookGenresDao();
	}

	@Bean
	public BookLoansDao bLDao() {
		return new BookLoansDao();
	}

	@Bean
	public BorrowerDao borDao() {
		return new BorrowerDao();
	}

	@Bean
	public BranchDao branchDao() {
		return new BranchDao();
	}

	@Bean
	public GenreDao genreDao() {
		return new GenreDao();
	}

	@Bean
	public PublisherDao publisherDao() {
		return new PublisherDao();
	}

	@Bean
	public BookService bookService() {
		return new BookService();
	}

	@Bean
	public PublisherService publisherService() {
		return new PublisherService();
	}

	@Bean
	public AuthorService authorService() {
		return new AuthorService();
	}

	@Bean
	public GenreService genreService() {
		return new GenreService();
	}

	@Bean
	public BorService borService() {
		return new BorService();
	}

	@Bean
	public BookAutService bookAutService() {
		return new BookAutService();
	}

	@Bean
	public BookCopyService bookCopyService() {
		return new BookCopyService();
	}

	@Bean
	public BookLoanService bookLoanService() {
		return new BookLoanService();
	}
	@Bean
	public BookGenService bookGenService() {
		return new BookGenService();
	}

}
