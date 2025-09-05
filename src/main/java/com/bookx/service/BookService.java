package com.bookx.service;

import java.util.List;

import com.bookx.entity.Book;

public interface BookService {

	public Book saveBook(Integer userId, Book book);
	
//	public List<Book> getAllBooks(Integer userId);
	
	public void deleteBookMethod(Book book);

	public void deleteBookById(Integer bookId);
}
