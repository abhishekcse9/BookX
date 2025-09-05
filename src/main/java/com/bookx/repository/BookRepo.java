package com.bookx.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bookx.entity.Book;

@Repository
public interface BookRepo extends JpaRepository<Book, Integer> {

}
