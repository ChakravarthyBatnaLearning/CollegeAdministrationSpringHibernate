package com.college.student.pojo;

import com.college.student.repository.constants.AddressType;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "ADDRESS")
public class Address implements Cloneable, Serializable, Comparable<Address> {

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
    @JoinColumn(name = "STUDENT_ROLL_NO", referencedColumnName = "ROLL_NO")
    private Student student;


    public Address() {
    }

    public Address(Integer addressID, String country, String state, String city, AddressType addressType) {
        this.addressID = addressID;
        this.country = country;
        this.state = state;
        this.city = city;
        this.addressType = addressType;
    }

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
                "addressID=" + addressID +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", city='" + city + '\'' +
                ", addressType=" + addressType +
                ", student=" + student +
                '}';
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        Address address = (Address) object;
        return addressID == address.addressID && Objects.equals(country, address.country) && Objects.equals(state, address.state) && Objects.equals(city, address.city) && addressType == address.addressType && Objects.equals(student, address.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(addressID, country, state, city, addressType, student);
    }

    @Override
    public int compareTo(Address o) {
        return Integer.compare(addressID, o.getAddressID());
    }

}
