package ma.ensate.location_voiture.repositories;

import ma.ensate.location_voiture.entities.AppUser;
import ma.ensate.location_voiture.entities.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends MongoRepository<Reservation, String> {

    long countByStatus(String status);

    List<Reservation> findAllByStatus(String status);

    Optional<Reservation> findById(String id);
}
