package br.com.marianadmoreira.book.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.marianadmoreira.book.entity.Book;
import br.com.marianadmoreira.book.service.BookService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {
    
    private final BookService bookService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Book book ) {
        bookService.addBook(book);
    }

    @GetMapping
    public ResponseEntity<List<Book>> findAllBooks() {
        return ResponseEntity.ok(bookService.listBooks());
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> findBookById(@PathVariable("bookId") Long bookId) {
        return ResponseEntity.ok(bookService.searchBook(bookId));
    }

    @GetMapping("/by-title/{title}")
    public ResponseEntity<List<Book>> findBooksByTitle(@PathVariable("title") String title) {
        return ResponseEntity.ok(bookService.searchBookByTitle(title));
    }

    @PutMapping("/{bookId}")
    public void updateBook(@PathVariable("bookId") Long bookId, @RequestBody Book book) {
        bookService.updateBook(bookId, book);
    }

    @DeleteMapping("/{bookId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBook(@PathVariable("bookId") Long bookId) {
        bookService.deleteBook(bookId); 
    }
}
