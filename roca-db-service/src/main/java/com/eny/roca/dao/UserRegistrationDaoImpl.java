package com.eny.roca.dao;
import java.security.SecureRandom;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.eny.roca.db.bean.OtpBean;
import com.eny.roca.db.bean.UserRegistration;
import com.eny.roca.db.services.RandomString;
import com.eny.roca.db.services.SmtpMailSender;
import com.eny.roca.db.services.ValidateEmail;

@Transactional
@Repository
public class UserRegistrationDaoImpl implements UserRegistrationDao {
	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private SmtpMailSender smtpMailSender;

	@Override
	public UserRegistration getUserRegistrationId(int Id) {
		return entityManager.find(UserRegistration.class, Id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<UserRegistration> getAllRegistration() {
		Query query = entityManager.createNativeQuery("SELECT * FROM roca_users_roca_user_registration",
				UserRegistration.class);
		return (List<UserRegistration>) query.getResultList();
	}

	@Override
	public Integer setUserRegistrationId(UserRegistration registrationBean) {
		int i = 0;
		String ran = RandomString.digits + "ACEFGHJKLMNPQRUVWXYabcdefhijkprstuvwx" + RandomString.specialCharacter;
		RandomString autoGenPassword = new RandomString(8, new SecureRandom(), ran);

		String password = autoGenPassword.nextString();
		if (!ObjectUtils.isEmpty(registrationBean) && !entityManager.contains(registrationBean)) {
			try {
				entityManager.persist(registrationBean);
				i = 1;
				String link = "http://localhost:4200/login/email-verify?emailId=" + registrationBean.getEmailId();
				StringBuilder sb = new StringBuilder();
				sb.append("<html><head> WELCOME TO ROCA Services, <head> <body><br />   Activation Link :  <a href=\""
						+ link
						+ "\">Verify EmailId Here</a><br /><br /> You can Login to ROCA site from below Credentials <br />");
				sb.append("UserName : " + registrationBean.getEmailId() + "<br />");
				sb.append("Password : " + password);
				smtpMailSender.send(registrationBean.getEmailId(), "ROCA Account Activation", sb.toString());
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return i;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	@Override
	public Integer verifyEmailId(String email) {
		int i = 0;
		List<UserRegistration> userRegistration = this.getAllRegistration();
		System.out.println(userRegistration.get(1).getEmailId());
		UserRegistration u = userRegistration.stream().filter(a -> a.getEmailId().equals(email)).findAny().orElse(null);
		u.setIsEmailVerified(1);
		try {
			entityManager.persist(u);
			i = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return i;
	}

	@Override
	public Integer validateMobileNo(Long mobileNo) {
		Query query = entityManager.createNativeQuery(
				"select count(*) from roca_users_roca_user_registration where mobile_number=:mobilenumber");
		query.setParameter("mobilenumber", mobileNo);
		int count = ((Number) query.getSingleResult()).intValue();
		;
		System.out.println(count);

		return count;
	}

	@Override
	public Integer sendOtp(String mobileNo) {
		int i = 0;
		String ran = RandomString.digits;
		RandomString autoGenPassword = new RandomString(4, new SecureRandom(), ran);
		String otp = autoGenPassword.nextString();
		// gatewayAPITest.sendMessage(mobileNo, otp);

		OtpBean otpBean = new OtpBean();

		otpBean.setMobileNumber(mobileNo);
		otpBean.setOtp(Integer.parseInt(otp));

		try {
			entityManager.persist(otpBean);
			i = 1;
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return i;
	}

	@Override
	public Integer verifyOtp(String mobileNo, Integer otp) {

		Query query = entityManager.createNativeQuery(
				"select count(b.id) from roca_Users_Otp_Verification b where b.mobile_number=:mobileNumber and b.otp=:otp",
				OtpBean.class);
		query.setParameter("mobileNumber", mobileNo);
		query.setParameter("otp", otp);
		int count = ((Number) query.getSingleResult()).intValue();
		;
		System.out.println(count);

		return count;
	}

	@Override
	public Boolean validateEmailId(String emailId) {

		return ValidateEmail.isAddressValid(emailId);
	}
}
