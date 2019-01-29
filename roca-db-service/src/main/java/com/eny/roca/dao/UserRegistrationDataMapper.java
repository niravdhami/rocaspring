package com.eny.roca.dao;

import java.sql.ResultSet;

import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import com.eny.roca.db.bean.UserRegistration;

public class UserRegistrationDataMapper implements RowMapper<UserRegistration> {
	 
		@Override
		public UserRegistration mapRow(ResultSet rs, int arg1) throws SQLException {
			UserRegistration userRegistrationData = new UserRegistration();
			userRegistrationData.setLegalEntityName(rs.getString("LegalEntityName"));
			userRegistrationData.setContactPerson(rs.getString("contactperson"));
			userRegistrationData.setRoleId(Integer.parseInt(rs.getString("roleId")));
			userRegistrationData.setEmailId(rs.getString("emailid"));
			userRegistrationData.setMobileNo(Long.parseLong(rs.getString("mobileNumber")));
			userRegistrationData.setIndustryId(rs.getString("industryId"));
			userRegistrationData.setCountryCode(Integer.parseInt(rs.getString("CountryId")));
			if(rs.getString("isemailverified") != null) {
			userRegistrationData.setIsEmailVerified(Integer.parseInt(rs.getString("isemailverified")));
			}
			if(rs.getString("ismobileverified") != null) {
			userRegistrationData.setIsMobileVerified(Integer.parseInt(rs.getString("ismobileverified")));
			}
			return userRegistrationData;
		}
}
