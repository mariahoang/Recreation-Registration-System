/*
 * This class is a helper class for Student class to access the Student table
 */
package student.course.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import student.course.Student;

/**
 *
 * @author Maria Hoang
 */
public class StudentDb {
    
    /**
     * Strings for the database connection
     */
    String driver = "com.mysql.jdbc.Driver";
    String connUrl = "jdbc:mysql://localhost/";
    String database = "recreation";
    String user = "root";
    String pass = "PROG32758";
    
    /**
     * These constants represent the field names in the Student table
     */
    private final String TABLE_NAME = "student";
    private final String ID = "id";
    private final String FIRST_NAME = "firstName";
    private final String LAST_NAME = "lastName";
    private final String AGE = "age";
    
    /**
     * This method will return a positive integer if the query was successful
     * and the student was added, otherwise an error message will be displayed.
     * This method takes in a student object as a parameter.
     * @param student
     * @return an integer to validate if the query was successful or not
     * @throws java.lang.Exception 
    */
    public int addStudent(Student student) throws Exception{
        String formatSql = "INSERT INTO %s (%s, %s, %s) VALUES (?, ?, ?)";
        String sql = String.format(formatSql,TABLE_NAME,FIRST_NAME,LAST_NAME,
            AGE);
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        int result = 0;
        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, student.getFirstName());
            ps.setString(2, student.getLastName());
            ps.setInt(3, student.getAge());
            
            result = ps.executeUpdate(); 
        } catch (Exception ex) {
            throw(ex);
        } finally{
            DBConnector.closeJDBCObjects(conn, ps);
        }
        return result;
        
    }
    
    /**
     * This method will return a positive integer if the query was successful
     * and the student was removed, otherwise an error message will be displayed.
     * This method takes in a student object as a parameter.
     * @param studentId
     * @return an integer to validate if the query was successful or not
     * @throws Exception 
     */
    public int removeStudent(String studentId) throws Exception{
        String sqlQuery = "DELETE FROM %s WHERE %s = ?";
        String formatSql = String.format(sqlQuery, TABLE_NAME, ID);
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        int result = 0;  
        try { 
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(formatSql);
            ps.setString(1, studentId);
            
            result = ps.executeUpdate();  
        } catch (Exception ex) {
            throw(ex);
        } finally{
            DBConnector.closeJDBCObjects(conn, ps);
        }
        return result;
        
    }
    
    /**
     * This method will return an array of student and their details
     * @return an ArrayList<Student> 
     */
    public ArrayList<Student> getStudents(){
        String formatSql = "SELECT * FROM %s";
        String sql = String.format(formatSql, TABLE_NAME);
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList<Student> students = new ArrayList<>();
        
        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Student student = new Student(
                    rs.getInt(ID),
                    rs.getString(FIRST_NAME),
                    rs.getString(LAST_NAME),
                    rs.getInt(AGE)
                );
                students.add(student);
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally {
            DBConnector.closeJDBCObjects(conn, ps, rs);
        }
        return students;
        
    }

}
