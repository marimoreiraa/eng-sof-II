package br.com.marianadmoreira.loan.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marianadmoreira.loan.entity.Loan;

public interface LoanRepository extends JpaRepository<Loan, Long>{
    
}
