package com.sammidev;

import com.sammidev.entity.Address;
import com.sammidev.entity.Gender;
import com.sammidev.entity.Student;
import com.sammidev.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootApplication
public class App {

	public static void main(String[] args) {
		SpringApplication.run(App.class, args);
	}

	@Bean
	CommandLineRunner runner(StudentRepository studentRepository, MongoTemplate mongoTemplate) {
		return args -> {
			String email = "sammidev4@gmail.com";
			String email2 = "aditya@gmail.com";
			String email3 = "izzah@gmail.com";

			Student student = Student.builder()
					.name("Sammi Aldhi Yanto")
					.email(email)
					.gender(Gender.MALE)
					.address(new Address("Padang", "2131", "Indonesia"))
					.favouriteSubjects(List.of("Computer Science"))
					.totalSpentInBooks(BigDecimal.valueOf(2000000))
					.created(LocalDateTime.now())
					.updated(null)
					.build();

			Student student2 = Student.builder()
					.name("Aditya Andika Putra")
					.email(email2)
					.gender(Gender.MALE)
					.address(new Address("Jakarta", "2132", "Indonesia"))
					.favouriteSubjects(List.of("Anastesi"))
					.totalSpentInBooks(BigDecimal.valueOf(4000000))
					.created(LocalDateTime.now())
					.updated(null)
					.build();

			Student student3 = Student.builder()
					.name("Rahmatul Izzah Annisa")
					.email(email3)
					.gender(Gender.FEMALE)
					.address(new Address("Padang", "2131", "Indonesia"))
					.favouriteSubjects(List.of("Computer Science"))
					.totalSpentInBooks(BigDecimal.valueOf(2000000))
					.created(LocalDateTime.now())
					.updated(null)
					.build();

			// usingMongoTemplateAndQuery(studentRepository, mongoTemplate, email, student);
			studentRepository.findStudentByEmail(email).ifPresentOrElse(s -> {
						System.out.println(student + " already exists");
					}, () -> {
						studentRepository.insert(student);
						System.out.println("inserting student " + student);
						studentRepository.insert(student2);
						System.out.println("inserting student " + student2);
						studentRepository.insert(student3);
						System.out.println("inserting student " + student3);
					});
		};
	}

	private void usingMongoTemplateAndQuery(StudentRepository studentRepository, MongoTemplate mongoTemplate, String email, Student student) {
		Query query = new Query();
		query.addCriteria(Criteria.where("email").is(email));
		List<Student> students = mongoTemplate.find(query, Student.class);
		if (students.size() > 1) {
			throw new IllegalStateException("found many students with email " + email);
		}

		if (students.isEmpty()) {
			System.out.println("inserting student " + student);
			studentRepository.insert(student);
		}else {
			System.out.println(student + " already exists");
		}
	}
}