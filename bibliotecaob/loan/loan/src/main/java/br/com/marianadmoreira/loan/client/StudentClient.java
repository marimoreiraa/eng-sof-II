package br.com.marianadmoreira.loan.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import br.com.marianadmoreira.loan.entity.Student;

@FeignClient(name = "student-service", url="${application.config.students-url}")
public interface StudentClient {
    @GetMapping("/students/{studentId}")
    Student findStudentById(@PathVariable("studentId") Long studentId);

}