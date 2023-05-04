package in.laxmi.service;

import in.laxmi.binding.LoginForm;
import in.laxmi.binding.SignUpForm;
import in.laxmi.binding.UnlockForm;

public interface UserService {
public String login(LoginForm form);
public boolean signup(SignUpForm form);
public boolean unlockAccount(UnlockForm form);
public boolean forgotPwd(String email);
}
