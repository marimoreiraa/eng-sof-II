package br.com.marianadmoreira.student.controller;

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

import br.com.marianadmoreira.student.entity.Student;
import br.com.marianadmoreira.student.service.StudentService;
import lombok.RequiredArgsConstructor;
    
@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentController {
    private final StudentService studentService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void add(@RequestBody Student student ) {
        studentService.addStudent(student);
    }

    @GetMapping
    public ResponseEntity<List<Student>> findAllStudents() {
        return ResponseEntity.ok(studentService.listStudents());
    }

    @GetMapping("/{studentId}")
    public ResponseEntity<Student> findStudentById(@PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok(studentService.searchStudentById(studentId));
    }

    @GetMapping("/by-name/{name}")
    public ResponseEntity<List<Student>> findStudentsByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(studentService.searchStudents(name));
    }

    @PutMapping("/{studentId}")
    public void updateStudent(@PathVariable("studentId") Long studentId, @RequestBody Student student) {
        studentService.updateStudent(studentId,student);
    }

    @DeleteMapping("/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable("studentId") Long studentId) {
        studentService.deleteStudent(studentId);
    }
}


