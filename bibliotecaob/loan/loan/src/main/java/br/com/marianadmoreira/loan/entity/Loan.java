package br.com.marianadmoreira.loan.entity;

import java.time.LocalDate;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Loan {
    
    public static final int LIMIT_BORROWS = 3;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idLoan;

    private LocalDate loanDate;

    private LocalDate returnLimitDate;

    private LocalDate returnDate;

    private String status;

    private Double fine;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
}
