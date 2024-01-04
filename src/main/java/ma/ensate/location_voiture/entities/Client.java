package ma.ensate.location_voiture.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.security.SecureRandom;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    private String cin;
    private String email;
    private String nom;
    private String prenom;

}
