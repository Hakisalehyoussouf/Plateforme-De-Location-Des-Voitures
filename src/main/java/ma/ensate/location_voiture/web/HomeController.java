package ma.ensate.location_voiture.web;


import ma.ensate.location_voiture.entities.AppUser;
import ma.ensate.location_voiture.entities.Voiture;
import ma.ensate.location_voiture.services.AccountService;
import ma.ensate.location_voiture.services.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@Controller
//@RequestMapping("/")
public class HomeController {

    private  final AccountService accountService;
    private final ManagerService managerService;

    public HomeController(AccountService accountService, ManagerService managerService) {
        this.accountService = accountService;
        this.managerService = managerService;
    }

    @PostMapping("/index") // Ce n'est pas important
    public String index(@RequestParam("marque") String marque,
                        @RequestParam("modele") String modele,
                        @RequestParam("annee") int annee,
                        @RequestParam("matricule") String matricule,
                        @RequestParam("prix") double prix,
                        @RequestParam("idManager") String idManager,Model model){

        System.out.println(marque);
        System.out.println(modele);
        System.out.println(annee);
        System.out.println(prix);
        System.out.println(matricule);
        System.out.println(idManager);


        Optional<AppUser> managerOptinal =  accountService.loadUserById("646ecad6f5e871225904c934");

        if(managerOptinal.isPresent()) {
            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager );
            return "manager/index";
        }else {
            return  null;
        }
    }


    @GetMapping("/login")
    public String login(){
        return  "login";
    }



    @GetMapping("/dashboardManager/{id}")
    public String dashboardManager(Model model, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        if(managerOptinal.isPresent()) {
            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager );
            return "manager/index";
        }else {
            return  null;
        }

    }

    @GetMapping("/dashboardAdmin/{id}")
    public String dashboardAdmin(Model model, @PathVariable("id") String id){

        Optional<AppUser> adminOptinal =  accountService.loadUserById(id);

        if(adminOptinal.isPresent()) {
            AppUser admin = adminOptinal.get();
            populateDashboardAdmin(model,admin);
            return "admin/index";
        }else {
            return  null;
        }

    }



    @PostMapping("/authentifcation")
    public String  login(@RequestParam("email") String email, @RequestParam("password") String Password, Model model){

        AppUser user = accountService.loadUserByEmail(email);
        if (user == null ) {
            model.addAttribute("error", "Invalid email");

            return "login";
        }else if(!user.getPassword().equals(Password)){
            model.addAttribute("error", "Invalid password");


            return "login";
        } else {

            // int a=adminService.countAnnounces();

            if(user.getUserRole().equals("ADMIN")){

                populateDashboardAdmin(model, user);
                return  "admin/index";

            }else  {

                populateDashboardManager(model, user);
                return  "manager/index";

            }

            //populateDashboardData(model, admin);




        }
    }









    // recuperr les informtion pour le dashboard d'admin
    private void populateDashboardAdmin(Model model, AppUser user) {
        model.addAttribute("admin", user);
        model.addAttribute("nbr_annonce", accountService.countVoitures());
        model.addAttribute("nbr_reservation", accountService.countReservedReservations());
        model.addAttribute("nbr_partenaire", accountService.countDistinctManagers());
    }



    // recuperr les informtion pour le dashboard de manager
    private void populateDashboardManager(Model model, AppUser user) {
        model.addAttribute("manager", user);
        model.addAttribute("nbr_annonce", accountService.countVoitures());
        model.addAttribute("nbr_reservation", accountService.countReservedReservations());
        model.addAttribute("nbr_partenaire", accountService.countRefusedReservations());
    }









}
