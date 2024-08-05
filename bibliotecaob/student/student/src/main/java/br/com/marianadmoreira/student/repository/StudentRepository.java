package br.com.marianadmoreira.student.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.marianadmoreira.student.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Long>{

    
} 