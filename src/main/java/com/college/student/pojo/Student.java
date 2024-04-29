package com.college.student.pojo;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

//POJO-plain old java object's
//it's to store the student data;
@Entity
@Table(name = "student")
public class Student implements Serializable, Comparable<Student>, Cloneable {

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

    public Student(Integer rollNo, String name, Byte age, Long phoneNo, String gender) {
        this.rollNo = rollNo;
        this.name = name;
        this.age = age;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }

    public Student() {
    }

    public int getRollNo() {
        return rollNo;
    }

    public void setRollNo(int rollNo) {
        this.rollNo = rollNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte getAge() {
        return age;
    }

    public void setAge(byte age) {
        this.age = age;
    }

    public long getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(long phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }

    public Admission getAdmission() {
        return admission;
    }

    public void setAdmission(Admission admission) {
        this.admission = admission;
    }

    @Override
    public String toString() {
        return "Student{" +
                "rollNo=" + rollNo +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", phoneNo=" + phoneNo +
                ", gender='" + gender + '\'' +
                ", addressList=" + addressList +
                ", admission=" + admission +
                '}';
    }

    @Override
    public int compareTo(Student student) {
        return Integer.compare(this.rollNo, student.getRollNo());
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Student student = (Student) object;
        return rollNo == student.rollNo && age == student.age && phoneNo == student.phoneNo && Objects.equals(name, student.name) && Objects.equals(gender, student.gender);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rollNo, name, age, phoneNo, gender);
    }
}
