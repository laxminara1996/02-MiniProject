package in.laxmi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.laxmi.entity.StudentEnqEntity;

public interface StudentEnqRepo extends JpaRepository<StudentEnqEntity, Integer> {

}
