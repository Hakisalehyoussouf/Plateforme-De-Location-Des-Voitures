package ma.ensate.location_voiture.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Reservations")
public class Reservation {


    @Id
    private String id;
    private Date date_debut;
    private Date date_fin;
    private String status;
    private Client client;

    @DBRef
    private  AppUser manager;
    @DBRef
    private  Voiture voiture;


}
