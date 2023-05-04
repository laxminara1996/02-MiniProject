package in.laxmi.runner;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import in.laxmi.entity.CourseEntity;
import in.laxmi.entity.EnqStatusEntity;
import in.laxmi.repo.CourseRepo;
import in.laxmi.repo.EnqStatusRepo;
@Component
public class DataLoader implements ApplicationRunner {
     @Autowired
	 private CourseRepo courseRepo;
     @Autowired
     private EnqStatusRepo statusRepo;
	@Override
	public void run(ApplicationArguments args) throws Exception {
		// TODO Auto-generated method stub
		courseRepo.deleteAll();
		CourseEntity c = new CourseEntity();
		c.setCourseName("Java");
		CourseEntity c1 = new CourseEntity();
		c1.setCourseName("Python");
		CourseEntity c2 = new CourseEntity();
		c2.setCourseName("Devops");
		courseRepo.saveAll(Arrays.asList(c,c1,c2));
		statusRepo.deleteAll();
		EnqStatusEntity e = new EnqStatusEntity();
		e.setStatusName("New");
		EnqStatusEntity e1 = new EnqStatusEntity();
		e1.setStatusName("Enrolled");
		EnqStatusEntity e2 = new EnqStatusEntity();
		e2.setStatusName("Lost");
		statusRepo.saveAll(Arrays.asList(e,e1,e2));
	}

}
