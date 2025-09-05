package com.bookx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bookx.entity.Book;
import com.bookx.repository.BookRepo;
import com.bookx.serviceImpl.BookServiceImpl;


@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:3000") 
@PreAuthorize("isAuthenticated()") 
public class BookController {

    @Autowired
    private BookServiceImpl bookServiceImpl;

    @Autowired
    private BookRepo bookRepo;
    
    
    @PostMapping("/createBook/{userId}")
    @PreAuthorize("isAuthenticated() and #userId == authentication.principal.id")
    public ResponseEntity<Book> saveBook(
            @PathVariable Integer userId,
            @RequestParam String bookTitle,
            @RequestParam(required = false) String originalprice,
            @RequestParam(required = false) Double sellprice,
            @RequestParam(required = false) String publication,
            @RequestParam(required = false) String edition,
            @RequestParam(required = false) String condition,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String location,
            @RequestParam("image") MultipartFile imageFile) throws Exception {

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


    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookServiceImpl.getAllBooks();
        return ResponseEntity.ok(books);
    }

    
    @GetMapping("/{bookId}/image")
    public ResponseEntity<byte[]> getBookImage(@PathVariable Integer bookId) {
        Book book = bookRepo.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found"));

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(book.getImage());
    }

    @GetMapping("/{bookId}")
    @PreAuthorize("isAuthenticated()")
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

    @DeleteMapping("/{bookId}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<String> deleteBookById(@PathVariable Integer bookId) {
        bookServiceImpl.deleteBookById(bookId);
        return ResponseEntity.ok("Book with ID " + bookId + " deleted successfully");
    }
}



