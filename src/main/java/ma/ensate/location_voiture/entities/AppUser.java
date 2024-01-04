package ma.ensate.location_voiture.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data @NoArgsConstructor @AllArgsConstructor
@Document(collection = "Users")
public class AppUser {

    @Id
    private String id;
    private String username;
    private String email;
    private String password;


    private String  userRole;
}
