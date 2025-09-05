package com.bookx.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bookx.entity.Book;
import com.bookx.entity.User;
import com.bookx.exceptions.BookNotFoundException;
import com.bookx.exceptions.UserNotFoundException;
import com.bookx.repository.BookRepo;
import com.bookx.repository.UserRepo;
import com.bookx.service.BookService;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private UserRepo userRepo;
    
    @Autowired
    private BookRepo bookRepo;
    
	public Book saveBook(Integer userId, Book book) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));
        
        user.addBook(book);
        userRepo.save(user);
        return book;
	}

	public List<Book> getAllBooks() {
        return bookRepo.findAll();
	}
	
    public Optional<Book> getBookById(Integer bookId) {
        return bookRepo.findById(bookId);
    }

	public void deleteBookMethod(Book book) {
		bookRepo.delete(book);
	}

	public void deleteBookById(Integer bookId) {
        Book book = bookRepo .findById(bookId)
                .orElseThrow(() -> new BookNotFoundException("Book not found with id: " + bookId));
        
		bookRepo.delete(book);
	}

}
