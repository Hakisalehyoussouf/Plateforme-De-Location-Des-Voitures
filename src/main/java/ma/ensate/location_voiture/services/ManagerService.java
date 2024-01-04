package ma.ensate.location_voiture.services;

import ma.ensate.location_voiture.entities.Reservation;
import ma.ensate.location_voiture.entities.Voiture;

import java.util.List;
import java.util.Optional;

public interface ManagerService {

    Voiture ajouterVoiture(Voiture voiture);
    void  supprimerVoiture(String id);
    void modifierVoiture(String id,Voiture voiture);


    Optional<Voiture> loadVoitureById(String id);
    List<Voiture> listVoitures();



    Reservation ajouterReservation(Reservation reservation);
    //void ajouterVoitureToReservation(String reservationId, String roleName); C'estv annuulee
    void accepterReservation(String id);
    void refuserReservation(String id);

    Voiture loadByReservationId(String id);
    List<Reservation> listReservations();


}
