package ma.ensate.location_voiture.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Voitures")
public class Voiture {

    @Id
    private String id;
    private String marque;
    private String modele;
    private String matricule;

    private int prix;
    private int annee;
    private boolean disponble;
    private boolean archivee;

    @DBRef
    private AppUser manager;


}
