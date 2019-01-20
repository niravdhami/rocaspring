package com.eny.roca.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.multipart.MultipartFile;

import com.eny.roca.db.bean.SubscriptionDocDetails;
import com.eny.roca.db.bean.DocType;

public class SubscriptionDataMapper implements RowMapper<SubscriptionDocDetails> {
 
	@Override
	public SubscriptionDocDetails mapRow(ResultSet rs, int arg1) throws SQLException {
		SubscriptionDocDetails subscriptionDocDetails = new SubscriptionDocDetails();
		byte[] data = rs.getBytes("doc_data");
		subscriptionDocDetails.setFileData(data);
		return subscriptionDocDetails;
	}

}
