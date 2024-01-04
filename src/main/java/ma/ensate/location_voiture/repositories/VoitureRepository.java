package ma.ensate.location_voiture.repositories;

import ma.ensate.location_voiture.entities.Voiture;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface VoitureRepository extends MongoRepository<Voiture, String> {
    //AppUser findByUsername(String username); methode pour chercher la voiture
    long count();

    List<Voiture> findAllByArchivee(boolean archive);

    Optional<Voiture> findById(String id);

}
