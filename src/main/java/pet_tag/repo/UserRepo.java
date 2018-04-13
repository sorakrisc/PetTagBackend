package pet_tag.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet_tag.model.User;

@Repository("userRepo")
public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
    User findOneByUsername(String username);

}