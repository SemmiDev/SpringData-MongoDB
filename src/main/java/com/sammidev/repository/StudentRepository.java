package com.sammidev.repository;

import com.sammidev.entity.Gender;
import com.sammidev.entity.Student;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends MongoRepository<Student, String> {
    Optional<Student> findStudentByEmail(String email);
    List<Student> findStudentsByGender(Gender gender);
    List<Student> findStudentsByAddress_PostCode(String postCode);
    List<Student> findStudentsByAddress_City(String city);
    List<Student> findStudentsByAddress_Country(String country);
}