/*
 * This class is a helper class for Course class to access the Course table
 */
package student.course.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import student.course.Course;

/**
 *
 * @author Maria Hoang
 */
public class CourseDb {
    
    /**
     * Strings for the database connection
     */
    String driver = "com.mysql.jdbc.Driver";
    String connUrl = "jdbc:mysql://localhost/";
    String database = "oakvillerec";
    String user = "root";
    String pass = "PROG32758";
    
    /**
     * These constants represent the field names in the Student table
     */
    private final String TABLE_NAME = "course";
    private final String ID = "id";
    private final String COURSE_NAME = "name";
    private final String START_TIME = "startTime";
    
    /**
     * This method will return an integer to validate if the query 
     * was successful or not
     * @param course
     * @return int
     */
    public int addCourse(Course course){
        String formatSql = "INSERT INTO %s (%s, %s) VALUES (?, ?)";
        String sql = String.format(formatSql,TABLE_NAME,COURSE_NAME,START_TIME);
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        int result = 0;
        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database,user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, course.getCourseName());
            ps.setTime(2, course.getStartTime());
            
            result = ps.executeUpdate();
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally{
            DBConnector.closeJDBCObjects(conn, ps);
        }
        return result;
        
    }
    
    /**
     * This method will return an integer to validate if the query 
     * was successful or not for removing a course from CourseDb
     * @param courseId
     * @return int
     */    
    public int removeCourse(String courseId){
        String formatSql = "DELETE FROM %s WHERE %s = ?";
        String sql = String.format(formatSql,TABLE_NAME,ID);
        
        Connection conn = null;
        PreparedStatement ps = null;
        
        int result = 0;     
        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            ps.setString(1, courseId);

            result = ps.executeUpdate();
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally{
            DBConnector.closeJDBCObjects(conn, ps);
        }
        return result;
        
    }
    
    /**
     * This method will return an array of courses that contains the detail
     * of each course
     * @return an ArrayList<Course> 
     */
    public ArrayList<Course> getCourses(){  
        String formatSql = "SELECT * FROM %s";
        String sql = String.format(formatSql, TABLE_NAME);
        
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        ArrayList<Course> courses = new ArrayList<>();
        
        try {
            conn = DBConnector.getConnection(
                driver, connUrl, database, user, pass
            );
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            
            while(rs.next()){
                Course course = new Course(
                rs.getInt(ID),
                rs.getString(COURSE_NAME),
                rs.getTime(START_TIME));
                courses.add(course);
            }
            
        } catch (Exception ex) {
            System.out.println(ex.toString());
        } finally{
            DBConnector.closeJDBCObjects(conn, ps, rs);
        }
        return courses;
    }
    
}
