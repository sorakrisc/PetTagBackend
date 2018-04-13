package pet_tag.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet_tag.model.User;

@Repository("userProfileRepo")
public interface UserProfileRepo extends JpaRepository<User, Long> {

}