package in.laxmi.service;

import java.util.List;

import in.laxmi.binding.DashBoardResponse;
import in.laxmi.binding.EnqSearchCriteria;
import in.laxmi.binding.EnquiryForm;
import in.laxmi.entity.StudentEnqEntity;

public interface EnquiryService {
	public List<String> getcourseNames();

	public List<String> getEnqStatus();

	public DashBoardResponse getDashboardData(Integer userId);

	public boolean saveEnquiry(EnquiryForm form);
    public String upsertEnquiry(EnquiryForm form);
	public List<StudentEnqEntity> getEnquiries();
	public EnquiryForm getEnquiry(Integer EnquiryId);
	public List<StudentEnqEntity> getFilteredEnqs(EnqSearchCriteria criteria,Integer userId);
}
