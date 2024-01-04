package ma.ensate.location_voiture.services;

import ma.ensate.location_voiture.entities.AppRole;
import ma.ensate.location_voiture.entities.AppUser;

import java.util.List;
import java.util.Optional;

public interface AccountService {
    AppRole addNewRole(AppRole appRole);

    AppUser addNewUser(AppUser appUser);
    void deleteUser(String id);
    void updateUser(String id, AppUser appUser);


   // void addRoleToUser(String email, String roleName);
    AppUser loadUserByEmail(String email);

    Optional<AppUser> loadUserById(String id);

    List<AppUser> listManagers();





    long countVoitures();
    long countReservedReservations();
    long countDistinctManagers();

    long countRefusedReservations();
}
