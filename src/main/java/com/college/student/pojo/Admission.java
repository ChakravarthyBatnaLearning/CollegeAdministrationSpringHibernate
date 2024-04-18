package com.college.student.pojo;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "admission")
public class Admission implements Cloneable, Serializable, Comparable<Admission> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ADMISSION_ID")
    private int admissionId;
    @Column(name = "ROLL_NO")
    private int rollNo;

    @Column(name = "COURSE")
    private String course;

    @Column(name = "SECTION")
    private int section;

    @Column(name = "ADMISSION_YEAR")
    private int admissionYear;

    @OneToOne
    @JoinColumn(name = "STUDENT_ROLL_NO")
    private Student student;
    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public int getAdmissionId() {
        return admissionId;
    }

    public void setAdmissionId(int admissionId) {
        this.admissionId = admissionId;
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

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    @Override
    public String toString() {
        return "Admission{" +
                "course='" + course + '\'' +
                ", section=" + section +
                ", admissionYear=" + admissionYear +
                ", rollNo=" + +
                '}';
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public int compareTo(Admission o) {
        return 0;
    }

//    @Override
//    public boolean equals(Object object) {
//        if (this == object) return true;
//        if (object == null || getClass() != object.getClass()) return false;
//        Admission admission = (Admission) object;
//        return section == admission.section && admissionYear == admission.admissionYear && rollNo == admission.rollNo && Objects.equals(course, admission.course);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(course, section, admissionYear, rollNo);
//    }
//
//    @Override
//    public int compareTo(Admission o) {
//        return Integer.compare(o.getRollNo(), rollNo);
//    }
}
