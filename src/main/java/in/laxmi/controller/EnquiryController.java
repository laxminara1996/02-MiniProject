package in.laxmi.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import in.laxmi.binding.DashBoardResponse;
import in.laxmi.binding.EnqSearchCriteria;
import in.laxmi.binding.EnquiryForm;
import in.laxmi.entity.StudentEnqEntity;
import in.laxmi.service.EnquiryService;

@Controller
public class EnquiryController {
	@Autowired
	private EnquiryService enqService;
	@Autowired
	private HttpSession session;
	@GetMapping("/dashboard")
	public String dashboardPage(Model model) {
		Integer userId = (Integer) session.getAttribute("userId");
		DashBoardResponse dashboardData = enqService.getDashboardData(userId);
		model.addAttribute("dashboardData", dashboardData);
		return "dashboard";
	}
	@GetMapping("/enquiry")
	public String addEnquiryPage(Model model) {
		List<String> courses = enqService.getcourseNames();
		List<String> enqStatuses = enqService.getEnqStatus();
		EnquiryForm formObject=  new EnquiryForm();
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObject);
		return "add-enquiry";
	}
	@PostMapping("/addEnq")
	public String addEnquiry(@ModelAttribute("formObj") EnquiryForm formObj,Model model) {
		System.out.println(formObj);
		boolean status = enqService.saveEnquiry(formObj);
		if(status) {
			model.addAttribute("succMsg", "Enquiry added");
		}else {
			model.addAttribute("errMsg", "Problem occured");
		}
		return "add-enquiry";
	}
	
	private void initForm(Model model) {
		List<String> courses = enqService.getcourseNames();
		List<String> enqStatuses = enqService.getEnqStatus();
		EnquiryForm formObj=  new EnquiryForm();
		model.addAttribute("courseNames", courses);
		model.addAttribute("statusNames", enqStatuses);
		model.addAttribute("formObj", formObj);
	}
	@GetMapping("/enquiries")
	public String viewEnquiryPage(Model model) {
		initForm(model);
		List<StudentEnqEntity> enquiries = enqService.getEnquiries();
		model.addAttribute("enquiries", enquiries);
		return "view-enquiries";
	}
	@GetMapping("/filter-enquiries")
	public String getFilteredEnqs(@RequestParam String cname,@RequestParam String status,@RequestParam String mode,Model model) {
		EnqSearchCriteria criteria = new EnqSearchCriteria();
		criteria.setCourseName(cname);
		criteria.setEnquiryStatus(status);
		criteria.setClassMode(mode);
		System.out.println(criteria);
		Integer userId = (Integer) session.getAttribute("userId");
		List<StudentEnqEntity> filterdEnqs = enqService.getFilteredEnqs(criteria, userId);
		model.addAttribute("filterenquiries", filterdEnqs);
		return "filteredEnqs";
	}
	@GetMapping("/logout")
	public String logout() {
		session.invalidate();
		return "index";
	}
}
