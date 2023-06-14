package metier.services;

import dao.ClientDao;
import dao.ConsultationDao;
import dao.EmployeDao;
import dao.JpaUtil;
import dao.MediumDao;
import metier.om.Astrologue;
import metier.om.Cartomancien;
import metier.om.Client;
import metier.om.Consultation;
import metier.om.Employe;
import metier.om.Medium;
import metier.om.ProfilAstral;
import metier.om.Spirite;
import java.io.IOException;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author : B3430 : Adrien Morin, Marie Roulier
 * Cette classe fournit des services relatifs à l'initialisation de l'application.
 * Elle permet d'initialiser des Employés, Clients, Médiums et Consultations.
 */
public class ServiceInit {
    
    public ServiceInit(){
        
    }
    
    // permet d'initialiser les Employés
    public List<Employe> initialiserEmployes(){
        List<Employe> toAdd = new ArrayList<>();
        
        Employe e1 = new Employe("DUPOND", "Camille", "F", "cdupond@predict.if", "06 55 44 77 88");
        e1.setMotDePasse("mdpdifficile");
        toAdd.add(e1);
        
        Employe e2 = new Employe("MARTIN", "Nicolas", "H", "nmartin@predict.if", "06 55 44 77 89");
        e2.setMotDePasse("motdepasse123");
        toAdd.add(e2);

        Employe e3 = new Employe("LEFEVRE", "Amélie", "F", "alefevre@predict.if", "06 55 44 77 90");
        e3.setMotDePasse("azertyuiop");
        toAdd.add(e3);

        Employe e4 = new Employe("DURAND", "Jean", "H", "jdurand@predict.if", "06 55 44 77 91");
        e4.setMotDePasse("azerty1234");
        toAdd.add(e4);

        Employe e5 = new Employe("ROUSSEAU", "Julie", "F", "jrousseau@predict.if", "06 55 44 77 92");
        e5.setMotDePasse("password123");
        toAdd.add(e5);

        Employe e6 = new Employe("DUBOIS", "Louis", "H", "ldubois@predict.if", "06 55 44 77 93");
        e6.setMotDePasse("monmotdepasse");
        toAdd.add(e6);

        Employe e7 = new Employe("BERNARD", "Marie", "F", "mbernard@predict.if", "06 55 44 77 94");
        e7.setMotDePasse("letmein");
        toAdd.add(e7);

        Employe e8 = new Employe("MOREAU", "Antoine", "H", "amoreau@predict.if", "06 55 44 77 95");
        e8.setMotDePasse("password");
        toAdd.add(e8);

        Employe e9 = new Employe("FOURNIER", "Margaux", "F", "mfournier@predict.if", "06 55 44 77 96");
        e9.setMotDePasse("azerty");
        toAdd.add(e9);

        Employe e10 = new Employe("GIRARD", "Thomas", "H", "tgirard@predict.if", "06 55 44 77 97");
        e10.setMotDePasse("12345678");
        toAdd.add(e10);

        Employe e11 = new Employe("RICHARD", "Chloé", "F", "crichard@predict.if", "06 55 44 77 98");
        e11.setMotDePasse("motdepasse");
        toAdd.add(e11);

        Employe e12 = new Employe("PETIT", "Alexandre", "H", "apetit@predict.if", "06 55 44 77 99");
        e12.setMotDePasse("abcdef");
        toAdd.add(e12);

        Employe e13 = new Employe("ROUX", "Clémentine", "F", "croux@predict.if", "06 55 44 77 00");
        e13.setMotDePasse("qwerty");
        toAdd.add(e13);
        
        Employe e14 = new Employe("FONTAINE", "Maxime", "H", "mfontaine@predict.if", "06 55 44 77 01");
        e14.setMotDePasse("azertyuiop123");
        toAdd.add(e14);
        
        Employe e15 = new Employe("DAVID", "Justine", "F", "jdavid@predict.if", "06 55 44 77 02");
        e15.setMotDePasse("blopdifficile0");
        toAdd.add(e15);

        
        EmployeDao eDao = new EmployeDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            for (Employe addEmp : toAdd){
                eDao.create(addEmp);
            }
            
            JpaUtil.validerTransaction();
            
        } catch(Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();
            toAdd = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return toAdd;
    }
    
    // permet d'initialiser les Médiums
    public List<Medium> initialiserMediums(){
        
        List<Medium> toAdd = new ArrayList<>();
        Medium m1 = new Spirite("Gwenaëlle", "F", "Spécialiste des grandes conversations au-delà de TOUTES les frontières.", "Boule de cristale");
        toAdd.add(m1);
        
        Medium m2 = new Cartomancien("Mme Irma", "F", "Comprenez votre entourage grâce à mes cartes ! Résultats rapides.");
        toAdd.add(m2);
        
        Medium m3 = new Cartomancien("Endora", "F", "Mes cartes répondront à toutes vos questions personnelles.");
        toAdd.add(m3);
        
        Medium m4 = new Astrologue("Serena", "F", "Basée à Champigny-sur-Marne, Serena vous révèlera votre avenir pour éclairer votre passé.", "Ecole Normale Supérieure d'Astrologie (ENS-Astro)", "2006");
        toAdd.add(m4);
        
        Medium m5 = new Astrologue("Mr M", "H", "Avenir, avenir, que nous réserves-tu ? N'attendez-plus, demandez à me consulter !", "Institut des Nouveaux Savoirs Astrologiques", "2010");
        toAdd.add(m5);
        
        Medium m6 = new Spirite("Michel", "H", "Je suis spécialisé dans la communication avec les esprits. Venez communiquez avec moi :)", "Pendule");
        toAdd.add(m6);
        
        Medium m7 = new Cartomancien("Violetta", "F", "Je pratique la cartomancie depuis plus de 15 ans. Mes cartes sont mes alliées pour dévoiler les secrets de votre avenir.");
        toAdd.add(m7);
        
        Medium m8 = new Astrologue("Stefan", "H", "Je peux vous aider à mieux comprendre votre signe et les planètes qui influencent votre vie.", "Ecole Normale Supérieure d'Astrologie (ENS-Astro)", "2001");
        toAdd.add(m8);
        
        Medium m9 = new Spirite("Professeur Tran", "H", "Votre avenir est devant vous : regardons-le ensemble !", "Marc de café, boule de cristal, oreilles de lapin");
        toAdd.add(m9);
        
        Medium m10 = new Cartomancien("Lucas", "H", "Pratiquant la tarologie depuis plus de 10 ans, je suis persuadé que mes cartes permetront de lire votre avenir et de vous donner des conseils pour avancer dans la vie.");
        toAdd.add(m10);
        
        Medium m11 = new Astrologue("Chantal", "F", "L'astrologie est ma passion et j'ai l'honneur d'en avoir fait mon métier. C'est pourquoi je suis ravie de partager mes connaissances avec vous.", "Université Astrologique du Blabla", "2007");
        toAdd.add(m11);
        
        Medium m12 = new Spirite("Maître Nathan", "H", "Je suis là pour vous aider à entrer en contact avec vos proches disparus, mais aussi pour vous guider dans votre vie.", "Verre à esprits");
        toAdd.add(m12);
        
        Medium m13 = new Cartomancien("Sophie", "F", "Les cartes sont mon outil de prédilection pour vous aider à prendre les bonnes décisions pour votre futur. Faites-moi confiance, prenez rendez-vous.");
        toAdd.add(m13);
        
        Medium m14 = new Astrologue("Matthieu", "H", "J'ai aidé de nombreuses personnes à mieux comprendre leur personnalité et leur destinée grâce à mon puissant savoir-faire. N'hésitez plus, consultez-moi dès à présent.", "École des Astres Parfaits", "2012");
        toAdd.add(m14);
        
        Medium m15 = new Spirite("Emma", "F", "Mon don me permet d'entrer en contact avec des êtres spirituels pour vous guider sur votre chemin de vie.", "Matériaux conducteurs d'énergie");
        toAdd.add(m15);
        
        Medium m16 = new Cartomancien("Aristote", "H", "Je suis spécialisé dans l'art de la divination par les cartes. Je suis là pour vous donner des solutions concrètes à tous vos problèmes.");
        toAdd.add(m16);
        
        Medium m17 = new Astrologue("Laura", "F", "Passionnée par l'étude des astres et de leur influence sur notre destin. J'ai fait des études pour vos permettre de comprendre votre personnalité et votre avenir. Sollicitez-moi.", "Centre de Savoirs Astrologiques", "2008");
        toAdd.add(m17);
        
        Medium m18 = new Cartomancien("Rosemary", "F", "Mon don me permet d'entrer en contact avec des êtres spirituels pour vous aider dans votre vie.");
        toAdd.add(m18);
        
        Medium m19 = new Astrologue("Karine", "F", "Prenez rendez-vous avec Karine pour mieux comprendre les influences planétaires qui agissent sur votre avenir,et surtout prendre des décisions éclairées.", "Centre de Savoirs Astrologiques", "1985");
        toAdd.add(m19);
        
        Medium m20 = new Spirite("Mme Anais", "F", "Je suis spirite et je travaille avec les énergies pour vous aider à trouver des réponses à vos questions les plus profondes.", "Boule à énergie");
        toAdd.add(m20);
        
        
        MediumDao mDao = new MediumDao();
        try {
            JpaUtil.creerContextePersistance();
            JpaUtil.ouvrirTransaction();

            for (Medium addMedium : toAdd){
                mDao.create(addMedium);
            }
            
            JpaUtil.validerTransaction();
            
            
        } catch(Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();    
            toAdd = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return toAdd;
    }
    
    // permet d'initialiser les Clients
    public List<Client> initialiserClients(){
        List<Client> toAdd = new ArrayList<>();
        
        String[] noms = {"PASCAL", "FAVRO", "DONODIO GALVIS", "DEKEW", "LOU", "GUOGUEN", "HERNENDEZ", "OBADII", "CHONI-MENG-HIME", "HUONG", "LITT", "BEUZID", "ELILJ", "PAOI"};
        String[] prenoms = {"Alice", "Samuel", "Florine", "Simon", "Flavien", "Gabriela", "Vincent", "Yaelle", "Emmanuel", "Adrian Alejandro", "Raphaël", "Gaëtan", "Amine", "Othmane"};
        String[] datesInString = {"27/02/1975", "15/03/1989", "29/09/1980", "18/04/1984", "09/03/1972", "21/08/1994", "27/06/1996", "15/08/1973", "10/11/1980", "06/01/1988", "07/07/1997", "29/01/1972", "11/02/1989", "14/10/1997"};
        String[] adresses = {"21 rue Albert Einstein", "12 Rue Léon Blum, Villeurbanne", "5 Allée Marcel Achard, Villeurbanne", "14 Rue Lafontaine, Villeurbanne", "9 Rue François Gillet, Villeurbanne", "14 Rue Baudin, Villeurbanne", "6 Rue de la Ligne de l'Est, Villeurbanne", "5 Allée Henri Georges Clouzot, Villeurbanne", "2 Rue de la Fraternite, Villeurbanne", "13 Rue Jean Bertin, Villeurbanne", "18 Rue de Bel Air, Villeurbanne", "3 Rue Mauvert, Villeurbanne", "18 Avenue Galline, Villeurbanne", "20 Rue Bergonier, Villeurbanne"};
        String[] tels = {"06 88 77 44 55", "06 42 04 93 05", "06 71 15 05 03", "07 13 20 09 50", "04 37 34 05 32", "07 19 84 33 16", "04 41 56 41 93", "06 47 38 03 86", "05 57 74 58 29", "02 98 34 74 65", "07 55 52 70 86", "09 14 11 29 06", "04 26 62 84 48", "05 49 47 72 61"};
        String[] mails = {"alice.pascal@free.fr", "sfavro@free.fr", "florine.donodio-galvis@gmail.com", "sdekew4845@yahoo.com", "flavien.lou@yahoo.com", "gguoguen2418@hotmail.com", "vhernendez@yahoo.com", "yaelle.obadii@hotmail.com", "echoni-meng-hime4744@hotmail.com", "adrian-alejandro.huong@yahoo.com", "rlitt6371@hotmail.com", "gbeuzid4218@outlook.com", "amine.elilj@gmail.com", "opaoi@laposte.net"};
        String[] mdp = {"alice", "password123", "hello123", "qwertyuiop", "123456789", "letmein123", "admin123", "iloveyou123", "welcome123", "monkey123", "football123", "changeme123", "sunshine123", "abcd1234"};

        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        Date date;
        try {
            for (int i = 0; i < noms.length; i++){
                date = formatter.parse(datesInString[i]);
                Client client = new Client(noms[i], prenoms[i], date, adresses[i], tels[i], mails[i]);
                client.setMotDePasse(mdp[i]);
                AstroNetApi api = new AstroNetApi();
                List<String> profils = api.getProfil(client.getPrenom(), client.getDateNaissance());

                String signeZodiaque = profils.get(0);
                String signeChinois = profils.get(1);
                String couleur = profils.get(2);
                String animal = profils.get(3);

                ProfilAstral profilAstral = new ProfilAstral(signeZodiaque, signeChinois, couleur, animal);
                client.setProfilAstral(profilAstral);

                toAdd.add(client);         
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        ClientDao cDao = new ClientDao();
        try {
            JpaUtil.creerContextePersistance();
         
            JpaUtil.ouvrirTransaction();
            for (Client addClient : toAdd){
                cDao.create(addClient);
            }
            
            JpaUtil.validerTransaction();
        } catch(Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();    
            toAdd = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return toAdd;
    }
    
    // permet d'initialiser les Consultations
    public List<Consultation> initialiserConsultations(){
        ClientDao cDao = new ClientDao();
        EmployeDao eDao = new EmployeDao();
        MediumDao mDao = new MediumDao();
        ConsultationDao consDao = new ConsultationDao();

        List<Consultation> toAdd = new ArrayList<>();
        
        // persistance des nouvelles consultations
        try {
            JpaUtil.creerContextePersistance();
            
            // listes des clients, employés et médiums deja initialisés
            List<Client> listeCli = cDao.findAll();
            List<Employe> listeEmp = eDao.findAll();
            List<Medium> listeMed = mDao.findAll();

            for (int i = 0; i < 40; i++) {
                Date date = new Date();
                Time heure = new Time(date.getTime());

                // on choisit un medium, un employe, et un client random des listes correspondantes
                Client randomClient = listeCli.get( (int)(Math.random() * listeCli.size()) );
                Employe randomEmploye = listeEmp.get ((int)(Math.random() * listeEmp.size()) );
                Medium randomMedium = listeMed.get( (int)(Math.random() * listeMed.size()) );

                // creation de la consultation
                Consultation cons = new Consultation(randomMedium, randomEmploye, randomClient, date, heure);
                toAdd.add(cons);

                // délai de 2 secondes avant de créer la consultation suivante
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
         
            JpaUtil.ouvrirTransaction();
            for (Consultation addCons : toAdd){
                addCons.setTerminee(true);
                consDao.create(addCons);
            }
            
            JpaUtil.validerTransaction();
        } catch(Exception ex) {
            ex.printStackTrace();
            JpaUtil.annulerTransaction();    
            toAdd = null;
        }
        finally {
            JpaUtil.fermerContextePersistance();
        }
        return toAdd;
    }
    
}