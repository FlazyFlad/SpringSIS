package com.example.springsis.Service;

import com.example.springsis.Entity.Book;

import java.util.List;

public interface BookServiceInterface {
    List<Book> FindAllBooks();
    Book saveBook(Book book);
    Book findById(Long id);
    Book deleteBook(Long id);
}
