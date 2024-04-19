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
@Getter
@Setter
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
    @JoinColumn(name = "STUDENT_ROLL_NO",referencedColumnName = "ROLL_NO")
    private Student student;


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
