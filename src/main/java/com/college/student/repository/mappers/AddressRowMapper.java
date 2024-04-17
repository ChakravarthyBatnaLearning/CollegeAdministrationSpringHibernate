package com.college.student.repository.mappers;

import com.college.student.pojo.Address;
import com.college.student.repository.constants.AddressConstants;
import com.college.student.repository.constants.AddressType;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AddressRowMapper implements RowMapper<Address> {

    @Override
    public Address mapRow(ResultSet rs, int rowNum) throws SQLException {
        Address address = new Address();
        address.setCountry(rs.getString(AddressConstants.COUNTRY.toString()));
        address.setState(rs.getString(AddressConstants.STATE.toString()));
        address.setCity(rs.getString(AddressConstants.CITY.toString()));
        address.setRollNo(rs.getInt(AddressConstants.ROLL_NO.toString()));
        address.setAddressType(AddressType.valueOf(rs.getString(AddressConstants.ADDRESS_TYPE.toString())));
        return address;
    }
}
