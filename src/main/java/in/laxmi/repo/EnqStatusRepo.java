package in.laxmi.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.laxmi.entity.EnqStatusEntity;

public interface EnqStatusRepo extends JpaRepository<EnqStatusEntity, Integer>{
}
