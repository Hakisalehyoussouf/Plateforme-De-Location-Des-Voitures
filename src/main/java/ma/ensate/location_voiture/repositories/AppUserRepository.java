package ma.ensate.location_voiture.repositories;

import ma.ensate.location_voiture.entities.AppUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface AppUserRepository extends MongoRepository<AppUser, String> {
    AppUser findByEmail(String email);
    long countByUserRole(String userRole);

    Optional<AppUser> findById(String id);

    List<AppUser> findAllByUserRole(String role);

    void  deleteById(String id);
}
