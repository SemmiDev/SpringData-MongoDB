package com.sammidev.service;

import com.sammidev.model.CreateStudentRequest;
import com.sammidev.model.ListStudentRequest;
import com.sammidev.model.StudentResponse;
import com.sammidev.model.UpdateStudentRequest;

import javax.validation.Valid;
import java.util.List;

public interface StudentService {
    StudentResponse createStudent(@Valid CreateStudentRequest createStudentRequest);
    StudentResponse updateStudent(@Valid String id, UpdateStudentRequest updateStudentRequest);
    List<StudentResponse> getAllStudents(@Valid ListStudentRequest listStudentRequest);
    void deleteStudent(String id);

    StudentResponse getById(String id);
    StudentResponse getByEmail(String email);
    List<StudentResponse> getAllStudentsByGender(String gender);
    List<StudentResponse> getAllStudentsByCity(String city);
    List<StudentResponse> getAllStudentsByPostCode(String postCode);
    List<StudentResponse> getAllStudentsByCountry(String country);
}