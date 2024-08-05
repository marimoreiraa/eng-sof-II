package br.com.marianadmoreira.book.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marianadmoreira.book.entity.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    Book findByIsbn(String isbn);
}
