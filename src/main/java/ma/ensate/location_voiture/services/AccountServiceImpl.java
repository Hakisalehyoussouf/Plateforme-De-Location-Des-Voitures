package ma.ensate.location_voiture.services;

import ma.ensate.location_voiture.entities.AppRole;
import ma.ensate.location_voiture.entities.AppUser;
import ma.ensate.location_voiture.entities.Voiture;
import ma.ensate.location_voiture.repositories.AppRoleRepository;
import ma.ensate.location_voiture.repositories.AppUserRepository;
import ma.ensate.location_voiture.repositories.ReservationRepository;
import ma.ensate.location_voiture.repositories.VoitureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Service
@Transactional
public class AccountServiceImpl implements AccountService {

    private final AppUserRepository appUserRepository;

    private final AppRoleRepository appRoleRepository;

    private final VoitureRepository voitureRepository;

    private final ReservationRepository reservationRepository;

    public AccountServiceImpl(AppUserRepository appUserRepository, AppRoleRepository appRoleRepository, VoitureRepository voitureRepository, ReservationRepository reservationRepository) {
        this.appUserRepository = appUserRepository;
        this.appRoleRepository = appRoleRepository;
        this.voitureRepository = voitureRepository;
        this.reservationRepository = reservationRepository;
    }

    @Override
    public AppUser addNewUser(AppUser appUser) {

        return appUserRepository.save(appUser);
    }

    @Override
    public void deleteUser(String id) {
        appUserRepository.deleteById(id);
    }

    @Override
    public void updateUser(String id, AppUser appUser) {


            Optional<AppUser> manager1 = appUserRepository.findById(id);

            if(manager1.isPresent()) {

                AppUser manager2 = manager1.get();

                manager2.setUsername(appUser.getUsername());
                manager2.setEmail(appUser.getEmail());
                manager2.setPassword(appUser.getPassword());
                manager2.setUserRole(appUser.getUserRole());


                // voiture2.setArchivee();
                appUserRepository.save(manager2);
            }

    }

    @Override
    public AppRole addNewRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }
/*
    @Override
    public void addRoleToUser(String email, String roleName) {
        AppUser appUser = appUserRepository.findByEmail(email);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);
        appUser.getUserRoles().add(appRole);
        appUserRepository.save(appUser);
    }

*/
    @Override
    public AppUser loadUserByEmail(String email) {
        return appUserRepository.findByEmail(email);
    }

    @Override
    public Optional<AppUser> loadUserById(String id) {
        return appUserRepository.findById(id);
    }

    @Override
    public List<AppUser> listManagers() {

        return appUserRepository.findAllByUserRole("MANAGER");
    }




    @Override
    public long countVoitures() {
        return voitureRepository.count();
    }

    @Override
    public long countReservedReservations() {
        return reservationRepository.countByStatus("accepte");
    }

    @Override
    public long countDistinctManagers() {
        return appUserRepository.countByUserRole("MANAGER");
    }

    @Override
    public long countRefusedReservations() {
        return reservationRepository.countByStatus("refusee");
    }


}
