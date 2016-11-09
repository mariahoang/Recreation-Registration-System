/*
 * This Student class represents the Student table's field names and
 * includes two constructors and the getters and setters for each field.
 */
package student.course;

/**
 *
 * @author Maria Hoang
 */
public class Student {

    private int dbId;
    private String firstName;
    private String lastName;
    private int age;

    /**
     * Student constructor that takes in four parameters
     * @param studentId
     * @param firstName
     * @param lastName
     * @param age 
     */
    public Student(int studentId, String firstName, String lastName, int age) {
        this.dbId = studentId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
     * Student constructor that takes in three parameters
     * @param firstName
     * @param lastName
     * @param age 
     */
    public Student(String firstName, String lastName, int age) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    /**
    * Below are the getters for each field in Student table
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
     * @return Student's First Name
     */
    public String getFirstName() {
        return firstName;
    }
    
    /**
     * 
     * @return Student's Last Name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * 
     * @return Student's Age
     */
    public int getAge() {
        return age;
    }
    
    /**
    * Below are the setters for each field in Student table
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
     * @param firstName 
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * 
     * @param lastName 
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * 
     * @param age 
     */
    public void setAge(int age) {
        this.age = age;
    }
    
}
