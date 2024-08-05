package br.com.marianadmoreira.book.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.marianadmoreira.book.entity.Book;
import br.com.marianadmoreira.book.repository.BookRepository;


@Service
public class BookService {
    
    @Autowired
    private BookRepository bookRepository;


    public List<Book> listBooks(){
        return this.bookRepository.findAll();
    }


    public Book addBook(Book book) {
        book.setStatus("DISPONIVEL");
        return this.bookRepository.save(book);
    }


    public void deleteBook(Long id) {
        this.bookRepository.delete(bookRepository.findById(id).get());
    }


    public void updateBook(Long id, Book book) {
        Book bookUpdating = bookRepository.findById(id).get();
        bookUpdating.setId(book.getId());
        bookUpdating.setAuthor(book.getAuthor());
        bookUpdating.setTitle(book.getTitle());
        bookUpdating.setCategory(book.getCategory());
        bookUpdating.setYear(book.getYear());
        bookUpdating.setPublisher(book.getPublisher());
        bookUpdating.setStatus(book.getStatus());

        this.bookRepository.save(bookUpdating);

    }


    public Book searchBook(Long idBook) {
        return this.bookRepository.findById(idBook).orElse(null);
    }


    public List<Book> searchBookByTitle(String title) {
        var allBooks = this.listBooks();
        List<Book> books = new ArrayList<>();

        if(title.isBlank()){
            return allBooks;
        }
        
        for(Book book: allBooks){
            if(book.toString().toUpperCase().contains(title.toUpperCase())){
                books.add(book);
            }
        }

        return books;
    }

    public boolean checkIfBookExists(String isbn) {
        Book bookExists = bookRepository.findByIsbn(isbn);
        if(bookExists != null){
            return true ;     
        }
        else{
            return false;
        }
    }


}


