/*
 * Description: This program will display a console menu which will allow
 *              the user to select from 8 different options (add a course,
 *              add a student,  enroll student into course, unenroll student
 *              from course, remove a course, remove a student, list students 
 *              from course and exit the program) to update a database that has
 *              three tables (Student,Course and StudentCourse).
 */

package student.course;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Scanner;
import student.course.db.CourseDb;
import student.course.db.StudentCourseDb;
import student.course.db.StudentDb;

/**
 *
 * @author Maria Hoang
 */
public class StudentCourse {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /**
         * This String will list the menu options
         */
        String menu = "Please enter one of the following options:\n"
                + "1 - Add a Course\n"
                + "2 - Add a Student\n"
                + "3 - Enroll a Student in a Course\n"
                + "4 - Un-enroll a Student from a Course\n"
                + "5 - Remove a Course\n"
                + "6 - Remove a Student\n"
                + "7 - List Students from a Course\n"
                + "0 - To exit";
        
        /**
         * This Scanner will allow the user to input one of the menu options
         */
        Scanner input = new Scanner(System.in);
        System.out.println(menu);
        String selection = input.nextLine();
        
        /**
         * This while-loop will execute each menu option in the switch until 
         * the user enters 0 to exit the program.
         */
        while(!selection.equals("0")){
            switch(selection){
                case "1":
                    addCourse();
                    break;
                case "2":
                    addStudent();
                    break;
                case "3":
                    enrollStudentInCourse();
                    break;
                case "4":
                    unEnrollStudentFromCourse();
                    break;
                case "5":
                    removeCourse();
                    break;
                case "6":
                    removeStudent();
                    break;
                case "7":
                    listStudentsByCourse();
                    break;
                default:
                    System.out.println("Error: Please enter a valid option.\n");
                    break;
            }
            System.out.println(menu);
            selection = input.nextLine();
        }
        
    }

    /**
     * This method will add a course to the course table if the user input is
     * valid, otherwise it will ask to reenter the input until the correct 
     * information is entered.
     */
    private static void addCourse() {
        Scanner input = new Scanner(System.in);

        // check to see if user enter valid course name
        String courseName;
        do {
            System.out.println("Enter Course name:");
            courseName = input.nextLine();
        } while (!courseName.matches("[a-zA-Z]+$"));
        
        /**
         * validate user input of start time and converts the entered
         * string input into a time object
         */
        String startTime;
        do {
            System.out.println("Enter the start time:");
            startTime = input.nextLine();
        } while (!startTime.matches("([01]?[0-9]|2[0-3]):[0-5][0-9]:[0-5][0-9]")
                || startTime.equals("00:00:00"));
        Time time = Time.valueOf(startTime);
        
        Course course = new Course(courseName,time);
        
        CourseDb courseDb = new CourseDb();
        
        // check to see if adding course was possible or failed
        try {
            int result = courseDb.addCourse(course);
            if(result > 0)
                System.out.println("Course created!\n");
            else
                System.out.println("Error: Course not created.\n");
        } catch (Exception ex) {
            System.out.println("Error: Course not created.\n");
            System.out.println(ex.toString());
        }
        
    }

    /**
     * This method will add a student to the student table if the user input is
     * valid, otherwise it will ask to reenter the input until the correct 
     * information is entered.
     */
    private static void addStudent() {
        Scanner input = new Scanner(System.in);
        
        // check to see if user enter valid first and last name
        String firstName,lastName;
        do {
            System.out.println("Enter student's first name:");
            firstName = input.nextLine();
        } while (!firstName.matches("[a-zA-Z]+$"));

        do {
            System.out.println("Enter student's last name:");
            lastName = input.nextLine();
        } while (!lastName.matches("[a-zA-Z]+$"));

        // checks to see if age is a valid number
        int age;
        do {
            System.out.println("Enter student's age:");
            while(!input.hasNextInt()){
                System.out.println("Enter student's age:");
                input.next();
            }
            age = input.nextInt();
        } while (age <=0);
        
        Student student = new Student(firstName,lastName,age);
        
        StudentDb studentDb = new StudentDb();
        
        // check to see if adding student was possible or failed
        try {
            int result = studentDb.addStudent(student);
            if(result > 0)
                System.out.println("Student created!");
            else
                System.out.println("Error: Student not created.");
        } catch (Exception ex) {
            System.out.println("Error: Student not created.");
        }
        
    }

    /**
     * This method will enroll a specific student to a specific course based
     * on the users input (studentId and courseId) as long as the user input is
     * valid, otherwise it will ask to reenter the input until the correct 
     * information is entered.
     */
    private static void enrollStudentInCourse() {
        Scanner input = new Scanner(System.in);
        
        ArrayList<Student> students = listStudents();
        
        // user enter desired student and check if valid id
        String studentId;
        boolean testStudentId = false;
        boolean testCourseId = false;
        
        do {
            System.out.println("Please enter a Student to Enroll:");
            studentId = input.nextLine();
            testStudentId = validateId(studentId, "student");
        } while(!testStudentId);
        
        StudentCourseDb studentCourseDb = new StudentCourseDb();
        studentCourseDb.courseAvailability();
       
        // user enter desired course and check if valid id
        String courseId;
        do{
            System.out.println("Please enter a Course:");
            courseId = input.nextLine();
            testCourseId = validateId(courseId, "course");
        }while(!testCourseId);
        
        // check to see if enrollment was possible or failed
        int result;
        try {
            result = studentCourseDb.assignStudentToCourse(studentId, courseId);
            if(result > 0)
                System.out.println("Student enrolled!\n");
            else
                System.out.println("Error: Student was not enrolled.\n");
        } catch (Exception ex) {
            System.out.println("Error: Student was not enrolled.\n");
        }
        
    }

    /**
     * This method will show the student and course list and allow the user to 
     * choose the student then course for the selected student to un-enroll from
     */
    private static void unEnrollStudentFromCourse() {
        Scanner input = new Scanner(System.in);
        try {
            ArrayList<Student> students = listStudents();
            
            // user enter desired student and check if valid id
            String studentId;
            boolean testStudentId = false;
            boolean testCourseId = false;
            boolean checkStatus = true;

            do {
                System.out.println("Please enter a Student to Unenroll:");
                studentId = input.nextLine();
                testStudentId = validateId(studentId, "student");
            }while(!testStudentId);

            
            StudentCourseDb studentCourseDb = new StudentCourseDb();
            int checkId = Integer.parseInt(studentId);
           
            // will check if student is enrolled in courses
            checkStatus = studentCourseDb.checkStudentEnrollment(studentId);
            
            if (checkStatus){
                // will display the courses the student is enrolled in
                studentCourseDb.listCoursesOfStudent(checkId);

                // user enter desired course and check if valid id
                String courseId;
                do{
                    System.out.println("Please enter a Course:");
                    courseId = input.nextLine();
                    testCourseId = validateId(courseId, "course");
                }while(!testCourseId);

                /**
                 * will return a positive number if the student was successfully
                 * otherwise it will show an error message
                 */       
                int result;
                try {
                    result = studentCourseDb.removeStudentFromCourse(
                        studentId, 
                        courseId);
                    if(result > 0)
                        System.out.println("Student unenrolled!\n");
                    else
                        System.out.println("Error: Student was not unenrolled."
                            + "\n");
                } catch (Exception ex) {
                    System.out.println("Error: Student was not unenrolled.\n"
                    +ex.toString());
                }
            }else
                System.out.print("");
        }catch (Exception ex) {
            System.out.println(ex.toString());
        }

    }

    /*
     * This method will display the course list with the id and name
     * and allow the user to select which course to be removed. If there are no
     * students enrolled then the course will be removed, otherwise it will
     * display current students enrolled in the course.
     */
    private static void removeCourse() {
        Scanner input = new Scanner(System.in);
        try {
            ArrayList<Course> courses = listCourses();
            StudentCourseDb studentCourseDb = new StudentCourseDb();
            CourseDb courseDb = new CourseDb();
            
            boolean emptyCourse = false;
            boolean testCourseId = false;
            int result = 0;
            
            // user enter desired course and check if valid id
            String courseId;
            do{
                System.out.println("Please enter a Course ID to remove course:");
                courseId = input.nextLine();
                testCourseId = validateId(courseId, "course");
                
                // check if course is empty
                emptyCourse = studentCourseDb.emptyCourse(courseId);
                if(emptyCourse && testCourseId ){
                    result = courseDb.removeCourse(courseId);
                    if(result>0)
                        System.out.println("Course removed");  
                    else
                        System.out.println("Course not removed");
                }else if ((!emptyCourse) && testCourseId){
                    System.out.println("Cannot remove Course since there "
                        + "are students:");
                    studentCourseDb.listStudentsByCourseId(courseId);
                }
            }while((emptyCourse && !testCourseId)||(!emptyCourse && !testCourseId));
        } catch (Exception ex) {
            System.out.println("Course not removed");
        }
        
    }

    /**
     * This method will display the student list with their id and full name
     * and allow the user to select which student to be removed from all courses
     * and from the studentDb, as long as all the user input are valid.
     */
    private static void removeStudent() {
        Scanner input = new Scanner(System.in);
        
        ArrayList<Student> students = listStudents();

        try {
            StudentDb studentDb = new StudentDb();
            StudentCourseDb studentCourseDb = new StudentCourseDb();
            
            boolean testStudentId = false;
            boolean studentStatus = true;
            int removedStudent = 0;
            String studentId;
            
            // check if id is valid
            do {                
                System.out.println("Please enter a Student ID "
                    + "to remove student:");  
                studentId = input.nextLine();
                testStudentId = validateId(studentId,"student");
            } while (!testStudentId);
            
            /**
             * will return a positive number if the student was removed
             * otherwise it will show an error message
             */       
            int result = 0;
            try {
                // check if student is enrolled in courses
                studentStatus = studentCourseDb.checkStudentEnrollment(studentId);
                
                /**
                 * if the student is enrolled in courses, will remove student 
                 * from all the courses student is enrolled in
                 */
                if (studentStatus){
                    result = studentCourseDb.removeStudentFromAllCourses(studentId);
                    
                    // checks to see if student was successfully removed
                    if(result > 0) {
                        removedStudent = studentDb.removeStudent(studentId);
                        if (removedStudent > 0) 
                            System.out.println("Student removed");
                        else
                            System.out.println("Error: Student was not removed.\n");
                    }else
                        System.out.println("Error: Student was not removed.\n");
                }else if (!studentStatus){
                    removedStudent = studentDb.removeStudent(studentId);
                    if (removedStudent > 0) 
                        System.out.println("Student removed");
                    else
                        System.out.println("Error: Student was not removed.\n");
                }  
            } catch (Exception ex) {
                System.out.println("Error: Student was not removed.\n");
            }
        }catch (Exception e) {
            System.out.println("Student not removed");
        }

    }

    /**
     * This method will return the students enrolled in the user entered course
     * unless there are no students at which there will be an error message 
     */
    private static void listStudentsByCourse() {
        Scanner input = new Scanner(System.in);
        
        // check if valid course name
        String courseName;
        do {
            System.out.println("Enter the Course name:");
            courseName = input.nextLine();
        } while (!courseName.matches("[a-zA-Z]+$"));
        
        System.out.println("");

        StudentCourseDb studentCourseDb = new StudentCourseDb();
        try {
            studentCourseDb.listStudentsByCourse(courseName);
            System.out.println("");
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        
    }
    
    /**
     * This method will return true if the id is valid and false otherwise and 
     * takes in two parameters, id and value, where value represents either
     * student or course
     * @param id
     * @param value
     * @return boolean validate
     */
     private static boolean validateId(String id, String value){
        boolean validated = false;
        // checks if student ID is valid
        if (value.equalsIgnoreCase("student")) {
            StudentDb studentDb = new StudentDb();
            ArrayList<Student> students = null;
            try{
                int validateId = Integer.parseInt(id);
                students = studentDb.getStudents();
                for (int i = 0; i < students.size(); i++) {
                    Student student = students.get(i);
                    if(student.getDbId() == validateId){
                        validated = true;
                    }
                }
            }catch (Exception e){
                System.out.println("Enter a valid id");
            } 
         // checks if course ID is valid
         }else if(value.equalsIgnoreCase("course")){
            CourseDb courseDb = new CourseDb();
            ArrayList<Course> courses = null;
            try{
                int validateId = Integer.parseInt(id);
                courses = courseDb.getCourses();
                for (int i = 0; i < courses.size(); i++) {
                    Course course = courses.get(i);
                    if(course.getDbId() == validateId){
                        validated = true;
                    }
                }
            }catch (Exception e){
                System.out.println("Enter a valid id");
            } 
         }      
        return validated;
   
    }

    /**
     * This method will return an array of courses with their id, course name
     * @return ArrayList<Course> courses
     */
    private static ArrayList<Course> listCourses(){
        CourseDb courseDb = new CourseDb();
        ArrayList<Course> courses = null;
        
        try {
            courses = courseDb.getCourses();
            System.out.println("Course List:");
            String format = "%d: %s";
            for (int i = 0; i < courses.size() ; i++) {
                String output = String.format(format,
                    courses.get(i).getDbId(),
                    courses.get(i).getCourseName()
                );
                System.out.println(output);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return courses;
    }
    
    /**
     * This method an array of students with their id, first and last name
     * @return ArrayList<Student> 
     */
    private static ArrayList<Student> listStudents(){
        StudentDb studentDb = new StudentDb();
        ArrayList<Student> students = null;
        
        try {
            students = studentDb.getStudents();
            System.out.println("Student List:");
            String format = "%d: %s %s";
            for (int i = 0; i < students.size() ; i++) {
                String output = String.format(format,
                    students.get(i).getDbId(),
                    students.get(i).getFirstName(),
                    students.get(i).getLastName()
                );
                System.out.println(output);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        return students;
    } 
}
