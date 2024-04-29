package com.college.student.pojo;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "admission")
public class Admission implements Cloneable, Serializable, Comparable<Admission> {

    private int rollNo;
    @Column(name = "ADMISSION_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int admissionID;

    @Column(name = "COURSE")
    private String course;

    @Column(name = "SECTION")
    private int section;

    @Column(name = "ADMISSION_YEAR")
    private int admissionYear;

    @JoinColumn(name = "STUDENT_ROLL_NO", referencedColumnName = "ROLL_NO")
    @OneToOne()
    private Student student;

    public Admission() {
    }  //no args

    public Admission(Integer admissionID, String course, Integer section, Integer admissionYear, Integer rollNo) {
        this.rollNo = rollNo;
        this.admissionID = admissionID;
        this.course = course;
        this.section = section;
        this.admissionYear = admissionYear;
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public int getAdmissionID() {
        return admissionID;
    }

    public void setAdmissionID(int admissionID) {
        this.admissionID = admissionID;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public int getSection() {
        return section;
    }

    public void setSection(int section) {
        this.section = section;
    }

    public int getAdmissionYear() {
        return admissionYear;
    }

    public void setAdmissionYear(int admissionYear) {
        this.admissionYear = admissionYear;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    public String toString() {
        return "Admission{" +
                "rollNo=" + rollNo +
                ", admissionID=" + admissionID +
                ", course='" + course + '\'' +
                ", section=" + section +
                ", admissionYear=" + admissionYear +
                ", student=" + student +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Admission admission = (Admission) object;
        return rollNo == admission.rollNo && admissionID == admission.admissionID && section == admission.section && admissionYear == admission.admissionYear && Objects.equals(course, admission.course) && Objects.equals(student, admission.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rollNo, admissionID, course, section, admissionYear, student);
    }


    @Override
    public int compareTo(Admission o) {
        return Integer.compare(this.admissionID, o.getAdmissionID());
    }
}
