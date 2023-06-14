package ihm;

import dao.JpaUtil;
import metier.om.Client;
import metier.om.Consultation;
import metier.om.Employe;
import metier.om.Medium;
import metier.om.ProfilAstral;
import metier.services.Saisie;
import metier.services.ServiceClient;
import metier.services.ServiceConsultation;
import metier.services.ServiceEmploye;
import metier.services.ServiceInit;
import metier.services.ServiceMedium;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe correspond à l'IHM de l'application. Elle permet de faire le lien avec l'utilisateur
 * Il pourra notamment s'authentifier, en tant qu'employé ou que client, et pourra avoir accès aux 
 * différentes fonctionnalités correspondant à son statut.
 * En particulier, un client peut demander une consultation, consulter son profil astral et historique 
 * de consultations ou encore découvrir la liste des médiums
 * Un employé doit accepter la consultation qui lui est confiée, en valider la fin. Il peut, lors d'une
 * consultation, générer des prédictions. Enfin, il a accès aux statistiques de l'entreprise.
 */
public class Main {

    public static void main(String[] args) {
        JpaUtil.creerFabriquePersistance();
        
        
        /*
        * INITIALISATION DE LA BD (employés et médiums)
        */
        System.out.println("\n\n --- Init employés ---");
        initialiserEmployes();
        
        System.out.println("\n\n --- Init médiums ---");
        initialiserMediums();
        
        System.out.println("\n\n --- Init clients ---");
        initialiserClients();
        
        System.out.println("\n\n --- Init consultations ---");
        initialiserConsultations();
        
        
        
        /*
        * fonctionnalités disponibles AVANT AUTHENTIFICATION
        */
        System.out.println("\n\n --- Afficher la liste des médiums sous forme de carrousel ---");
        afficherMediumDeListeAleatoire();
        
        System.out.println("\n\n --- Inscription client ---");
        inscriptionClient();
        
        System.out.println("\n\n --- Authentifier un client par mail ---");
        authentificationClient();
        
        //System.out.println("\n\n --- Authentifier un employé par mail ---");
        //authentificationEmploye();
        
        
        
        /*
        * fonctionnalités disponibles POUR CLIENT après authentification
        */
        userStoryClient();
        
        /*
        * fonctionnalités disponibles POUR EMPLOYE après authentification
        */
        userStoryEmploye();
        
        JpaUtil.fermerFabriquePersistance();
    }
    
    
    
    //// FONCTIONNALITÉS DISPONIBLES AVANT AUTHENTIFICATION
    
    
    // cette méthode permet d'afficher un Médium de la liste des Médiums, et de défiler ensuite,
    // comme un carrousel, pour découvrir les autres Médiums
    private static void afficherMediumDeListeAleatoire(){
        ServiceMedium s = new ServiceMedium();
        List<Medium> listeMed = s.afficherListeAleatoireMedium();
        
        int tailleListe = listeMed.size();
        if (tailleListe > 0){
            List<Integer> options = new ArrayList<>();
            options.add(0); 
            options.add(1); 
            options.add(2);

            boolean quit = false;
            int currentMedium = 0;
            while (!quit){
                System.out.println(listeMed.get(currentMedium));

                String menu = "\tChoissisez une option (valeur entre 0 et 2) \n\t\t0. Médium précédent \n\t\t1. Médium suivant \n\t\t2. Quitter";
                Integer choix = Saisie.lireInteger(menu, options);

                switch (choix) {
                    case 0:
                        currentMedium = (currentMedium - 1 + tailleListe) % tailleListe;
                        break;
                    case 1:
                        currentMedium = (currentMedium + 1) % tailleListe;
                        break;
                    default:
                        quit = true;
                        break;
                }
            }    
        } else {
            System.out.println("\nAucun médium à afficher.");
        }
    }
    
    // cette méthode permet d'inscrire interactivement (avec saisie) un Client
    private static void inscriptionClient(){
        // ici faut tester l'inscription d'un client interactivement
        ServiceClient s = new ServiceClient();
        System.out.println("\nFormulaire d'inscription");
        
        ///////// nom du client
        String nom = Saisie.lireChaine("\tNom : ");
        
        ///////// prenom
        String prenom = Saisie.lireChaine("\tPrénom : ");
        
        ///////// date de naissance
        // definition des valeurs possibles des jours et des mois
        List<Integer> days = new ArrayList<>();
        List<Integer> months = new ArrayList<>();
        for (int i = 1; i <= 31; i++) {
            days.add(i);
            if (i < 13)
                months.add(i);
        }
        
        // récupération des valeurs de la date de naissance
        Integer jour = Saisie.lireInteger("\tJour de naissance (jj) : ", days);
        Integer mois = Saisie.lireInteger("\tMois de naissance (mm) : ", months);
        Integer an = Saisie.lireInteger("\tAnnée de naissance (aaaa) : ");
        while (an < 1900 || an > 2023) {
            System.out.println("Merci d'entrer une année de naissance valable.");
            an = Saisie.lireInteger("\tAnnée de naissance (aaaa) : ");
        }

        String jourString = jour.toString();
        String moisString = mois.toString();
        String anString = an.toString();
        
        if (jour < 10){
            jourString = "0" + jourString;
        }
        if (mois < 10){
            moisString = "0" + moisString;
        }
            
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String dateInString = jourString + "/" + moisString + "/" + anString;
        Date dateNaissance = null;
        try {
            dateNaissance = formatter.parse(dateInString);
        } catch (ParseException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        ///////// adresse postale
        String adP = Saisie.lireChaine("\tAdresse postale (n°, rue, code postal, ville) : ");
        
        ///////// téléphone
        boolean telOk = false;
        String tel = "";
        while (!telOk){
            tel = Saisie.lireChaine("\tNuméro de téléphone :");
            // verification que le numéro contient exactement et seulement 10 digits (+ espaces)
            if (tel.matches("[0-9\\s]+") && tel.replaceAll("\\s", "").length() == 10) telOk = true;
            else System.out.println("\nMerci d'entrer un numéro de téléphone valide (10 chiffres et espaces éventuellement).");
        }
        
        ///////// mail
        String mail = Saisie.lireChaine("\tAdresse e-mail : ");
        
        ///////// mdp
        String mdp = Saisie.lireChaine("\tMot de passe : ");
        
        Client client = new Client(nom, prenom, dateNaissance, adP, tel, mail);
        client.setMotDePasse(mdp);
        Client inscrit = s.inscriptionClient(client);
        if (inscrit != null){
            System.out.println("\nTrace : succès de l'inscription du client.");
        } else {                
            System.out.println("\nTrace : échec de l'inscription ");
        }
    }
        
    // cette méthode permet d'authentifier interactivement (avec saisie) un Employé
    private static Employe authentificationEmploye() {
        ServiceEmploye s = new ServiceEmploye();
        
        System.out.println("\nFormulaire de connexion");
        
        ///////// mail de l'employé
        String mail = Saisie.lireChaine("\tLogin (e-mail): ");
        
        ///////// mot de passe
        String mdp = Saisie.lireChaine("\tMot de passe : ");
        
        // tentative de connexion
        Employe tentative = s.authentifierEmploye(mail, mdp);
        if (tentative != null){
            System.out.println("\nTrace : Tentative de connexion réussie pour l'employé d'adresse email '" + tentative.getMail() + "'\n");
        } else {
            System.out.println("\nTrace : Tentative de connexion échouée.\n");
        }
        return tentative;
    }
    
    // cette méthode permet d'authentifier interactivement (avec saisie) un Employé
    private static Client authentificationClient() {
        ServiceClient s = new ServiceClient();
        
        System.out.println("\nFormulaire de connexion");
        
        ///////// mail du client
        String mail = Saisie.lireChaine("\tLogin (e-mail): ");
        
        ///////// mdp
        String mdp = Saisie.lireChaine("\tMot de passe : ");
        
        // tentative de connexion
        Client tentative = s.authentifierClient(mail, mdp);
        if (tentative != null){
            System.out.println("\nTrace : Tentative de connexion réussie pour le client d'adresse email '" + tentative.getMail() + "'\n");
        } else {
            System.out.println("\nTrace : Tentative de connexion échouée.\n");
        }
        return tentative;
    }
    
    
    
    //// FONCTIONNALITÉS D'UN CLIENT AUTHENTIFIÉ
    
    // cette méthode permet de gérer toutes les fonctionnalités dont dispose un Client, de sa connexion, jusqu'à sa déconnexion
    // il peut notamment consulter son profil astral, son historique de consultations, ou encore choisir un médium pour prendre rendez-vous
    private static void userStoryClient(){
        // il faut d'abord que le client puisse s'authentifier
        Client client = authentificationClient();
        
        boolean deconnecte = false;
        // tant que le client ne choisit pas de se déconnecter, il peut consulter son profil, historique ou prendre rdv
        while (client != null && !deconnecte){
            String menu = "\nChoisissez parmi les options du menu client :\n1. Afficher votre historique de consultations \n2. Afficher votre profil astral \n3. Prendre rendez-vous \n4. Se déconnecter";
            List<Integer> options = new ArrayList<>();
            options.add(1); 
            options.add(2); 
            options.add(3); 
            options.add(4);
            Integer choix = Saisie.lireInteger(menu, options);

            switch (choix) {
                case 1:
                    System.out.println("\n\n --- Votre historique de consultation ---");
                    afficherHistoConsultationsClient(client);
                    afficherMediumsPreferes(client);
                    break;
                    
                case 2:
                    System.out.println("\n\n --- Votre Profil Astral ---");
                    afficherProfilAstralClient(client);
                    break;
                    
                case 3:
                    System.out.println("\n\n --- Choisir un médium avec qui prendre RDV ---");
                    choisirUnMediumPourNouvelleConsultation(client);
                    break;
                    
                default:
                    // déconnexion du client
                    deconnecte = true;
                    System.out.println("\nTrace : déconnexion");
                    break;
            }
        }
    }
    
    // cette méthode permet à un Client de choisir un Médium pour demander une Consultation
    private static void choisirUnMediumPourNouvelleConsultation(Client client){  
        ServiceMedium m = new ServiceMedium();

        List<Integer> options = new ArrayList<>();
        options.add(1); 
        options.add(2);
        options.add(3);
        options.add(4);

        String menu = "Chercher un médium (entrez un numéro entre 0 et 3) \n\t1. Par dénomination \n\t2. Par filtrage \n\t3. Par défaut (tous les médiums) \n\t4. Déconnexion ";
        Integer choix = Saisie.lireInteger(menu, options);
        List<Medium> listeMedPourCons = new ArrayList<>();
        Medium mPourCons = null;

        switch (choix) {
            case 1:
                ///////// recherche par dénomination
                String denom = Saisie.lireChaine("\tNom du médium : ");
                mPourCons = m.trouverMediumParDenom(denom);
                break;

            case 2:
                ///////// recherche par filtres
                listeMedPourCons = filtrerMediums(m);
                break;

            case 3:
                ///////// affichage de tous les médiums
                listeMedPourCons = m.afficherTousMediumsOrdreAlpha();
                break;
                    
            default:
                System.out.println("\nTrace : déconnexion");
                break;
        }
        
        // si l'utilisateur a choisi l'option 2 ou 3 
        if (listeMedPourCons.isEmpty()){
            System.out.println("\nAucun médium correspondant aux critères choisis n'a été trouvé...");
        } else {
            List<Integer> listeOptions = new ArrayList<>();
            // affichage des Médiums filtrés
            for (int i = 0; i < listeMedPourCons.size(); i++){
                System.out.println(i+1 + ". " + listeMedPourCons.get(i));
                listeOptions.add(i+1);
            }
                  
            menu = "\nSélectionner le numéro du médium que vous souhaitez consulter (numéro entre 1 et " + listeMedPourCons.size() + ")";
            choix = Saisie.lireInteger(menu, listeOptions);
            mPourCons = listeMedPourCons.get(choix-1);
        }  

        // si un médium a été choisi 
        if (mPourCons != null){
            System.out.println("\nTrace : Vous avez choisi ce médium :\n\n" + mPourCons);
            demanderConsultation(client, mPourCons);
        } else if (choix != 4 && mPourCons == null) {
            System.out.println("\nTrace : Erreur lors du choix du médium");
        }
    }
    
    // cette méthode permet à un Client de choisir un Médium grâce à différents filtres
    private static List<Medium> filtrerMediums(ServiceMedium s){
        ///////// Type des médiums
        List<String> types = new ArrayList<>();
        
        boolean typeOk;
       
        do {
            types.clear();
            String menuType = "\tType du médium (choisir une ou plusieurs valeur(s) entre 1 et 4 - si plusieurs valeurs, séparer avec des \';\') "
                    + "\n\t\t1. Astrologue \n\t\t2. Cartomancien \n\t\t3. Spirite \n\t\t4. Tous ";
            String choixType = Saisie.lireChaine(menuType);

            List<String> choix = Arrays.asList(choixType.split(";"));
            
            if (choix.containsAll(Arrays.asList("1", "2", "3")) || choix.contains("4")){
                // s'il y a les trois types ou option 4, alors --> tous médiums
                typeOk = true;
                types.add("Tous");
            } else if (!choix.contains("1") && !choix.contains("2") && !choix.contains("3") && !choix.contains("4")){
                typeOk = false;
                System.out.println("\nMauvais choix, merci de recommencer.");
            } else {
                typeOk = true;
                // ajout des types choisis dans la liste des types
                for (String optn : choix) {
                    try {
                        int type = Integer.parseInt(optn);
                        switch (type) {
                            case 1:
                                types.add("Astrologue");
                                break;
                            case 2:
                                types.add("Cartomancien");
                                break;
                            case 3:
                                types.add("Spirite");
                                break;
                            default:
                                System.out.println("\nMauvais choix, merci de recommencer.");
                                typeOk = false;
                                break;
                        } 
                    } catch (NumberFormatException e) {
                        System.out.println("\nMauvais choix, merci de recommencer.");
                        typeOk = false;
                        break;
                    }
                }
            }
        } while (!typeOk);

        
        ///////// Genre des médiums
        String genre;
        List<Integer> options = new ArrayList<>();
        options.add(1); 
        options.add(2); 
        options.add(3);
        
        String menuGenre = "\tGenre du médium (choisir une valeur entre 1 et 3) \n\t\t1. Homme \n\t\t2. Femme \n\t\t3. Tous ";
        Integer choixGenre = Saisie.lireInteger(menuGenre, options);
        
        switch (choixGenre) {
            case 1:
                genre = "H";
                break;
            case 2:
                genre = "F";
                break;
            default:
                genre = "Tous";
                break;
        }
        
        return s.filtrerMediums(types, genre);
    }
    
    // cette méthode permet de générer une demande de Consultation et de la transmettre à l'Employé concerné
    private static void demanderConsultation(Client client, Medium medium) {
        ServiceClient s = new ServiceClient();
        ServiceEmploye e = new ServiceEmploye();
        
        Employe emp = e.trouverEmployeDispo(medium.getGenre());
        boolean consultationOk = true;
        
        if (emp != null){
            
            Date date = new Date();
            Time heure = new Time(date.getTime());
            Consultation preCons = new Consultation(date, heure); // permet de transmettre les date et heure de la Consultation

            // l'envoi de la notification est à destination de l'employé chargé de la consultation : 
            System.out.println("\n\n /!\\ Notification à destination de l'EMPLOYÉ /!\\");
            Consultation cons = s.creerConsultation(medium, emp, client, preCons);
            if (cons == null){
                consultationOk = false;
            }
        }
        
        // s'il n'y a pas d'employé disponible, ou si la création de consultation n'a pas pu aboutir
        if (emp == null || !consultationOk) {
            // pas d'employé disponible, rejet de la demande et avertissement sur la console pour le Client
            System.out.println("Bonjour "+client.getPrenom()+". Votre demande de consultation avec "+medium.getDenomination()+" n'a pas pu aboutir... Veuillez réessayer ultérieurement.");
        }
    }

    // cette méthode permet d'afficher l'historique complet des Consultations d'un Client
    private static void afficherHistoConsultationsClient(Client client){
        if (client != null){
            ServiceConsultation sCons = new ServiceConsultation();

            List<Consultation> listeCons = sCons.retournerHistoConsultationsClient(client);

            System.out.println("\n~<[ Historique Consultations ]>~"); 
            if (listeCons.isEmpty()){
                System.out.println("Aucune consultation à afficher");
            } else {
                for (Consultation cons : listeCons){
                    System.out.println(cons);
                }
                System.out.println();
            }
        }
    }
    
    // cette méthode permet d'afficher le Profil Astral d'un Client
    private static void afficherProfilAstralClient(Client client){        
        if (client != null){
            ProfilAstral profil = client.getProfilAstral();
            
            System.out.println("\n~<[ Profil ]>~");
            System.out.println("[Profil] Signe du Zodiaque: " + profil.getSigneZodiaque());
            System.out.println("[Profil] Signe Chinois: " + profil.getSigneChinois());
            System.out.println("[Profil] Couleur porte-bonheur: " + profil.getCouleur());
            System.out.println("[Profil] Animal-totem: " + profil.getAnimal());
            System.out.println();
        }
    }
    
    // cette méthode permet au Client de visualiser les Médiums préférés 
    // (c'est-à-dire les Médiums qu'il a le plus consultés)
    private static void afficherMediumsPreferes(Client client){
        ServiceClient c = new ServiceClient();
        
        Map<Medium, Long> medPref = c.trouverMediumsFavoris(client);
        
        System.out.println("\n~<[ Vos médiums préférés ]>~");
        for (Map.Entry<Medium, Long> entry : medPref.entrySet()) {
            Medium med = entry.getKey();
            Long nbCons = entry.getValue();
            System.out.println(med.getDenomination() + " : " + nbCons + (nbCons == 1 ? " consultation" : " consultations"));
        }
    }
    
    
    
    
    
    
   
    
    //// FONCTIONNALITÉS D'UN EMPLOYÉ AUTHENTIFIÉ
    
    // cette méthode permet de gérer toutes les fonctionnalités dont dispose un Employé, de sa connexion, jusqu'à sa déconnexion
    // il peut notamment voir la prochaine consultation qu'il doit effectuer et la gérer (consulter le profil du Client, notifier qu'il est prêt,
    // générer des prédictions, valider la fin de la Consultation, saisir un commentaire) et consulter les chiffres de l'agence
    private static void userStoryEmploye(){
        // il faut d'abord que l'employé puisse s'authentifier
        Employe emp = authentificationEmploye();
        
        boolean deconnecte = false;
        // tant que l'employé ne choisit pas de se déconnecter, il peut gérer ses consultations à venir
        while (emp != null && !deconnecte){
            ServiceEmploye sEmp = new ServiceEmploye();
            String menu = "\nChoisissez parmi les options du menu employé :\n1. Voir la consultation à venir \n2. Consulter les chiffres de l'agence \n3. Se déconnecter";
            List<Integer> options = new ArrayList<>();
            options.add(1); 
            options.add(2);
            options.add(3);
            Integer choix = Saisie.lireInteger(menu, options);

            switch (choix) {
                case 1:
                    System.out.println("\n\n --- Gérer une consultation en tant qu'employé ---");
                    gestionConsultationParEmploye(emp, sEmp);
                    break;
                    
                case 2:
                    System.out.println("\n\n --- L'agence en chiffres ---");
                    visualiserStatistiques(sEmp);
                    break;
                default:
                    // déconnexion de l'employé
                    deconnecte = true;
                    System.out.println("\nTrace : déconnexion");
                    break;
            }
            
        }
    }
    
    // cette méthode permet à l'Employé de découvrir la prochaine Consultation qu'il doit effectuer, mais aussi de la gérer
    // (consulter le profil du Client, dire qu'il est prêt, générer des prédictions, valider la fin de la Consultation et saisir un commentaire)
    private static void gestionConsultationParEmploye(Employe emp, ServiceEmploye sEmp){
        Consultation prochaineConsultation = sEmp.afficherProchaineConsultation(emp);
        if (prochaineConsultation != null){
            // infos Client
            String nomClient = prochaineConsultation.getClient().getPrenom() + " " + prochaineConsultation.getClient().getNom();

            // infos Médium
            String nomMedium = prochaineConsultation.getMedium().getDenomination();

            // infos Consultation
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            SimpleDateFormat timeFormat = new SimpleDateFormat("HH'h'mm");
            String dateEtHeure = dateFormat.format(prochaineConsultation.getDateConsult()) + " à " + timeFormat.format(prochaineConsultation.getHeure());

            System.out.println("\nVotre prochaine consultation : ");
            System.out.println("Avec " + nomClient + ", en tant que " + nomMedium + ", pour le " + dateEtHeure);


            while(prochaineConsultation != null && !prochaineConsultation.isTerminee()){
                String menu;
                List<Integer> options;
                Integer choix;

                // si la consultation n'a pas encore commencé
                if(!prochaineConsultation.isEnCours()){
                    menu = "\nChoisissez parmi les options :\n1. Consulter le profil du client \n2. Je suis prêt.e pour la consultation";
                    options = new ArrayList<>();
                    options.add(1); 
                    options.add(2);
                    choix = Saisie.lireInteger(menu, options);

                    switch (choix){
                        case 1:
                            // affichage de l'historique du client + son profil astral
                            System.out.println("\nInformations concernant le client " + nomClient);
                            afficherHistoConsultationsClient(prochaineConsultation.getClient());
                            afficherProfilAstralClient(prochaineConsultation.getClient());
                            break;

                        case 2:
                            // la consultation peut démarrer
                            System.out.println("\n\n /!\\ Notification à destination du CLIENT /!\\");
                            prochaineConsultation = sEmp.demarrerConsultation(emp, prochaineConsultation);
                            if (prochaineConsultation != null){
                                System.out.println("\nTrace : La consultation a démarré");
                            } else {
                                System.out.println("\nTrace : Echec du démarrage de la consultation");
                            }
                                break;

                        default:
                            break;
                    }
                } 

                // si la consultation est en cours
                else {
                    menu = "\nChoisissez parmi les options :\n1. Demander de l'aide avec une génération de prédictions \n2. Valider la fin de la consultation";
                    options = new ArrayList<>();
                    options.add(1); 
                    options.add(2);
                    choix = Saisie.lireInteger(menu, options);

                    switch(choix){
                        case 1:
                            // demande de prédictions
                            demanderPrediction(prochaineConsultation, sEmp);
                            break;

                        case 2:
                            // fin de la consultation
                            prochaineConsultation = sEmp.validerFinConsultation(emp, prochaineConsultation);
                            if (prochaineConsultation != null){
                                System.out.println("\nTrace : Validation de la fin de la consultation...");

                                menu = "\nVoulez-vous saisir un commentaire pour vos collègues ?\n1. OUI \n2. NON";
                                options = new ArrayList<>();
                                options.add(1); 
                                options.add(2);

                                choix = Saisie.lireInteger(menu, options);

                                if (choix == 1) {
                                    saisirCommentaire(prochaineConsultation, sEmp);
                                }

                            } else {
                                System.out.println("\nTrace : Erreur lors de la validation de la fin de la consultation");
                            }
                            break;
                                        
                        default:
                            break;
                    }
                }
            }
                        
        } else {
            System.out.println("\nAucune consultation à venir pour le moment...");
        }          
    }
    
    // cette méthode permet à l'Employé d'obtenir des prédictions relatives au Client de la Consultation en cours,
    // en fonction des notes saisies
    private static void demanderPrediction(Consultation consultationEnCours, ServiceEmploye e){
        Client cli = consultationEnCours.getClient();
        
        System.out.println("\nPour chacun des trois thèmes, saisissez une note allant de 1 (mauvais) à 4 (excellent) sur l'échelle du bonheur");
        
        List<Integer> options = new ArrayList<>();
        for (int i = 1;i <= 4; i++){
            options.add(i);
        }
        
        String menu = "\tAmour : ";
        Integer amour = Saisie.lireInteger(menu, options);
        menu = "\tSanté : ";
        Integer sante = Saisie.lireInteger(menu, options);
        menu = "\tTravail : ";
        Integer travail = Saisie.lireInteger(menu, options);
        
        List<String> predictions = e.demanderPredictions(cli, amour, sante, travail, consultationEnCours);
        if (predictions != null){
            System.out.println("\n~<[ Prédictions ]>~");
            System.out.println("[ Amour ] " + predictions.get(0));
            System.out.println("[ Santé ] " + predictions.get(1));
            System.out.println("[Travail] " + predictions.get(2));
        } else {
            System.out.println("Aucune prédiction générable... Utilisez votre imagination :)");
        }
            
    }
    
    // cette méthode permet à l'Employé de saisir un commentaire à la fin de la Consultation, à l'intention de ses collègues
    private static void saisirCommentaire(Consultation cons, ServiceEmploye e){        
        String commentaire = Saisie.lireChaine("\nVeuillez saisir le commentaire : ");

        String saisieRealisee = e.saisirCommentaire(commentaire, cons);
        
        if (saisieRealisee != null){
            System.out.println("\nTrace : Commentaire saisi !");
        } else {
            System.out.println("\nTrace : Erreur lors de l'enregistrement du commentaire...");
        }
    }
    
    // cette méthode permet à l'Employé de visualiser les statistiques relatives à l'agence
    private static void visualiserStatistiques(ServiceEmploye e){
        ServiceMedium m = new ServiceMedium();
        
        Map<Medium, Integer> consParMed = m.trouverConsultationsParMedium();
        Map<Medium, Double> topMediums = m.trouverMediumsPlusConsultes();
        Map<Employe, Integer> repartition = e.trouverRepartitionClientsParEmp();
        
        if (consParMed != null && !consParMed.isEmpty()){
            System.out.println("\n~<[ Nombre de Consultations par Médium ]>~");
            for (Map.Entry<Medium, Integer> entry : consParMed.entrySet()) {
                Medium med = entry.getKey();
                Integer nbCons = entry.getValue();
                System.out.println(med.getDenomination() + " : " + nbCons + (nbCons <= 1 ? " consultation" : " consultations"));
            }
        }
        
        if (topMediums != null && !topMediums.isEmpty()){
            System.out.println("\n~<[ Top 5 des Médiums préférés par les Clients ]>~");
            for (Map.Entry<Medium, Double> entry : topMediums.entrySet()) {
                Medium med = entry.getKey();
                Double pourcentage = entry.getValue();
                System.out.println(med.getDenomination() + " : " + String.format("%.2f", pourcentage) + "% des consultations");
            }
        }
        
        if (repartition != null && !repartition.isEmpty()){
            System.out.println("\n~<[ Répartition des Clients par Employé ]>~");
            for (Map.Entry<Employe, Integer> entry : repartition.entrySet()) {
                Employe emp = entry.getKey();
                Integer nbClients = entry.getValue();
                System.out.println(emp.getPrenom() + " " + emp.getNom() + " : " + nbClients + (nbClients <= 1 ? " client" : " clients"));
            }
        }

    }
   
    
    
    
    
    //// METHODES D'INITIALISATION
    // cette méthode permet de rentrer l'ensemble des Employés dans la base de données
    private static void initialiserEmployes(){
        ServiceInit s = new ServiceInit();
        List<Employe> employes = s.initialiserEmployes();
        
        if (employes != null){
            System.out.println("Trace : succès pour initialiser les employés ");
        } else {                
            System.out.println("Trace : échec de l'initialisation des employés ");
        }
    }
    
    // cette méthode permet de rentrer l'ensemble des Médiums dans la base de données
    private static void initialiserMediums(){
        ServiceInit s = new ServiceInit();
        List<Medium> mediums = s.initialiserMediums();
        
        if (mediums != null){
            System.out.println("Trace : succès pour initialiser les médiums ");
        } else {                
            System.out.println("Trace : échec de l'initialisation des médiums ");
        }
    }
    
    // cette méthode permet de rentrer l'ensemble des Clients dans la base de données
    private static void initialiserClients(){
        ServiceInit s = new ServiceInit();
        List<Client> clients = s.initialiserClients();
        
        if (clients != null){
            System.out.println("Trace : succès pour initialiser les clients ");
        } else {                
            System.out.println("Trace : échec de l'initialisation des clients ");
        }
    }
    
    // cette méthode permet d'enregistrer quelques Consultations dans la base de données
    private static void initialiserConsultations(){
        ServiceInit s = new ServiceInit();
        List<Consultation> consultations = s.initialiserConsultations();
        
        if (consultations != null){
            System.out.println("Trace : succès pour initialiser les consultations ");
        } else {                
            System.out.println("Trace : échec de l'initialisation des consultations ");
        } 
    }
    
}
