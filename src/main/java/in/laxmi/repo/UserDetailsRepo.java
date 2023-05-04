package in.laxmi.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.laxmi.entity.UserDetailsEntity;

public interface UserDetailsRepo extends JpaRepository<UserDetailsEntity, Integer> {

public UserDetailsEntity findByEmail(String email);
public UserDetailsEntity findByEmailAndPwd(String email,String password);

}
