package com.sammidev.controller;

import com.sammidev.model.*;
import com.sammidev.service.StudentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.MediaType;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/students")
public record StudentController(StudentService studentService) {

    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<StudentResponse> createStudent(@RequestBody CreateStudentRequest request) {
        var studentResponse = studentService.createStudent(request);
        return new WebResponse<>(
                HttpStatus.CREATED.value(),
                HttpStatus.CREATED.name(),
                studentResponse);
    }

    @GetMapping(
            value = "/{studentID}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<StudentResponse> getStudentByID(@PathVariable("studentID") String studentID) {
        var studentResponse = studentService.getById(studentID);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                studentResponse);
    }

    @GetMapping(
            value = "/country/{country}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<StudentResponse>> getAllStudentsByCountry(@PathVariable("country") String country) {
        var studentResponse = studentService.getAllStudentsByCountry(country);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                studentResponse);
    }

    @GetMapping(
            value = "/gender/{gender}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<StudentResponse>> getAllStudentsByGender(@PathVariable("gender") String gender) {
        var studentResponse = studentService.getAllStudentsByGender(gender);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                studentResponse);
    }

    @GetMapping(
            value = "/email/{email}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<StudentResponse> getStudentByEmail(@PathVariable("email") String email) {
        var studentResponse = studentService.getByEmail(email);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                studentResponse);
    }

    @GetMapping(
            value = "/postCode/{postCode}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<StudentResponse>> getAllStudentsByPostCode(@PathVariable("postCode") String postCode) {
        var studentResponse = studentService.getAllStudentsByPostCode(postCode);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                studentResponse);
    }

    @GetMapping(
            value = "/city/{city}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<StudentResponse>> getAllStudentsByCity(@PathVariable("city") String city) {
        var studentResponse = studentService.getAllStudentsByCity(city);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                studentResponse);
    }

    @PutMapping(
            value = "/{studentID}",
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<StudentResponse> updateStudentById(@PathVariable("studentID") String id,
                                                          @RequestBody UpdateStudentRequest request) {
        var studentResponse = studentService.updateStudent(id, request);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                studentResponse);
    }

    @DeleteMapping(
            value = "/{studentID}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> deleteStudentById(@PathVariable("studentID") String id) {
        studentService.deleteStudent(id);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                id);
    }

    @GetMapping(
            produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<StudentResponse>> getAllStudents(@RequestParam(value = "size", defaultValue = "10") int size,
                                                             @RequestParam(value = "page", defaultValue = "0") int page) {
        var listStudentRequest = ListStudentRequest.builder()
                .page(page)
                .size(size)
                .build();

        var studentResponse = studentService.getAllStudents(listStudentRequest);
        return new WebResponse<>(
                HttpStatus.OK.value(),
                HttpStatus.OK.name(),
                studentResponse);
    }
}