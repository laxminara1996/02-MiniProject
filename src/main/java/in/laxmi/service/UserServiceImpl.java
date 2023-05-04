package in.laxmi.service;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.laxmi.binding.LoginForm;
import in.laxmi.binding.SignUpForm;
import in.laxmi.binding.UnlockForm;
import in.laxmi.constants.AppConfigConstants;
import in.laxmi.entity.UserDetailsEntity;
import in.laxmi.repo.UserDetailsRepo;
import in.laxmi.util.EmailUtils;
import in.laxmi.util.PwdUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDetailsRepo userDtlsRepo;
	@Autowired
    private EmailUtils emailUtils;
	@Autowired
    private PwdUtils pwdUtils;
	@Autowired
	private HttpSession session;
	public String login(LoginForm form) {
		// TODO Auto-generated method stub
          UserDetailsEntity entity = userDtlsRepo.findByEmailAndPwd(form.getEmail(), form.getPwd());
          if(entity==null) {
        	  return AppConfigConstants.INVALID_CREDENTIALS_MSG;
          }
          if(entity.getAccStatus().equals(AppConfigConstants.STR_lOCKED)) {
        	  return AppConfigConstants.STR_ACC_LOCKED_MSG;
          }
          session.setAttribute(AppConfigConstants.STR_USERID, entity.getUserId());
		return AppConfigConstants.STR_SUCCESS;
	}

	public boolean signup(SignUpForm form) {
		UserDetailsEntity user = userDtlsRepo.findByEmail(form.getEmail());
		if(user!=null) {
			return false;
		}
		// copy date from binding to entity object
		UserDetailsEntity entity = new UserDetailsEntity();
		BeanUtils.copyProperties(form, entity);
		// TODO generate random pwd
		String tempPwd = pwdUtils.generateRandomPwd();
		entity.setPwd(tempPwd);
		// TODO set the account status LOCKED
		entity.setAccStatus(AppConfigConstants.STR_lOCKED);
		// TODO insert record into table
		userDtlsRepo.save(entity);
		// TODO send email to unlock account
		String to = form.getEmail();
		String subject=AppConfigConstants.UNLOCK_EMAIL_SUBJECT;
		StringBuffer body = new StringBuffer();
		 body.append("<h1> Use below temporary pwd to unnlock your account</h1>");
		 body.append("Temporary Pwd :"+tempPwd);
		 body.append("<br/>");
		 body.append("<a href=\"http:localhost:8098/unlock?email="+to+"\">Click Here To Unlock Your Account<a/>");
         emailUtils.sendEmail(to, subject, body.toString());
         
		return true;
	}

	public boolean unlockAccount(UnlockForm form) {
		// TODO Auto-generated method stub
		UserDetailsEntity entity = userDtlsRepo.findByEmail(form.getEmail());
		if(entity.getPwd().equals(form.getTempPwd())) {
			entity.setPwd(form.getNewPwd());
			entity.setAccStatus(AppConfigConstants.STR_UNlOCKED);
			userDtlsRepo.save(entity);
			return true;
		}else {
			return false;
		}
		
	}

	public boolean forgotPwd(String email) {
		// TODO Auto-generated method stub
		UserDetailsEntity entity = userDtlsRepo.findByEmail(email);
		if(entity==null) {
			return false;
		}
		String pwd = entity.getPwd();
		String subject = AppConfigConstants.RECOVER_PWD_EMAIL_SUBJECT;
		String body="Your Pwd :"+entity.getPwd();
		emailUtils.sendEmail(email, subject, body);
		return true;
	}

}
