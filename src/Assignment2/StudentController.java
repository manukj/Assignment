package Assignment2;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StudentController {
	private FileOutputStream fileOutputStream = null;
	private final String FILE_NAME = "Assignment2.ser";
	private List<Student> listOfStudents;
	private Input input = null;

//Constructor
	public StudentController() {
		input = new Input();
		listOfStudents = new ArrayList<Student>();
		try {
			fileOutputStream = new FileOutputStream(FILE_NAME);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	//function to display the user
	public void disaplayAlllistOfStudents() {
		if (listOfStudents.size() != 0) {
			System.out.println("--------------------------------------------------------------");
            System.out.printf("%-15s %-11s %-5s %-20s %-10s","Roll Number","Name","Age","Address","Courses\n");
            System.out.println("--------------------------------------------------------------");
            
            Student[] students = new Student[listOfStudents.size()]; 
            students = listOfStudents.toArray(students); 
            
            Arrays.sort(students);
            for(Student std : students)
            {
            	System.out.printf("%-15s %-11s %-5s %-20s %-10s",std.getRollNumber(),std.getFullName(),std.getAge(),std.getAddress(),String.join(" ", std.getCoursenrolled()));
            }
            System.out.println("\n\n");
		} else
			System.out.println("listOfStudents list is empty");
	}

//function to add the student in the list 
	public void addStudent() {
		Student s = new Student();
		System.out.println("Enter student Details (all the fields are mandatory)");

		System.out.println("Full Name : ");
		s.setFullName(input.getValidString("FullName"));

		System.out.println("Age : ");
		s.setAge(input.getValidInt("Age"));

		System.out.println("Address : ");
		s.setAddress(input.getValidString("Address"));

		System.out.println("RollNumber : ");
		s.setRollNumber(input.getValidInt("RollNumber"));

		ArrayList<String> courses_list = new ArrayList<String>();
		courses_list.add("A");
		courses_list.add("B");
		courses_list.add("C");
		courses_list.add("D");
		courses_list.add("E");
		courses_list.add("F");
		s.setCoursenrolled(input.getValidCourseInput(courses_list, 4));
		listOfStudents.add(s);
	}
}
