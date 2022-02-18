package bloggie.repository;

import bloggie.domain.User;
import liquibase.pro.packaged.T;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

}
