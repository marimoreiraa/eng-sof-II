package br.com.marianadmoreira.student.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.marianadmoreira.student.entity.Student;
import br.com.marianadmoreira.student.repository.StudentRepository;

@Service
public class StudentService {
    
    @Autowired
    private StudentRepository studentRepository;

    public List<Student> listStudents() {
        return this.studentRepository.findAll();
    }

    public List<Student> searchStudents(String name) {
        var allStudents = this.listStudents();
        List<Student> students = new ArrayList<>();

        if(name.isBlank()){
            return allStudents;
        }
        
        for(Student student: allStudents){
            if(student.getFirstname().toUpperCase().contains(name.toUpperCase())){
                students.add(student);
            }
        }

        return students;
    }


    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public void updateStudent(Long id,Student student) {
        Student newStudent = studentRepository.findById(id).get();
        newStudent.setFirstname(student.getFirstname());
        newStudent.setLasname(student.getLasname());
        newStudent.setEmail(student.getEmail());
        studentRepository.save(newStudent);
    }


    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }


    public Student searchStudentById(Long id) {
        Student student = studentRepository.findById(id).orElse(null);
        return student;
    }

}
