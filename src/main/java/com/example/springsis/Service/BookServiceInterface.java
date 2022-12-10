package com.example.springsis.Service;

import com.example.springsis.Entity.Book;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BookServiceInterface {
    List<Book> FindAllBooks();
    Book saveBook(Book book, MultipartFile file) throws IOException;
    Book updateBook(Book book);
    Book findById(Long id);
    Book deleteBook(Long id);
}
