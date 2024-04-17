package com.college.student.sort;

import com.college.student.pojo.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;

public class StudentAgeAndGenderComparator implements Comparator<Student> {
    private static final Logger logger = LoggerFactory.getLogger(StudentAgeAndGenderComparator.class);

    @Override
    public int compare(Student student1, Student student2) {
//        logger.info("Comparator invokes to sort list");
//        if (student1.getAge() > student2.getAge() && student1.getGender().equals("FEMALE")
//                && !student2.getGender().equals("FEMALE")) return -1;
//        if (student1.getAge() > student2.getAge() && student1.getGender().equals("MALE")
//                && !student2.getGender().equals("MALE")) return -1;
//        if (student1.getAge() > student2.getAge() && student1.getGender().equals("OTHER")) return -1;
//        return Byte.compare(student1.getAge(), student2.getAge());
        return 0;
    }
}
