package ma.ensate.location_voiture.repositories;

import ma.ensate.location_voiture.entities.AppRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AppRoleRepository extends MongoRepository<AppRole, String> {
    AppRole findByRoleName(String roleName);
}
