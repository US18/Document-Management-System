package com.example.uplabdhisingh.docufy.constants;

public class Uploads
{
    public String facultyName;
    public String courseName;
    public String fileName;
    public String sessionName;
    public String semesterName;
    public String url;

    public boolean selected = false;

    public Uploads()
    { }

    public Uploads(String facultyName,String course, String fileName,String semesterName, String session,String url,boolean selected)
    {
        this.facultyName=facultyName;
        this.courseName = course;
        this.fileName = fileName;
        this.semesterName=semesterName;
        this.sessionName = session;
        this.url=url;
        this.selected = selected;
    }

    public String getFacultyName()
    {
        return facultyName;
    }

    public String getCourse() {
        return courseName;
    }

    public String getName()
    {
        return fileName;
    }

    public String getSemesterName()
    {
        return semesterName;
    }

    public String getSession()
    {
        return sessionName;
    }

    public String getUrl()
    {
        return url;
    }

    public boolean isSelected()
    {
        return selected;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

}
