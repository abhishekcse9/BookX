package com.bookx.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookx.entity.Book;
import com.bookx.serviceImpl.BookServiceImpl;
import com.bookx.serviceImpl.UserServiceImpl;


@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000") 
public class BookController {

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Autowired
    private UserServiceImpl userServiceImpl;

    

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookServiceImpl.getAllBooks();
        return ResponseEntity.ok(books);
    }
    
    @GetMapping("/{bookId}/image")
    public ResponseEntity<byte[]> getBookImage(@PathVariable Integer bookId) {
        Book book = bookServiceImpl.findById(bookId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(book.getImage());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable Integer bookId) {
        try {
            Book book = bookServiceImpl.getBookById(bookId)
                    .orElseThrow(() -> new RuntimeException("Book not found"));

            return ResponseEntity.ok(book);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Book>> getBooksByCategory(@PathVariable String categoryName) {
        List<Book> books = bookServiceImpl.getBooksByCategory(categoryName);
        return ResponseEntity.ok(books);
    }
    
    
    // Needs Authentication ================
	@PostMapping("/createBook/{userId}")
	public ResponseEntity<?> saveBook(
	        @PathVariable Integer userId,
	        @RequestHeader("Authorization") String authHeader,
	        @RequestParam String bookTitle,
	        @RequestParam(required = false) String originalprice,
	        @RequestParam(required = false) Double sellprice,
	        @RequestParam(required = false) String publication,
	        @RequestParam(required = false) String edition,
	        @RequestParam(required = false) String condition,
	        @RequestParam(required = false) String category,
	        @RequestParam(required = false) String location,
	        @RequestParam("image") MultipartFile imageFile) throws Exception {
	
	    ResponseEntity<?> validationResponse = userServiceImpl.validateUserAccess(authHeader, userId);
	    if (validationResponse != null) {
	        return validationResponse;
	    }
	
	    Book book = new Book();
	    book.setBookTitle(bookTitle);
	    book.setOriginalPrice(originalprice);
	    book.setSellPrice(sellprice);
	    book.setPublication(publication);
	    book.setEdition(edition);
	    book.setCondition(condition);
	    book.setCategory(category);
	    book.setLocation(location);
	    book.setImage(imageFile.getBytes());
	
	    Book savedBook = bookServiceImpl.saveBook(userId, book);
	    return ResponseEntity.status(HttpStatus.CREATED).body(savedBook);
	}



	@GetMapping("/user/{userId}")
	public ResponseEntity<?> getBooksByUser(
	        @PathVariable Integer userId,
	        @RequestHeader("Authorization") String authHeader) {

	    ResponseEntity<?> validationResponse = userServiceImpl.validateUserAccess(authHeader, userId);
	    if (validationResponse != null) {
	        return validationResponse;
	    }

	    List<Book> books = bookServiceImpl.findByUserId(userId);
	    return ResponseEntity.ok(books);
	}

	@DeleteMapping("/{bookId}")
	public ResponseEntity<?> deleteBookById(
	        @PathVariable Integer bookId,
	        @RequestHeader("Authorization") String authHeader) {

	    Book book = bookServiceImpl.findById(bookId);

	    ResponseEntity<?> validationResponse = userServiceImpl.validateUserAccess(authHeader, book.getUser().getId());
	    if (validationResponse != null) {
	        return validationResponse;
	    }

	    bookServiceImpl.deleteBookById(bookId);
	    return ResponseEntity.status(HttpStatus.OK).body(Map.of(
	    	    "status", HttpStatus.OK.value(),
	    	    "message", "Book with ID " + bookId + " deleted successfully"
	    	));
	}

}



