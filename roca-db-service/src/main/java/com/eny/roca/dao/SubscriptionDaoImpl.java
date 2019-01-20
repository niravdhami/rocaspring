package com.eny.roca.dao;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.eny.roca.db.bean.StatusBean;
import com.eny.roca.db.bean.SubscriptionAssignment;
import com.eny.roca.db.bean.SubscriptionAssignmentBean;
import com.eny.roca.db.bean.SubscriptionBean;
import com.eny.roca.db.bean.SubscriptionDocDetails;

@Repository
public class SubscriptionDaoImpl implements SubscriptionDao {
	
	private static final String SUBSCRIPTION_DOC_TABLE = "rocausers.SubscriptionDocuments";
	
	private static final int IS_ACTIVE_DOC = 0;
	@Autowired
	private NamedParameterJdbcTemplate  namedParameterJdbcTemplate;
	
	@Autowired
	private JdbcTemplate jdbcTemplate;

	
	@Override
	public Integer saveSubscriptionUser(SubscriptionBean subscriptionBean) {
		String query =  "INSERT INTO  rocausers.subscription "
				+ "				 ( LegalEntityName, pseudonym, CountryIncorporation, TaxResidentialStatus, BodyCorporates, "
				+ "				 IsCharitableOrNonProfitable, registationid, EmailId, IndustryId, CompanyHQLocation, Pan, IsPanAttached, PanComments, Gst, GstComments, Url, Address, "
				+ "				 IsEyDisclosureAccepted, Status, PaceId, IsAdditionalDocRequired, IsOnlineEngagedSigned, RoleId,WorkedWithEY,\r\n" + 
								"EYContactPerson1,\r\n" + 
								"EYContactPerson2,\r\n" + 
								"IsRocaServiceAvailed,\r\n" + 
								"RelatedPartyName1,\r\n" + 
								"RelatedPartyName2,\r\n" + 
								"mobileNumber,ContactPerson) "
				+ "				 VALUES (:legalEntityName,:pseudonym,:countryIncorporation,:taxResidentialStatus,:bodyCorporates, :isCharitable,:registrationId,:emailid,:industry,:companyHqLocation,:PAN,:isPanAttached, "
				+ "								:panComments, "
				+ "								:GST, "
				+ "								:gstComments, "
				+ "								:url, "
				+ "								:address, "
				+ "								:isEYDiscloureSigned, "
				+ "								:status, "
				+ "								:paceId, "
				+ "								:isAdditionalDocRequired, "
			 	+ "								:isOnlineEngagementSigned,:roleId,:workedWithEY,:eyContactPerson1,:eyContactPerson2,:rocaServiceAvailed,:relatedPartyName1,:relatedPartyName2,:mobileNumber,:contactPerson)";
		
		Map<String,Object> map = new HashMap<>(1);
		map.put("legalEntityName", subscriptionBean.getLegalEntityName());
		map.put("pseudonym", subscriptionBean.getPseudonym());
		map.put("countryIncorporation", subscriptionBean.getCountryIncorporation());
		map.put("taxResidentialStatus", subscriptionBean.getTaxResidentialStatus());
		map.put("bodyCorporates", subscriptionBean.getBodyCorporates());
		map.put("isCharitable", subscriptionBean.getIsCharitable());
		map.put("registrationId", subscriptionBean.getRegistrationId());
		map.put("emailid", subscriptionBean.getEmailId());
		map.put("industry", subscriptionBean.getIndustryId());
		map.put("companyHqLocation", subscriptionBean.getCompanyHqLocation());
		map.put("PAN", subscriptionBean.getPan());
		map.put("isPanAttached", subscriptionBean.getIsPanAttached());
		map.put("panComments", subscriptionBean.getPanComments());
		map.put("GST", subscriptionBean.getGst());
		map.put("gstComments", subscriptionBean.getGstComments());
		map.put("url", subscriptionBean.getUrl());
		map.put("address", subscriptionBean.getAddress());
		map.put("isEYDiscloureSigned", subscriptionBean.getIsEYDiscloureSigned());
		map.put("status", subscriptionBean.getStatus());
		map.put("paceId", subscriptionBean.getPaceId());
		map.put("isAdditionalDocRequired", subscriptionBean.getIsAdditionalDocRequired());
		map.put("isOnlineEngagementSigned", subscriptionBean.getIsOnlineEngagementSigned());
		map.put("roleId",subscriptionBean.getRoleId()); 
		
		map.put("workedWithEY",subscriptionBean.getWorkedWithEY()); 
		map.put("eyContactPerson1",subscriptionBean.getEyContactPerson1()); 
		map.put("eyContactPerson2",subscriptionBean.getEyContactPerson2()); 
		map.put("rocaServiceAvailed",subscriptionBean.getRocaServiceAvailed()); 
		map.put("relatedPartyName1",subscriptionBean.getRelatedPartyName1()); 
		map.put("relatedPartyName2",subscriptionBean.getRelatedPartyName2()); 
		map.put("mobileNumber",subscriptionBean.getMobileNo());
		map.put("contactPerson", subscriptionBean.getContactPerson());
		int update = namedParameterJdbcTemplate.update(query,map);
		return update;
	}


	@Override
	public Integer userSubscribed(String emailid) {
		Integer queryForObject = jdbcTemplate.queryForObject("select count(*) from subscription where emailid=?", new Object[] {emailid},  Integer.class);
		return queryForObject;
		
	}


	@Override
	public SubscriptionBean fetchUserSubscription(String emailId) {
		String query = ""
				+ "select s.id,s.LegalEntityName,s.Pseudonym,cm.CountryName, "
				+ "        s.TaxResidentialStatus,s.BodyCorporates,s.IsCharitableOrNonProfitable, "
				+ "        s.EmailId,cm1.CountryName cm1CountryName,s.IndustryId ,s.pan,s.IsPanAttached,s.PanComments,s.Gst, "
				+ "        s.GstComments,s.Url,s.Address,s.IsEyDisclosureAccepted,Status,PaceId,s.IsAdditionalDocRequired,IsOnlineEngagedSigned,rr.Name RoleDesc,s.ContactPerson,s.MobileNumber,"
				+ "s.WorkedWithEY,s.EYContactPerson1, s.EYContactPerson2, s.IsRocaServiceAvailed, s.RelatedPartyName1, s.RelatedPartyName2 "
				+ "        from rocausers.Subscription s Left join   rocausers.TransactionSubscribtionDetails ts "
				+ " on s.Id = ts.subscriptionId "
				+ " inner join RocaMaster.Country cm on s.CountryIncorporation = cm.id "
				+ " inner join RocaMaster.Country cm1 on s.CompanyHQLocation = cm1.id "
				+ " inner join RocaMaster.RoleMaster rr on s.RoleId = rr.id "
				+ "and s.emailId=?";
		
		
		List<SubscriptionBean> subscriptionBeans = jdbcTemplate.query(query, new Object[] {emailId},  new SubscriptionDetailsMapper());
		 if(null!=subscriptionBeans &&  !subscriptionBeans.isEmpty()) {
			 return null!=subscriptionBeans.get(0) ? subscriptionBeans.get(0) : null;
		 }else {
			 return null;
		 }
	}


	@Override
	public Integer saveSubscriptionAssignment(List<SubscriptionAssignment> subscriptionBean) {
		List<Integer> update = new ArrayList<Integer>();
		int i = 0;
		for(SubscriptionAssignment s : subscriptionBean) { 
		String query =  "INSERT INTO  rocaserviceteam.SubscriptionAssignment "
				+ "				 (SubscriptionId, FromAssignment, ToAssignment, Comments) "
				+ "				 VALUES (:subscriptionId,:fromAssignment,:toAssignment,:comments)";
		
		Map<String,Object> map = new HashMap<>(1);
		map.put("subscriptionId", s.getId());
		map.put("fromAssignment", s.getFromAssignment());
		map.put("toAssignment", s.getToAssignment());
		map.put("comments", s.getComments());
		 
		String getStatus = "select Status from rocausers.Subscription where rocausers.Subscription.Id=?";
		String queryForFromStatus = jdbcTemplate.queryForObject(getStatus , new Object[] {s.getId()},  String.class);
		String queryForToStatus = null;
		
		if (queryForFromStatus.equalsIgnoreCase("JRREVIEWED")) {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=? and Action=? and Condition=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus, s.getAction(), s.getCondition() }, String.class);
		} else if (queryForFromStatus.equalsIgnoreCase("PACE_CREATED")) {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=? and Action=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus, s.getAction() }, String.class);
		} else {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus , new Object[] {queryForFromStatus},  String.class);
		}
		String query2 =  "UPDATE  rocausers.Subscription SET Status = :status ,UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
		Map<String,Object> map2 = new HashMap<>(1);
		map2.put("status", queryForToStatus);
		map2.put("id", s.getId());
		
		
		update.add(namedParameterJdbcTemplate.update(query,map));
		update.add(namedParameterJdbcTemplate.update(query2,map2));
		}
		if(!update.contains(0)) {
			i =1;
		}
		return i;
	}


	@Override
 	public List<SubscriptionBean> fetchUserSubscriptionStatus(String emailId, String status, Integer subscriptionId) {
		String query = ""
				+ "select s.id,s.LegalEntityName,s.Pseudonym,cm.CountryName, "
				+ "        s.TaxResidentialStatus,s.BodyCorporates,s.IsCharitableOrNonProfitable, "
				+ "        s.EmailId,cm1.CountryName cm1CountryName,s.IndustryId ,s.pan,s.IsPanAttached,s.PanComments,s.Gst, "
				+ "        s.GstComments,s.Url,s.Address,s.IsEyDisclosureAccepted,s.Status,PaceId,s.IsAdditionalDocRequired,IsOnlineEngagedSigned,rr.Name RoleDesc,s.ContactPerson,s.MobileNumber,"
				+ "s.WorkedWithEY,s.EYContactPerson1, s.EYContactPerson2, s.IsRocaServiceAvailed, s.RelatedPartyName1, s.RelatedPartyName2,s.CreatedOn ,s.UpdatedOn "
				+ ",s.IsPaceProcessCompleted        from rocausers.Subscription s Left join   rocausers.TransactionSubscribtionDetails ts "
				+ " on s.Id = ts.subscriptionId "
				+ " inner join RocaMaster.Country cm on s.CountryIncorporation = cm.id "
				+ " inner join RocaMaster.Country cm1 on s.CompanyHQLocation = cm1.id "
				+ " inner join RocaMaster.RoleMaster rr on s.RoleId = rr.id "
				+ "and s.status=?";
		//(s.emailId=?  or s.id=? ) and 
		
		//List<SubscriptionBean> subscriptionBeans = jdbcTemplate.query(query, new Object[] {emailId,subscriptionId,status},  new SubscriptionDetailsMapper());
		return jdbcTemplate.query(query, new Object[] {status},  new SubscriptionDetailsMapper());
	}
	public Integer updateSubscriptionPaceId(List<SubscriptionAssignment> subscriptionBean) {
		
		List<Integer> update = new ArrayList<Integer>();
		int i = 0;
		for(SubscriptionAssignment s : subscriptionBean) { 
		String query =  "INSERT INTO  rocaserviceteam.SubscriptionAssignment "
				+ "				 (SubscriptionId, FromAssignment, ToAssignment, Comments) "
				+ "				 VALUES (:subscriptionId,:fromAssignment,:toAssignment,:comments)";
		
		Map<String,Object> map = new HashMap<>(1);
		map.put("subscriptionId", s.getId());
		map.put("fromAssignment", s.getFromAssignment());
		map.put("toAssignment", s.getToAssignment());
		map.put("comments", s.getComments());
		 
		String getStatus = "select Status from rocausers.Subscription where rocausers.Subscription.Id=?";
		String queryForFromStatus = jdbcTemplate.queryForObject(getStatus , new Object[] {s.getId()},  String.class);
		String queryForToStatus = null;
		
		if (queryForFromStatus.equalsIgnoreCase("JRREVIEWED")) {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=? and Action=? and Condition=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus, s.getAction(), s.getCondition() }, String.class);
		} else if (queryForFromStatus.equalsIgnoreCase("PACE_CREATED")) {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=? and Action=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus, s.getAction() }, String.class);
		} else {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus , new Object[] {queryForFromStatus},  String.class);
		}
		String query2 =  "UPDATE  rocausers.Subscription SET Status = :status, PaceId = :paceid ,UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
		Map<String,Object> map2 = new HashMap<>(1);
		map2.put("status", queryForToStatus);
		map2.put("paceid", s.getPaceId());
		map2.put("id", s.getId());
		
		update.add(namedParameterJdbcTemplate.update(query,map));
		update.add(namedParameterJdbcTemplate.update(query2,map2));
		}
		if(!update.contains(0)) {
			i =1;
		}
		return i;
	}


	@Override
	public Integer updateAdditionalDocRequired(StatusBean statusBean) {
		String query =  "UPDATE  rocausers.subscription SET IsAdditionalDocRequired = :isAdditionalDocRequired where Id = :id";
		Map<String,Object> map = new HashMap<>(1);
		map.put("isAdditionalDocRequired", statusBean.getDocRequired());
		map.put("id", statusBean.getId()); 
		int update = namedParameterJdbcTemplate.update(query,map);
		return update;
	}


	@Override
	public Integer updateStatus(List<StatusBean> statusBean) {
		List<Integer> update = new ArrayList<Integer>();
		int i = 0;
		for(StatusBean s : statusBean) { 
		String getStatus = "select Status from rocausers.Subscription where rocausers.Subscription.Id=?";
		String queryForFromStatus = jdbcTemplate.queryForObject(getStatus , new Object[] {s.getId()},  String.class);
		String queryForToStatus = null;
		
		if (queryForFromStatus.equalsIgnoreCase("JRREVIEWED")) {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=? and Action=? and Condition=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus, s.getAction(), s.getCondition() }, String.class);
		} else if (queryForFromStatus.equalsIgnoreCase("PACE_CREATED")) {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=? and Action=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus, new Object[] { queryForFromStatus, s.getAction() }, String.class);
		} else {
			String ToStatus = "select ToState from rocaserviceteam.SubscriptionStatusMaster where fromstate=?";
			queryForToStatus = jdbcTemplate.queryForObject(ToStatus , new Object[] {queryForFromStatus},  String.class);
		}
		String query =  "UPDATE  rocausers.Subscription SET Status = :status ,UpdatedOn = CURRENT_TIMESTAMP where Id = :id";
		Map<String,Object> map = new HashMap<>(1);
		map.put("status", queryForToStatus);
		map.put("id", s.getId());
		update.add(namedParameterJdbcTemplate.update(query,map));
		}
		if(!update.contains(0)) {
			i =1;
		}
		return i;
 	}
	
	@SuppressWarnings("unchecked")
	@Override
	public SubscriptionDocDetails saveFile(SubscriptionBean subscriptionBean) throws IOException {
		
		String docname = subscriptionBean.getDocName();
		String email = subscriptionBean.getEmail();
		String doctype = subscriptionBean.getDocType().toString();
		byte[] subDoc = subscriptionBean.getDocData().getBytes();
		String docExtention = subscriptionBean.getDocExtention();
		int docIsActive = subscriptionBean.getIs_valid_doc();
		int subscriptionId = subscriptionBean.getSubscriptionId();
		
		jdbcTemplate.update("insert into "+SUBSCRIPTION_DOC_TABLE+" (SubscriptionId,DocName,type, emailid,file_extention, doc_data,isActive) values (?,?,?,?,?,?,?)",subscriptionId,docname,doctype,email, docExtention,subDoc,docIsActive);
		
		return new SubscriptionDocDetails(docname, docExtention,subDoc.length);
		 
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<SubscriptionDocDetails> getFile(String fileName) throws IOException{
		List<SubscriptionDocDetails> fileData = jdbcTemplate.query("select * from "+SUBSCRIPTION_DOC_TABLE+" where DocName = '"+fileName+"' and isActive = 1",new SubscriptionDataMapper());
		return fileData;
	}
	
	
	@Override
 	public  SubscriptionBean  fetchSubscriptionDetailsById(Integer subscriptionId) {
		String query = ""
				+ "select s.id,s.LegalEntityName,s.Pseudonym,cm.CountryName, "
				+ "        s.TaxResidentialStatus,s.BodyCorporates,s.IsCharitableOrNonProfitable, "
				+ "        s.EmailId,cm1.CountryName cm1CountryName,s.IndustryId ,s.pan,s.IsPanAttached,s.PanComments,s.Gst, "
				+ "        s.GstComments,s.Url,s.Address,s.IsEyDisclosureAccepted,s.Status,PaceId,s.IsAdditionalDocRequired,IsOnlineEngagedSigned,rr.Name RoleDesc,s.ContactPerson,s.MobileNumber,"
				+ "s.WorkedWithEY,s.EYContactPerson1, s.EYContactPerson2, s.IsRocaServiceAvailed, s.RelatedPartyName1, s.RelatedPartyName2,s.CreatedOn ,s.UpdatedOn "
				+ ",s.IsPaceProcessCompleted        from rocausers.Subscription s Left join   rocausers.TransactionSubscribtionDetails ts "
				+ " on s.Id = ts.subscriptionId "
				+ " inner join RocaMaster.Country cm on s.CountryIncorporation = cm.id "
				+ " inner join RocaMaster.Country cm1 on s.CompanyHQLocation = cm1.id "
				+ " inner join RocaMaster.RoleMaster rr on s.RoleId = rr.id "
				+ "and s.id=?";
		
		return jdbcTemplate.queryForObject(query, new Object[] {subscriptionId},  new SubscriptionDetailsMapper());
	}
	
	@Override
	public List<SubscriptionAssignmentBean> getSubscriptionAssignments(Integer subscriptionId) {
		return jdbcTemplate.query("select subscriptionid,fromassignment,toassignment,comments from rocaserviceteam.SubscriptionAssignment where subscriptionid = ? AND IsActive = 1 order by createdon\r\n"
				, new Object[] {subscriptionId},  new SubscriptionAssignmentMapper());

		
	}

	@Override
	public Integer validateMobileNo(Long mobileNo) {
		Integer queryForObject = jdbcTemplate.queryForObject("select count(*) from rocausers.Subscription where mobileNumber=?", new Object[] {mobileNo},  Integer.class);
		return queryForObject;
	}
}
