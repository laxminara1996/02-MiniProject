package in.laxmi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.laxmi.entity.CourseEntity;

public interface CourseRepo extends JpaRepository<CourseEntity, Integer> {
}
