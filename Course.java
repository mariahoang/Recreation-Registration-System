/*
 * This Course class represents the Course table's field names and
 * includes two constructors and the getters and setters for each field
 */
package student.course;

import java.sql.Time;

/**
 *
 * @author Maria Hoang
 */
public class Course {
    
    private int dbId;
    private String courseName;
    private Time startTime;

    /**
     * Course constructor that takes in three parameters
     * @param dbId
     * @param courseName
     * @param startTime 
     */
    public Course(int dbId, String courseName, Time startTime) {
        this.dbId = dbId;
        this.courseName = courseName;
        this.startTime = startTime;
    }

    /**
     * Course constructor that takes in two parameters
     * @param courseName
     * @param startTime 
     */
    public Course(String courseName, Time startTime) {
        this.courseName = courseName;
        this.startTime = startTime;
    }

    /**
     * Below are the getters for each field in Course table
     */
    
    /**
     * 
     * @return dbId 
     */
    public int getDbId() {
        return dbId;
    }

    /**
     * 
     * @return course name
     */
     public String getCourseName() {
        return courseName;
    }
     
    /**
     * 
     * @return course start time
     */
    public Time getStartTime() {
        return startTime;
    } 
    
    /**
     * Below are the setters for each field in Course table
     */
    
    /**
     * 
     * @param dbId 
     */
    public void setDbId(int dbId) {
        this.dbId = dbId;
    }

    /**
     * 
     * @param courseName 
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * 
     * @param startTime 
     */
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    
}
