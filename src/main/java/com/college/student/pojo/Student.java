package com.college.student.pojo;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//POJO-plain old java object's
//it's to store the student data;
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "student")
public class Student implements Serializable, Comparable<Student>, Cloneable {

    public Student(Integer rollNo, String name, Byte age, Long phoneNo,String gender) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }
    @Id
    @Column(name = "ROLL_NO")
    private int rollNo;

    @Column(name = "NAME")
    private String name;

    @Column(name = "AGE")
    private byte age;

    @Column(name = "PHONE_NUMBER")
    private long phoneNo;

    @Column(name = "GENDER")
    private String gender;

    @OneToMany(targetEntity = Address.class, mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Address> addressList = new ArrayList<>();

    @OneToOne(targetEntity = Admission.class, mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Admission admission;


    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phoneNo=" + phoneNo +
                '}';
    }

    @Override
    public int compareTo(Student student) {

        return 0;
    }


    @Override
    public int hashCode() {
        return Objects.hash(rollNo, name, age, phoneNo, gender);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}
