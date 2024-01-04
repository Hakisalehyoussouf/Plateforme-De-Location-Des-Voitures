package ma.ensate.location_voiture.web;

import ma.ensate.location_voiture.entities.AppUser;
import ma.ensate.location_voiture.entities.Reservation;
import ma.ensate.location_voiture.entities.Voiture;
import ma.ensate.location_voiture.services.AccountService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@Controller

public class AdminController {

    private  final AccountService accountService;


    public AdminController(AccountService accountService) {
        this.accountService = accountService;
    }


    @GetMapping("index")
    public String index(){
        return  "admin/index";
    }


    @GetMapping("/admin/ajoutManager/{id}")
    public String ajoutManager(Model model, @PathVariable("id") String id){

        Optional<AppUser> adminOptinal =  accountService.loadUserById(id);

        if(adminOptinal.isPresent()) {
            AppUser admin = adminOptinal.get();
            populateDashboardAdmin(model,admin);


            return  "admin/ajoutmanager";
        }else {
            return  null;
        }


    }





    @PostMapping("/admin/ajoutmanagerValidatee")
    public String ajoutManagerValidate(@RequestParam("nom") String nom,
                                       @RequestParam("email") String email,
                                       @RequestParam("password") String password,
                                       @RequestParam("idAdmin") String idAdmin,Model model) {




        Optional<AppUser> adminOptinal =  accountService.loadUserById(idAdmin);

        if(adminOptinal.isPresent()) {
            AppUser admin = adminOptinal.get();
            populateDashboardAdmin(model,admin);

            AppUser appUser = new AppUser(null, nom, email, password, "MANAGER");

            accountService.addNewUser(appUser);

            List<AppUser> managers = accountService.listManagers();

            model.addAttribute("managers", managers);


            return  "admin/affichemanagers";


        }else {
            return  null;
        }



    }





    @GetMapping("/admin/managers/{id}")
    public String listReservations(Model model,  @PathVariable("id") String id){

        Optional<AppUser> managerOptinal =  accountService.loadUserById(id);

        if(managerOptinal.isPresent()) {
            AppUser admin = managerOptinal.get();
            populateDashboardAdmin(model,admin);

            List<AppUser> managers = accountService.listManagers();

            model.addAttribute("managers", managers);


            return  "admin/affichemanagers";
        }else {
            return  null;
        }

    }





    @GetMapping("/admin/delete/{idM}/{id}")
    public String deleteManager(Model model, @PathVariable("idM") String idM, @PathVariable("id") String id){

        Optional<AppUser> adminOptinal =  accountService.loadUserById(id);

        if(adminOptinal.isPresent()) {

            accountService.deleteUser(idM);

            AppUser manager = adminOptinal.get();
            populateDashboardAdmin(model,manager);



            List<AppUser> managers = accountService.listManagers();

            model.addAttribute("managers", managers);


            return  "admin/affichemanagers";
        }else {
            return  null;
        }

    }




    @GetMapping("/admin/edit/{idM}/{id}")
    public String editManager(Model model, @PathVariable("idM") String idM, @PathVariable("id") String id){

        Optional<AppUser> adminOptinal =  accountService.loadUserById(id);

        Optional<AppUser> managerOptional = accountService.loadUserById(idM);

        if(adminOptinal.isPresent() && managerOptional.isPresent() ){

            AppUser manager = managerOptional.get();

            AppUser admin = adminOptinal.get();
            populateDashboardAdmin(model,admin);

            model.addAttribute("manager", manager);


            return  "admin/modifierManager";
        }else {
            return  null;
        }

    }










    @PostMapping("/admin/modifiermanagerValidatee")
    public String modiferManagerValidate(@RequestParam("nom") String nom,
                                         @RequestParam("email") String email,
                                         @RequestParam("password") String password,
                                         @RequestParam("idAdmin") String idAdmin,
                                         @RequestParam("idManager") String idManager,Model model) {





        Optional<AppUser> adminOptinal =  accountService.loadUserById(idAdmin);


        if(adminOptinal.isPresent() ){

            AppUser admin = adminOptinal.get();
            populateDashboardAdmin(model,admin);

            AppUser manager =  new AppUser(null,nom, email, password,"MANAGER" );

            accountService.updateUser(idManager, manager);


            List<AppUser> managers = accountService.listManagers();

            model.addAttribute("managers", managers);


            return "admin/affichemanagers";
        }else {
            return  null;
        }



    }


















    // recuperr les informtion pour le dashboard d'admin
    private void populateDashboardAdmin(Model model, AppUser user) {
        model.addAttribute("admin", user);
        model.addAttribute("nbr_annonce", accountService.countVoitures());
        model.addAttribute("nbr_reservation", accountService.countReservedReservations());
        model.addAttribute("nbr_partenaire", accountService.countDistinctManagers());
    }





}

