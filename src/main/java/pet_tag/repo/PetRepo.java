package pet_tag.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pet_tag.model.Pet;

@Repository("petRepo")
public interface PetRepo extends JpaRepository<Pet, Long> {

}