package in.laxmi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.laxmi.binding.LoginForm;
import in.laxmi.binding.SignUpForm;
import in.laxmi.binding.UnlockForm;
import in.laxmi.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/signup")
	public String handleSignup(@ModelAttribute("user") SignUpForm form, Model model) {
		boolean status = userService.signup(form);
		if (status) {
			model.addAttribute("succsMsg", "Check Your Email");
		} else {
			model.addAttribute("errorMsg", "choose unique email");

		}
		return "signup";

	}

	/*8796515957*/
	@GetMapping("/login")
	public String loginPage(Model model) {
		model.addAttribute("loginForm", new LoginForm());
		return "login";
	}
	@PostMapping("/login")
    public String login(@ModelAttribute("loginForm") LoginForm loginForm,Model model) {
    	System.out.println(loginForm);
    	String status = userService.login(loginForm);
    	
    	if(status.contains("success")) {
    		return "redirect:dashboard";
    	}
    	model.addAttribute("errMsg", status);
    	return "login";
    }
	@GetMapping("/signup")
	public String signupPage(Model model) {
		model.addAttribute("user", new SignUpForm());
		return "signup";
	}

	@PostMapping("/unlock")
	public String unlockUserAccount(@ModelAttribute("unlock") UnlockForm form,Model model) {
		System.out.println(form);
		if(form.getNewPwd().equals(form.getConformPwd())){
			boolean status = userService.unlockAccount(form);
			if(status) {
				model.addAttribute("succMsg", "Your Account Unlocked Successfully");
			}else {
				model.addAttribute("errMsg", "Given temporary pwd Incorrect");
			}
		}else {
		model.addAttribute("errMsg", "newPwd and conformPwd should be same");
	    }
	 return"unlock";

	}

	@GetMapping("/unlock")
	public String unlockPage(@RequestParam String email,Model model) {
		UnlockForm unlockFormObj = new UnlockForm();
		unlockFormObj.setEmail(email);
		model.addAttribute("unlock", unlockFormObj);
		return "unlock";
	}

	@GetMapping("/forgot")
	public String forgotPwdPage() {
		return "forgotPwd";
	}
	@PostMapping("/forgotPwd")
	public String forgotPwd(@RequestParam("email") String email,Model model) {
		boolean status=userService.forgotPwd(email);
		if(status) {
		model.addAttribute("succMsg", "Pwd sent to Your Email");
		}else {
			model.addAttribute("errMsg", "Invalid Email");
		}
		return "forgotPwd";
	}
}
