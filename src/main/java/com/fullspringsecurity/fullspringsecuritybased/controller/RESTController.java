package com.fullspringsecurity.fullspringsecuritybased.controller;

import com.fullspringsecurity.fullspringsecuritybased.models.Student;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("api/v1")
public class RESTController {

    private static final List<Student> students = Arrays.asList(
            new Student((long) 1, "Nelson"),
            new Student((long) 2, "Moses"),
            new Student((long) 3, "Otieno"),
            new Student((long) 4, "Vic"),
            new Student((long) 5, "Sam")
    );

    @GetMapping("/findOne/{studentId}")
    public Student getOne(@PathVariable("studentId") Long studentId) {
        return students.stream().filter(student -> studentId.equals(student.getStudentId())).findFirst()
                .orElseThrow(() -> new IllegalStateException("Student with Id :" + studentId + " not found"));
    }

    @GetMapping("/adm")
    public String adminView() {
        System.out.println("WELCOME, YOU ARE ADMIN");
        return "WELCOME, YOU ARE ADMIN";
    }

    @GetMapping("/student")
    public String studentView() {
        System.out.println("WELCOME, YOU ARE STUDENT");
        return "WELCOME, YOU ARE STUDENT";
    }

    @GetMapping("/admin2")
    public String admin2() {
        System.out.println("WELCOME, YOU ARE ADMIN WITH READ PERMISSIONS ONLY");
        return "WELCOME, YOU ARE ADMIN WITH READ PERMISSIONS ONLY";
    }

    @GetMapping("/getAllstudent")
    public List<Student> getAll() {
        return students;
    }

    @PostMapping("/createStudent")
    public Student studentView(@RequestBody Student student) {
        students.add(student);
        return student;
    }

    @DeleteMapping("/deleteStudent/{studentId}")
    public String studentdelete(@PathVariable("studentId") Long studentId) {
        System.out.println("Deleteing Student with id : " + studentId);
        return "Deleteing Student with id : " + studentId;
    }
}
