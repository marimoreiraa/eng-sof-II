package br.com.marianadmoreira.loan.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import br.com.marianadmoreira.loan.entity.Loan;
import br.com.marianadmoreira.loan.service.LoanService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/loans")
@RequiredArgsConstructor
public class LoanController {
    
    private final LoanService loanService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestParam("studentId") Long studentId, @RequestParam("bookId") Long bookId) {
        loanService.addLoan(studentId, bookId);
    }
    
    @GetMapping
    public ResponseEntity<List<Loan>> findAllLoans() {
        return ResponseEntity.ok(loanService.listLoans());
    }

    @GetMapping("/actives")
    public ResponseEntity<List<Loan>> listActiveLoans() {
        return ResponseEntity.ok(loanService.activeLoans());
    }

    @GetMapping("/overdue")
    public ResponseEntity<List<Loan>> listOverdueLoans() {
        return ResponseEntity.ok(loanService.overdueLoans());
    }

    @GetMapping("/returned")
    public ResponseEntity<List<Loan>> listReturnedLoans() {
        return ResponseEntity.ok(loanService.returnedLoans());
    }

    @PutMapping("/{id}/return")
    public void returnLoan(@PathVariable Long id) {
        loanService.returnBook(id);
    }
}

