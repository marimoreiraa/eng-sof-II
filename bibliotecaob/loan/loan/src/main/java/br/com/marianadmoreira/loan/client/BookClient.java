package br.com.marianadmoreira.loan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.marianadmoreira.loan.entity.Book;

@FeignClient(name = "book-service", url = "${application.config.books-url}")
public interface BookClient {
    @GetMapping("/books/{bookId}")
    Book findBookById(@PathVariable("bookId") Long bookId);
}