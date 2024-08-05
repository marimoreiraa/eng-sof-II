package br.com.marianadmoreira.loan.service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.marianadmoreira.loan.client.BookClient;
import br.com.marianadmoreira.loan.client.StudentClient;
import br.com.marianadmoreira.loan.entity.Book;
import br.com.marianadmoreira.loan.entity.Loan;
import br.com.marianadmoreira.loan.entity.Student;
import br.com.marianadmoreira.loan.repository.LoanRepository;

@Service
public class LoanService {
    
    @Autowired
    private LoanRepository loanRepository;

    @Autowired
    private StudentClient studentClient;

    @Autowired
    private BookClient bookClient;

    public List<Loan> listLoans() {
        return this.loanRepository.findAll();
    }


    public List<Loan> loansByStudent(Long idStudent){
        var allLoans = this.listLoans();
        List<Loan> loans = new ArrayList<>();

        for( Loan loan: allLoans){
            if(loan.getStudent().getIdStudent().equals(idStudent)){
                loans.add(loan);
            }
        }
        return loans;
    }

    public List<Loan> activeLoansByStudent(Long idStudent) {
        List<Loan> allLoans = this.listLoans();
        List<Loan> loans = new ArrayList<>();
        for (Loan loan : allLoans) {
            if (loan.getStudent().getIdStudent().equals(idStudent) && loan.getReturnDate() == null) {
                loans.add(loan);
            }
        }
        return loans;
    }

    public List<Loan> activeLoans() {
        var allLoans = this.listLoans();
        List<Loan> loans = new ArrayList<>();

        for(Loan loan: allLoans){
            if(loan.getReturnDate() == null){
                loans.add(loan);
            }
        }
        return loans;
    }

    public List<Loan> overdueLoans() {
        var allLoans = this.listLoans();
        List<Loan> loans = new ArrayList<>();

        for(Loan loan: allLoans){
            if(loan.getStatus().equals("ATRASADO")){
                loans.add(loan);
            }
        }
        return loans;
    }

    public List<Loan> returnedLoans() {
        var allLoans = this.listLoans();
        List<Loan> loans = new ArrayList<>();
        for(Loan loan: allLoans){
            if(loan.getStatus().equals("DEVOLVIDO_COM_MULTA") || loan.getStatus().equals("DEVOLVIDO_SEM_MULTA")){
                loans.add(loan);
            }
        }
        return loans;
    }

    public Loan searchLoan(Long idLoan) {
        return this.loanRepository.findById(idLoan).orElse(null);
    }

    public boolean addLoan(Long studentId, Long bookId) {
        Student student = studentClient.findStudentById(studentId);
        Book book = bookClient.findBookById(bookId);

        if (student == null || book == null) {
            return false; // Usuário ou livro não encontrado
        }

        int qtdLoans = activeLoansByStudent(studentId).size();

        if(qtdLoans >= Loan.LIMIT_BORROWS){
            return false;
        }
        else if(book.getStatus() == "EMPRESTADO"){
            return false;
        }
        else{
            Loan loan = new Loan();
            loan.setStudent(student);
            loan.setBook(book);
            loan.setStatus("REGULAR");
            loan.getBook().setStatus("EMPRESTADO");
            loan.setLoanDate(LocalDate.now());
            loan.setReturnLimitDate(loan.getLoanDate().plusDays(10));
            loan.setFine(0.0);
            this.loanRepository.save(loan);
            return true;
        }

        
    }

    public void returnBook(Long id) {
        Loan loan = this.loanRepository.findById(id).get();
        Book book = loan.getBook();
        if (book != null) {
          book.setStatus("DISPONIVEL");
        }
        loan.setReturnDate(LocalDate.now());
        if (loan.getFine() == 0) {
            loan.setStatus("DEVOLVIDO_SEM_MULTA");
        } 
        else {
            loan.setStatus("DEVOLVIDO_COM_MULTA");
        }
       
    }

    public void calculeFine(Loan loan) {
        LocalDate today = LocalDate.now();
        int daysLate = (int) ChronoUnit.DAYS.between(today, loan.getReturnLimitDate());

        double fine = 0;

        switch (daysLate) {
            case 0:
                fine = 0;
                break;
            default:
                fine = daysLate * 1.0; // Convertido para double para manter a precisão
                break;
        }

        loan.setFine(fine);
    }

    public void setLoanStatus() {
        var allLoans = this.listLoans();
        LocalDate today = LocalDate.now();
        for(Loan loan: allLoans){
            int daysLoaned = (int) ChronoUnit.DAYS.between(today, loan.getLoanDate());
            if(daysLoaned > 10){
                loan.setStatus("ATRASADO");
            }
            else{
                loan.setStatus("REGULAR");
            }
        }

    }

    public void calculeFineForAllLoans() {
        var allLoans = this.listLoans();
        for (Loan loan : allLoans) {
            calculeFine(loan);
        }
    }

    public void updateLoanStatusForAllLoans() {
        var allLoans = this.listLoans();
        LocalDate today = LocalDate.now();
        for (Loan loan : allLoans) {
            int daysLoaned = (int) ChronoUnit.DAYS.between(loan.getLoanDate(), today);
            if (daysLoaned > 10) {
                loan.setStatus("ATRASADO");
            } else {
                loan.setStatus("REGULAR");
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *") // Executa todo dia à meia-noite
    public void scheduleCalculeFine() {
        calculeFineForAllLoans();
    }


    @Scheduled(cron = "0 0 0 * * *") // Executa todo dia à meia-noite
    public void scheduleUpdateLoanStatus() {
        updateLoanStatusForAllLoans();
    }

    public Optional<Loan> searchLoanById(Long idLoan) {
       return this.loanRepository.findById(idLoan);
    }

 
}
