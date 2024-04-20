package com.college.student.pojo;

import com.college.student.repository.constants.AddressType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "ADDRESS")
public class Address implements Cloneable, Serializable, Comparable<Address> {

    public Address (Integer addressID, String country, String state, String city, AddressType addressType) {
        this.addressID = addressID;
        this.country = country;
        this.state = state;
        this.city = city;
        this.addressType = addressType;
    }

    private static final long serialVersionUID = 25235232423L;
    @Column(name = "ADDRESS_ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int addressID;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "STATE")
    private String state;

    @Column(name = "CITY")
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "ADDRESS_TYPE")
    private AddressType addressType;

    @ManyToOne()
    @JoinColumn(name = "STUDENT_ROLL_NO",referencedColumnName = "ROLL_NO")
    private Student student;

    public int getAddressID() {
        return addressID;
    }

    public void setAddressID(int addressID) {
        this.addressID = addressID;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return "Address{" +
                "country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", rollNo=" + +
                '}';
    }

    @Override
    public int compareTo(Address o) {
        return 0;
    }
//
//    @Override
//    public int compareTo(Address o) {
//        return Integer.compare(rollNo, ((Address) o).rollNo);
//    }
}
