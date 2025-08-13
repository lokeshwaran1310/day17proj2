package com.example.hostelmanagement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.hostelmanagement.entity.*;
import com.example.hostelmanagement.repository.*;

@SpringBootApplication
public class HostelmanagementApplication {

	@Autowired
	private StudentRepository studentRepository;
	
	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private StaffRepository staffRepository;

	public static void main(String[] args) {
		SpringApplication.run(HostelmanagementApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandRunner() {
		return args -> {
			Staff staff1 = new Staff("Alice Warden", "alice.warden@hostel.com", "WARDEN", "9876543210", "DAY");
			Staff staff2 = new Staff("Bob Security", "bob.security@hostel.com", "SECURITY", "9876543211", "NIGHT");
			Staff staff3 = new Staff("Charlie Cleaner", "charlie.cleaner@hostel.com", "CLEANER", "9876543212", "DAY");
			Staff staff4 = new Staff("Diana Maintenance", "diana.maintenance@hostel.com", "MAINTENANCE", "9876543213", "NIGHT");
			Staff staff5 = new Staff("Eve Manager", null, "MANAGER", "9876543214", null); 
			staffRepository.save(staff1);
			staffRepository.save(staff2);
			staffRepository.save(staff3);
			staffRepository.save(staff4);
			staffRepository.save(staff5);
			
			Room room1 = new Room("101", "SINGLE", "OCCUPIED", 1, 1);
			Room room2 = new Room("102", "DOUBLE", "OCCUPIED", 2, 1);
			Room room3 = new Room("103", "TRIPLE", "AVAILABLE", 3, 1);
			Room room4 = new Room("201", "SINGLE", "AVAILABLE", 1, 2);
			Room room5 = new Room("202", "DOUBLE", "OCCUPIED", 2, 2);
			Room room6 = new Room("203", "TRIPLE", "MAINTENANCE", 3, 2);
			Room room7 = new Room("301", "SINGLE", null, 1, 3); 
			Room room8 = new Room("302", "QUAD", "AVAILABLE", 4, 3);
			room1.setManagedBy(staff1);
			room2.setManagedBy(staff1);
			room3.setManagedBy(staff2);
			room4.setManagedBy(staff2);
			room5.setManagedBy(staff3);
			room6.setManagedBy(staff3);
			room7.setManagedBy(staff4);
			room8.setManagedBy(null); 
			
			roomRepository.save(room1);
			roomRepository.save(room2);
			roomRepository.save(room3);
			roomRepository.save(room4);
			roomRepository.save(room5);
			roomRepository.save(room6);
			roomRepository.save(room7);
			roomRepository.save(room8);

			Student student1 = new Student("CS2021001", "Alice Smith", "alice.smith@student.com", "Computer Science", "9876543220", Integer.valueOf(3));
			Student student2 = new Student("CS2021002", "Bob Johnson", "bob.johnson@student.com", "Computer Science", "9876543221", Integer.valueOf(3));
			Student student3 = new Student("ME2021001", "Charlie Brown", "charlie.brown@student.com", "Mechanical", "9876543222", Integer.valueOf(2));
			Student student4 = new Student("EE2021001", "Diana Wilson", "diana.wilson@student.com", "Electrical", "9876543223", Integer.valueOf(4));
			Student student5 = new Student("CS2022001", "Eve Davis", "eve.davis@student.com", "Computer Science", "9876543224", Integer.valueOf(2));
			Student student6 = new Student("ME2022001", "Frank Miller", "frank.miller@student.com", "Mechanical", "9876543225", Integer.valueOf(1));
			Student student7 = new Student("EE2022001", "Grace Taylor", null, "Electrical", "9876543226", null); // Null values for testing
			Student student8 = new Student("CS2023001", "Henry Anderson", "henry.anderson@student.com", "Computer Science", "9876543227", Integer.valueOf(1));
			Student student9 = new Student("PH2021001", "Ivy Thomas", "ivy.thomas@student.com", "Physics", "9876543228", Integer.valueOf(3));
			Student student10 = new Student("CH2021001", "Jack White", "jack.white@student.com", "Chemistry", "9876543229", Integer.valueOf(2));

			student1.setRoom(room1);
			student2.setRoom(room2);
			student3.setRoom(room2);
			student4.setRoom(room5);
			student5.setRoom(room5);
			student6.setRoom(room4);
			
			studentRepository.save(student1);
			studentRepository.save(student2);
			studentRepository.save(student3);
			studentRepository.save(student4);
			studentRepository.save(student5);
			studentRepository.save(student6);
			studentRepository.save(student7);
			studentRepository.save(student8);
			studentRepository.save(student9);
			studentRepository.save(student10);
		};
	}
}
