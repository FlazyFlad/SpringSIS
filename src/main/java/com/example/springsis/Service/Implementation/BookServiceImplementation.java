package com.example.springsis.Service.Implementation;

import com.example.springsis.Entity.Book;
import com.example.springsis.Repository.BookRepository;
import com.example.springsis.Service.BookServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class BookServiceImplementation implements BookServiceInterface {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> FindAllBooks() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Book saveBook(Book book){
        return bookRepository.save(book);
    }

    @Override
    public Book findById(Long id){
        return bookRepository.findById(id).orElse(null);
    }

    @Override
    public Book deleteBook(Long id) {
        bookRepository.deleteById(id);
        return null;
    }
}
