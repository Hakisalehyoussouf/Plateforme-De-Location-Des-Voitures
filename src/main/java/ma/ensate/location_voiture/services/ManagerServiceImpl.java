package ma.ensate.location_voiture.services;

import ma.ensate.location_voiture.entities.Reservation;
import ma.ensate.location_voiture.entities.Voiture;
import ma.ensate.location_voiture.repositories.ReservationRepository;
import ma.ensate.location_voiture.repositories.VoitureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class ManagerServiceImpl implements ManagerService {

    private final VoitureRepository voitureRepository;
    private  final ReservationRepository reservationRepository;

    public ManagerServiceImpl(VoitureRepository voitureRepository, ReservationRepository reservationRepository) {
        this.voitureRepository = voitureRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public Voiture ajouterVoiture(Voiture voiture) {

        return voitureRepository.save(voiture);
    }

    @Override
    public void modifierVoiture(String id, Voiture voiture) {

        Optional<Voiture> voiture1 = voitureRepository.findById(id);

        if(voiture1.isPresent()) {

            Voiture voiture2 = voiture1.get();

            voiture2.setMarque(voiture.getMarque());
            voiture2.setModele(voiture.getModele());
            voiture2.setAnnee(voiture.getAnnee());
            voiture2.setPrix(voiture.getPrix());
            voiture2.setManager(voiture.getManager());
            voiture2.setMatricule(voiture.getMatricule());
           // voiture2.setArchivee();
            voitureRepository.save(voiture2);
        }

    }

    @Override
    public  void supprimerVoiture(String id) {

        Optional<Voiture> voiture1 = voitureRepository.findById(id);

        if(voiture1.isPresent()) {
            Voiture voiture2 = voiture1.get();
            voiture2.setArchivee(true);

            voitureRepository.save(voiture2);
        }
    }

    @Override
    public Optional<Voiture> loadVoitureById(String id) {
        return  voitureRepository.findById(id);
    }

    @Override
    public List<Voiture> listVoitures() {

        return voitureRepository.findAllByArchivee(false);
    }









    @Override
    public Reservation ajouterReservation(Reservation reservation) {

        return reservationRepository.save(reservation) ;
    }

    @Override
    public void accepterReservation(String id) {

        Optional<Reservation> reservation1 = reservationRepository.findById(id);
        if(reservation1.isPresent()) {
            Reservation reservation2 = reservation1.get();
            reservation2.setStatus("acceptee");

            reservationRepository.save(reservation2);
        }

    }

    @Override
    public void refuserReservation(String id) {

        Optional<Reservation> reservation1 = reservationRepository.findById(id);
        if(reservation1.isPresent()) {
            Reservation reservation2 = reservation1.get();
            reservation2.setStatus("refusee");

            reservationRepository.save(reservation2);
        }

    }


    @Override
    public Voiture loadByReservationId(String id) {
        return null;
    }

    @Override
    public List<Reservation> listReservations() {

        return reservationRepository.findAllByStatus("en cours");
    }


}
