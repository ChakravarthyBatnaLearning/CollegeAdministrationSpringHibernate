package com.college.student.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "admission")
public class Admission implements Cloneable, Serializable, Comparable<Admission>

{
    public Admission(Integer admissionID,String course, Integer section, Integer admissionYear,Integer rollNo) {
        this.rollNo = rollNo;
        this.admissionID = admissionID;
        this.course = course;
        this.section = section;
        this.admissionYear = admissionYear;
    }
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
}
