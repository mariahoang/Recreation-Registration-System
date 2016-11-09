/*
 * This class is a helper class for StudentCourse class to access the 
 * StudentCourse table
 */
package student.course.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author Maria Hoang
 */
public class StudentCourseDb {
    
    /**
     * Strings for the database connection
     */
    String driver = "com.mysql.jdbc.Driver";
    String connUrl = "jdbc:mysql://localhost/";
    String database = "recreation";
    String user = "root";
    String pass = "PROG32758";
    
    /**
     * These constants represent the field names in the Student table and
     * the max number of students allowed for a course
     */
    private final String TABLE_NAME = "studentcourse";
    private final String STUDENT_ID = "studentId";
    private final String COURSE_ID = "courseId";
    private final static int MAX_STUDENTS = 20;
   
    /**
     * This method will assign a specific student to a specific course
     * based on the user input
     * @param studentId
     * @param courseId
     * @return a positive number if the student was successfully enrolled,
     * otherwise it will return a negative number for failing to enroll the 
     * student
     */
    public int assignStudentToCourse(String studentId, String courseId){
        String formatSql = "INSERT INTO %s (%s, %s) VALUES (?, ?)";
        String sql = String.format(formatSql,
            TABLE_NAME,
            STUDENT_ID,
            COURSE_ID
        );
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        int result = 0;
        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            result = ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Duplicate entry!");
        } finally {
            DBConnector.closeJDBCObjects(conn, ps);
        }
        return result;
    }
    
    /**
     * This method will remove a specific student from all the course(s) the
     * student was previously enrolled in
     * @param studentId
     * @param courseId
     * @return a positive number if the student was successfully unenrolled,
     * otherwise it will return a negative number for failing to un-enroll the 
     * student
     */
    public int removeStudentFromCourse(String studentId, String courseId){
        String formatSql = "DELETE FROM %s "
            + "WHERE studentId = ? "
            + "AND courseId = ?"
        ;
        String sql = String.format(formatSql,TABLE_NAME,STUDENT_ID,COURSE_ID);
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        int result = 0;
        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            ps.setString(2, courseId);
            result = ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: Could not remove Student.");
        } finally {
            DBConnector.closeJDBCObjects(conn, ps);
        }
        return result;
    }
    
    /**
     * This method will list the students enrolled in a specific course
     * @param courseName
     * @throws Exception 
     */
    public void listStudentsByCourse(String courseName) throws Exception{
        String sql = "SELECT * FROM studentcourse "
            + "JOIN student ON student.id = studentcourse.studentId "
            + "JOIN course ON course.id = studentcourse.courseId "
            + "WHERE course.name = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        System.out.println("Students enrolled in " + courseName +  ":");

        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseName);
            rs = ps.executeQuery();
            
            if(!rs.next())
                System.out.println("There are no Students enrolled in "
                    + courseName);
            else{
                rs.beforeFirst();
                while(rs.next())
                    System.out.println(rs.getString("firstName") + " " 
                        + rs.getString("lastName")
                    );
            }
        } catch (Exception ex) {
            throw(ex);
        } finally{
            DBConnector.closeJDBCObjects(conn, ps, rs);
        }
        
    }
    
    /**
     * This method will list the students enrolled in a specific course
     * @param courseId
     * @throws Exception 
     */
    public void listStudentsByCourseId(String courseId) throws Exception{
        String sql = "SELECT * FROM studentcourse "
                + "JOIN student ON student.id = studentcourse.studentId "
                + "JOIN course ON course.id = studentcourse.courseId "
                + "WHERE course.id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseId);
            rs = ps.executeQuery();
            
            if(!rs.next())
                System.out.println("There are no Students enrolled in "
                    + courseId);
            else{
                rs.beforeFirst();
                while(rs.next())
                    System.out.println(rs.getString("firstName") + " " 
                        + rs.getString("lastName")
                    );
            }
        } catch (Exception ex) {
            throw(ex);
        } finally{
            DBConnector.closeJDBCObjects(conn, ps, rs);
        }
        
    }
   
    /**
     * This method takes in an int (studentId) and lists the courses associated
     * with the studentId number
     * @param studentId
     * @throws Exception 
     */
    public void listCoursesOfStudent(int studentId) throws Exception{
         String sql = "SELECT * FROM studentcourse "
                + "JOIN student ON student.id = studentcourse.studentId "
                + "JOIN course ON course.id = studentcourse.courseId "
                + "WHERE student.id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setInt(1, studentId);
            rs = ps.executeQuery();
            
            if(!rs.next()){
                System.out.println("Student is not enrolled in any course.\n");
            }else{
                rs.beforeFirst();
                System.out.println("Student is enrolled in:");
                while(rs.next()){
                    System.out.println(rs.getString("course.id") + ": "
                        + rs.getString("name")
                    );
                }
            }
        } catch (Exception ex) {
            throw(ex);
        } finally{
            DBConnector.closeJDBCObjects(conn, ps, rs);
        }
        
    }

    /**
     * This method will check to see if course is available 
     * for student to enroll based on the constant MAX_STUDENTS
     */
    public void courseAvailability(){
        String sql = "SELECT MAX(course.name) AS CourseName, "
            + "COUNT(studentcourse.studentId) AS NumStudents, " 
            + "course.id AS CourseId "    
            + "FROM course " 
            + "LEFT JOIN studentcourse ON studentcourse.courseId=course.id "
            + "GROUP BY course.id ";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            if(!rs.next())
                System.out.println("There are no Students enrolled");
            else{
                rs.beforeFirst();
                while(rs.next()){
                    int numStudents = Integer.parseInt(
                        rs.getString("NumStudents")
                    );
                    if(numStudents <= MAX_STUDENTS){
                        System.out.println(rs.getString("CourseId") + ": "
                            + rs.getString("CourseName")
                        );
                    }
                }
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally{
            DBConnector.closeJDBCObjects(conn, ps, rs);
        }
    }
    
    /**
     * This method will take in the courseId(String) and will return true if the
     * course is empty and false otherwise
     * @return boolean result
     */
    public boolean emptyCourse(String courseId){
        String sql = "SELECT * FROM studentcourse "
                + "JOIN student ON student.id = studentcourse.studentId "
                + "JOIN course ON course.id = studentcourse.courseId "
                + "WHERE course.id = ?";
        
        boolean result = false;
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
         try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseId);
            rs = ps.executeQuery();
            
            if(!rs.next())
                result = true;
            else{
                result = false;
            }

        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally{
            DBConnector.closeJDBCObjects(conn, ps, rs);
        }
        return result;
    }

    /**
     * This method takes in studentId(String) and will return a positive number
     * if the student was removed from all courses and return a negative 
     * number otherwise
     * @param studentId
     * @return int
     * @throws Exception 
     */
    public int removeStudentFromAllCourses(String studentId) throws Exception {
        String formatSql = "DELETE FROM %s "
            + "WHERE studentId = ?"
        ;
        String sql = String.format(formatSql,TABLE_NAME,STUDENT_ID);
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        int result = 0;
        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            result = ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println("Error: Could not remove Student.");
        } finally {
            DBConnector.closeJDBCObjects(conn, ps);
        }
        return result;
    }
    
    /**
     * This method takes in an String (studentId) and return true if the
     * student is enrolled in any courses and false otherwise
     * @param studentId
     * @throws Exception 
     */
    public boolean checkStudentEnrollment(String studentId) throws Exception{
         String sql = "SELECT * FROM studentcourse "
                + "JOIN student ON student.id = studentcourse.studentId "
                + "JOIN course ON course.id = studentcourse.courseId "
                + "WHERE student.id = ?";
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        boolean result = true;

        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, studentId);
            rs = ps.executeQuery();
            
            if(!rs.next()){
                System.out.println("Student is not enrolled in any course.\n");
                result = false;
            }else
                result = true;
        } catch (Exception ex) {
            throw(ex);
        } finally{
            DBConnector.closeJDBCObjects(conn, ps, rs);
        }
        return result;
    }
    
}
