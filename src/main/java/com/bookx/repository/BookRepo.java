package com.bookx.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookx.entity.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

	List<Book> findByCategoryIgnoreCase(String categoryName);

	List<Book> findByUserId(Integer userId);

	List<Book> getBooksByCategory(String categoryName);

}
