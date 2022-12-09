// \u000d package com.example.springsis.Repository;


import com.example.springsis.Entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * asd.
 */
@Repository
@Transactional
public interface BookRepository extends JpaRepository<Book, Long> {

}
