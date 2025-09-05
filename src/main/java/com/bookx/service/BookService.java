package com.bookx.service;

import java.util.List;
import java.util.Optional;
import org.springframework.http.ResponseEntity;
import com.bookx.entity.Book;


public interface BookService {

    public Book saveBook(Integer userId, Book book);

    public List<Book> getAllBooks();

    public Optional<Book> getBookById(Integer bookId);

    public List<Book> findByUserId(Integer userId);

    public Book findById(Integer bookId);

    public void deleteBookMethod(Book book);

    public List<Book> getBooksByCategory(String categoryName);

    public ResponseEntity<List<Book>> getBooksByUser(Integer userId);

    public void deleteBookById(Integer bookId);

}