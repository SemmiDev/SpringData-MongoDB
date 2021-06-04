package com.sammidev.service.impl;

import com.sammidev.entity.Gender;
import com.sammidev.entity.Student;
import com.sammidev.exception.NotFoundException;
import com.sammidev.model.CreateStudentRequest;
import com.sammidev.model.ListStudentRequest;
import com.sammidev.model.StudentResponse;
import com.sammidev.model.UpdateStudentRequest;
import com.sammidev.repository.StudentRepository;
import com.sammidev.service.StudentService;
import com.sammidev.validation.ValidationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    private StudentRepository studentRepository;
    private ValidationUtil validationUtil;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository, ValidationUtil validationUtil) {
        this.studentRepository = studentRepository;
        this.validationUtil = validationUtil;
    }

    @Override
    public StudentResponse createStudent(CreateStudentRequest createStudentRequest) {
        validationUtil.validate(createStudentRequest);
        var student = Student.builder()
                .name(createStudentRequest.getName())
                .email(createStudentRequest.getEmail())
                .gender(createStudentRequest.getGender())
                .address(createStudentRequest.getAddress())
                .favouriteSubjects(createStudentRequest.getFavouriteSubjects())
                .totalSpentInBooks(createStudentRequest.getTotalSpentInBooks())
                .created(LocalDateTime.now())
                .updated(null)
                .build();

        var std =  studentRepository.insert(student);
        return convertStudentToStudentResponse(std);
    }

    @Override
    public StudentResponse updateStudent(String id, UpdateStudentRequest updateStudentRequest) {
        var student = findStudentByIdThrowNotFound(id);
        validationUtil.validate(updateStudentRequest);

        student.setName(updateStudentRequest.getName());
        student.setEmail(updateStudentRequest.getEmail());
        student.setGender(updateStudentRequest.getGender());
        student.setAddress(updateStudentRequest.getAddress());
        student.setFavouriteSubjects(updateStudentRequest.getFavouriteSubjects());
        student.setTotalSpentInBooks(updateStudentRequest.getTotalSpentInBooks());
        student.setUpdated(LocalDateTime.now());

        studentRepository.save(student);
        return convertStudentToStudentResponse(student);
    }

    @Override
    public List<StudentResponse> getAllStudents(ListStudentRequest listStudentRequest) {
        var page = studentRepository.findAll(
                PageRequest.of(
                        listStudentRequest.getPage(),
                        listStudentRequest.getSize()));

        var students = page.get().collect(Collectors.toList());
        return convertStudentToListStudentResponse(students);
    }

    @Override
    public void deleteStudent(String id) {
        var student = findStudentByIdThrowNotFound(id);
        studentRepository.delete(student);
    }

    @Override
    public StudentResponse getById(String id) {
        return convertStudentToStudentResponse(findStudentByIdThrowNotFound(id));
    }

    @Override
    public StudentResponse getByEmail(String email) {
        var student = studentRepository.findStudentByEmail(email);
        if (!student.isPresent()) {
            throw new NotFoundException("Student with email " + email + " Not Found");
        }
        return convertStudentToStudentResponse(student.get());
    }

    @Override
    public List<StudentResponse> getAllStudentsByGender(String gender) {
        var students = studentRepository.findStudentsByGender(Gender.valueOf(gender));
        return convertStudentToListStudentResponse(students);
    }

    @Override
    public List<StudentResponse> getAllStudentsByCity(String city) {
        var students = studentRepository.findStudentsByAddress_City(city);
        return convertStudentToListStudentResponse(students);
    }

    @Override
    public List<StudentResponse> getAllStudentsByPostCode(String postCode) {
        var students = studentRepository.findStudentsByAddress_PostCode(postCode);
        return convertStudentToListStudentResponse(students);
    }

    @Override
    public List<StudentResponse> getAllStudentsByCountry(String country) {
        var students = studentRepository.findStudentsByAddress_Country(country);
        return convertStudentToListStudentResponse(students);
    }

    private StudentResponse convertStudentToStudentResponse(Student student) {
        return new StudentResponse().builder()
                .id(student.getId())
                .name(student.getName())
                .email(student.getEmail())
                .gender(student.getGender())
                .address(student.getAddress())
                .favouriteSubjects(student.getFavouriteSubjects())
                .totalSpentInBooks(student.getTotalSpentInBooks())
                .created(student.getCreated())
                .updated(student.getUpdated())
                .build();
    }

    private Student findStudentByIdThrowNotFound(String id) {
        var student = studentRepository.findById(id);
        if (student.isEmpty()) {
            throw new NotFoundException("Not Found");
        }
        return student.get();
    }

    private List<StudentResponse> convertStudentToListStudentResponse(List<Student> students) {
        return students.stream().map(this::convertStudentToStudentResponse).collect(Collectors.toList());
    }
}
