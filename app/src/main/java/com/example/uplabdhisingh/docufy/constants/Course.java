package com.example.uplabdhisingh.docufy.constants;

public class Course
{
    public String courseName1;
    public String courseID1;

    public String courseName2;
    public String courseID2;

    public String courseName3;
    public String courseID3;

    public Course(String courseName1, String courseID1, String courseName2, String courseID2, String courseName3, String courseID3)
    {
        this.courseName1 = courseName1;
        this.courseID1 = courseID1;
        this.courseName2=courseName2;
        this.courseID2=courseID2;
        this.courseName3=courseName3;
        this.courseID3=courseID3;
    }

    public String getCourseName() {
        return courseName1;
    }

    public String getCourseID() {
        return courseID1;
    }

    public void setCourseName(String courseName) {
        this.courseName1 = courseName;
    }

    public void setCourseID(String courseID) {
        this.courseID1 = courseID;
    }
}
