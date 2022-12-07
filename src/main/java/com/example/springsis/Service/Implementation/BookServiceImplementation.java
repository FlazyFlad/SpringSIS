package com.example.springsis.Service.Implementation;

import com.example.springsis.Entity.Book;
import com.example.springsis.Entity.Image;
import com.example.springsis.Repository.BookRepository;
import com.example.springsis.Service.BookServiceInterface;
import com.example.springsis.util.ImageUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@Slf4j
public class BookServiceImplementation implements BookServiceInterface {

    @Autowired
    BookRepository bookRepository;

    @Override
    public List<Book> FindAllBooks() {
        return bookRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
    }

    @Override
    public Book saveBook(Book book, MultipartFile file) throws IOException {
        Image image1 = null;
        if (file.getSize() != 0) {
            image1 = toImageEntity(file);
            image1.setPreviewImage(true);
            book.addImageToProduct(image1);
        }
        Book productFromDb = bookRepository.save(book);
        productFromDb.setPreviewImageId(image1.getId());
        bookRepository.save(book);
        return book;
    }

    @Override
    public Book updateBook(Book book) {
        String name = book.getName();
        return book;
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
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
