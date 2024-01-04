package ma.ensate.location_voiture;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication
public class LocationVoitureApplication {


    public static void main(String[] args) {
        SpringApplication.run(LocationVoitureApplication.class, args);
    }





    /*
    @Bean
    CommandLineRunner start(AccountService accountService){
        return args -> {
            accountService.addNewRole(new AppRole(null, "ADMIN"));
            accountService.addNewRole(new AppRole(null, "MANAGER"));

            accountService.addNewUser(new AppUser(null, "haki","123", new ArrayList<>() ));
            accountService.addNewUser(new AppUser(null, "adnan","123", new ArrayList<>() ));
            accountService.addNewUser(new AppUser(null, "salim","123", new ArrayList<>() ));

            accountService.addRoleToUser("haki","ADMIN");
            accountService.addRoleToUser("haki","MANAGER");
            accountService.addRoleToUser("salim","MANAGER");
            accountService.addRoleToUser("adnan","MANAGER");

        };
    }*/
}
