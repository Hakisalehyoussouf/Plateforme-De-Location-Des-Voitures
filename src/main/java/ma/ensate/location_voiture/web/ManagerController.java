package ma.ensate.location_voiture.web;

import ma.ensate.location_voiture.entities.AppUser;
import ma.ensate.location_voiture.entities.Client;
import ma.ensate.location_voiture.entities.Reservation;
import ma.ensate.location_voiture.entities.Voiture;
import ma.ensate.location_voiture.services.AccountService;
import ma.ensate.location_voiture.services.ManagerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.Optional;


@Controller
//@RequestMapping("manager")
public class ManagerController {


    private final AccountService accountService;
    private final ManagerService managerService;

    public ManagerController(AccountService accountService, ManagerService managerService) {
        this.accountService = accountService;
        this.managerService = managerService;
    }

    @GetMapping("/manager/index")
    public String index(){
        return  "manager/index";
    }


    @GetMapping("/manager/ajoutvoiture/{id}")
    public String ajoutvoiture(Model model, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        if(managerOptinal.isPresent()) {
            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);


            return  "manager/ajoutvoiture";
        }else {
            return  null;
        }


    }


    @GetMapping("/manager/voitures/{id}")
    public String listVoitures(Model model, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        if(managerOptinal.isPresent()) {
            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);

            List<Voiture> voitures = managerService.listVoitures();

            model.addAttribute("voitures", voitures);


            return  "manager/affichevoiture";
        }else {
            return  null;
        }

    }



    @GetMapping("/manager/delete/{idV}/{id}")
    public String deleteVoiture(Model model, @PathVariable("idV") String idV, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        if(managerOptinal.isPresent()) {

            managerService.supprimerVoiture(idV);

            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);



            List<Voiture> voitures = managerService.listVoitures();

            model.addAttribute("voitures", voitures);


            return  "manager/affichevoiture";
        }else {
            return  null;
        }

    }





    @GetMapping("/manager/edit/{idV}/{id}")
    public String editVoiture(Model model, @PathVariable("idV") String idV, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        Optional<Voiture> voitureOptional = managerService.loadVoitureById(idV);

        if(managerOptinal.isPresent() && voitureOptional.isPresent() ){

            Voiture voiture = voitureOptional.get();

            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);

            model.addAttribute("voiture", voiture);




            return  "manager/modifierVoiture";
        }else {
            return  null;
        }

    }




    @PostMapping("/manager/ajoutvoitureValidatee")
    public String ajoutVoitureValidate(@RequestParam("marque") String marque,
                                       @RequestParam("modele") String modele,
                                       @RequestParam("annee") int annee,
                                       @RequestParam("matricule") String matricule,
                                       @RequestParam("prix") int prix,
                                       @RequestParam("idManager") String idManager,Model model) {




        Optional<AppUser> managerOptinal =  accountService.loadUserById(idManager);

        if(managerOptinal.isPresent()) {
            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);

            Voiture voiture =  new Voiture(null,marque, modele, matricule, prix, annee, true, false, manager );

            managerService.ajouterVoiture(voiture);



            List<Voiture> voitures = managerService.listVoitures();

            model.addAttribute("voitures", voitures);


            return "manager/affichevoiture";
        }else {
            return  null;
        }



    }







    @PostMapping("/manager/modifiervoitureValidate")
    public String modiferVoitureValidate(@RequestParam("marque") String marque,
                                       @RequestParam("modele") String modele,
                                       @RequestParam("annee") int annee,
                                       @RequestParam("matricule") String matricule,
                                       @RequestParam("prix") int prix,
                                       @RequestParam("idManager") String idManager,
                                       @RequestParam("idVoiture") String idVoiture,Model model) {





        Optional<AppUser> managerOptinal =  accountService.loadUserById(idManager);


        if(managerOptinal.isPresent() ){

            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);

            Voiture voiture =  new Voiture(null,marque, modele, matricule, prix, annee, true, false, manager );

            managerService.modifierVoiture(idVoiture, voiture);


            List<Voiture> voitures = managerService.listVoitures();

            model.addAttribute("voitures", voitures);


            return "manager/affichevoiture";
        }else {
            return  null;
        }



    }



    @GetMapping("/manager/reserver/{idV}/{id}")
    public String reserverVoiture(Model model, @PathVariable("idV") String idV, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);
        Optional<Voiture> voitureOptional = managerService.loadVoitureById(idV);

        if(managerOptinal.isPresent() && voitureOptional.isPresent() ){

            Voiture voiture = voitureOptional.get();

            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);

            model.addAttribute("voiture", voiture);





            return  "manager/ajoutdemande";
        }else {
            return  null;
        }

    }








    @PostMapping("/manager/reservationValidate")
    public String reservationValidate(@RequestParam("cin") String cin,
                                       @RequestParam("nom") String nom,
                                       @RequestParam("prenom") String prenom,
                                       @RequestParam("email") String email,
                                       @RequestParam("date_fin") String date_finh,
                                       @RequestParam("idManager") String idManager,
                                       @RequestParam("idVoiture") String idVoiture,Model model)  {



        Date date_fin;
        try {

            SimpleDateFormat htmlDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            date_fin = htmlDateFormat.parse(date_finh);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }


        Optional<AppUser> managerOptinal =  accountService.loadUserById(idManager);
        Optional<Voiture> voitureOptional = managerService.loadVoitureById(idVoiture);

        if(managerOptinal.isPresent() && voitureOptional.isPresent() ) {

            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);
            Voiture voiture = voitureOptional.get();
            Client client = new Client(cin,email, nom, prenom);

            Reservation reservation = new Reservation(null,new Date(), date_fin, "en cours", client, manager, voiture);

            managerService.ajouterReservation(reservation);


            List<Reservation> reservations = managerService.listReservations();

            model.addAttribute("reservations", reservations);


            return  "manager/affichereservations";
        }else {
            return  null;
        }



    }



    @GetMapping("/manager/reservations/{id}")
    public String listReservations(Model model, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        if(managerOptinal.isPresent()) {
            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);

            List<Reservation> reservations = managerService.listReservations();

            model.addAttribute("reservations", reservations);


            return  "manager/affichereservations";
        }else {
            return  null;
        }

    }



    @GetMapping("/manager/accepter/{idR}/{id}")
    public String accepterRes(Model model, @PathVariable("idR") String idR, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        if(managerOptinal.isPresent()) {

            managerService.accepterReservation(idR);

            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);



            List<Reservation> reservations = managerService.listReservations();

            model.addAttribute("reservations", reservations);


            return  "manager/affichereservations";
        }else {
            return  null;
        }

    }


    @GetMapping("/manager/refuser/{idR}/{id}")
    public String refuserRes(Model model, @PathVariable("idR") String idR, @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        if(managerOptinal.isPresent()) {

            managerService.refuserReservation(idR);

            AppUser manager = managerOptinal.get();
            populateDashboardManager(model,manager);



            List<Reservation> reservations = managerService.listReservations();

            model.addAttribute("reservations", reservations);


            return  "manager/affichereservations";
        }else {
            return  null;
        }

    }






    // recuperr les informtion pour le dashboard d'admin /manager/reservationValidatee
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
