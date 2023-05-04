package in.laxmi.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.thymeleaf.standard.expression.EqualsNotEqualsExpression;

import in.laxmi.binding.DashBoardResponse;
import in.laxmi.binding.EnqSearchCriteria;
import in.laxmi.binding.EnquiryForm;
import in.laxmi.entity.CourseEntity;
import in.laxmi.entity.EnqStatusEntity;
import in.laxmi.entity.StudentEnqEntity;
import in.laxmi.entity.UserDetailsEntity;
import in.laxmi.repo.CourseRepo;
import in.laxmi.repo.EnqStatusRepo;
import in.laxmi.repo.StudentEnqRepo;
import in.laxmi.repo.UserDetailsRepo;
@Service
public class EnquiryServiceImpl implements EnquiryService {
	@Autowired
    private UserDetailsRepo userDtlsRepo;
	@Autowired
	private StudentEnqRepo enqRepo;
	@Autowired
	private CourseRepo courseRepo;
	@Autowired
	private EnqStatusRepo statusRepo;
	@Autowired
	private HttpSession session;
	DashBoardResponse response = new DashBoardResponse();
	public List<String> getcourseNames() {
		// TODO Auto-generated method stub
		List<CourseEntity> findAll = courseRepo.findAll();
		List<String> names=new ArrayList<>();
		for(CourseEntity entity:findAll) {
			names.add(entity.getCourseName());
		}
		return names;
	}

	public List<String> getEnqStatus() {
		// TODO Auto-generated method stub
		List<EnqStatusEntity> findAll = statusRepo.findAll();
		List<String> stausList=new ArrayList<>();
		for(EnqStatusEntity entity:findAll) {
			stausList.add(entity.getStatusName());
		}
		return stausList;
	}

	public DashBoardResponse getDashboardData(Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserDetailsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity userEntity= findById.get();
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			Integer total = enquiries.size(); 

			Integer enrolled = enquiries.stream()
			.filter(e -> e.getEnquiryStatus().equals("Enrolled"))
			.collect(Collectors.toList()).size();
			Integer losts = enquiries.stream()
			.filter(e -> e.getEnquiryStatus().equals("Lost"))
			.collect(Collectors.toList()).size();
			
			response.setTotlEnq(total);
			response.setEnrollEnq(enrolled);
			response.setLostEnq(losts);
		}
		
		return response;
	}

	public boolean saveEnquiry(EnquiryForm form) {
		// TODO Auto-generated method stub
		Integer userId = (Integer) session.getAttribute("userId");
		StudentEnqEntity entity = new StudentEnqEntity();
		BeanUtils.copyProperties(form, entity);
		UserDetailsEntity userEntity = userDtlsRepo.findById(userId).get();
		entity.setUserDetailsEntity(userEntity);
		enqRepo.save(entity);
		return true;
	}

	public String upsertEnquiry(EnquiryForm form) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<StudentEnqEntity> getEnquiries() {
		// TODO Auto-generated method stub
		Integer userId = (Integer) session.getAttribute("userId");
		Optional<UserDetailsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity userEntity= findById.get();
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			return enquiries;
		}
		return null;
	}

	public EnquiryForm getEnquiry(Integer EnquiryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<StudentEnqEntity> getFilteredEnqs(EnqSearchCriteria criteria, Integer userId) {
		// TODO Auto-generated method stub
		Optional<UserDetailsEntity> findById = userDtlsRepo.findById(userId);
		if(findById.isPresent()) {
			UserDetailsEntity userEntity= findById.get();
			List<StudentEnqEntity> enquiries = userEntity.getEnquiries();
			
			if(null!=criteria.getCourseName() & !"-Select-".equals(criteria.getCourseName())) {
				enquiries = enquiries.stream()
				.filter(c->c.getCourseName().equals(criteria.getCourseName()))
				.collect(Collectors.toList());
			}
			if(null!=criteria.getEnquiryStatus() & !"-Select-".equals(criteria.getEnquiryStatus())) {
				enquiries = enquiries.stream()
				.filter(c->c.getEnquiryStatus().equals(criteria.getEnquiryStatus()))
				.collect(Collectors.toList());
			}
			if(null!=criteria.getClassMode() & !"-Select-".equals(criteria.getClassMode())){
				enquiries = enquiries.stream()
				.filter(c->c.getClassMode().equals(criteria.getClassMode()))
				.collect(Collectors.toList());
			}
			return enquiries;
		}
		
		
		
		return null;
	}

}
