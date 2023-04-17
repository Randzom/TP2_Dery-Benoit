package client.model;

import java.net.UnknownHostException;
import java.util.Scanner;
import server.models.Course;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;


public class Client {

    private static Socket clientSocket;

    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    public static void client() {
        System.out.println("Bienvenue au portail d'inscription de l'UDEM");
        chargerListe();

    }
    public static void chargerListe(){
        try{
            clientSocket = new Socket("127.0.0.1", 1337);
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours (entrez le nombre correspondant au choix):");
            System.out.println("1. Automne\\n" +
                    "2. Hiver\\n" +
                    "3. Ete\\n");
            Scanner scanner = new Scanner(System.in);
            System.out.print("Choix: ");
            String sessionChoisi = null;
                String line = scanner.nextLine();
                switch (line) {
                    case "1" -> {
                        sessionChoisi = "Automne";
                    }
                    case "2" -> {
                        sessionChoisi = "Hiver";
                    }
                    case "3" -> {
                        sessionChoisi = "Ete";
                    }

                }
                String command = "CHARGER " + sessionChoisi;
                System.out.println("Les cours offerts durant la session d'" + sessionChoisi + " sont:");

                System.out.println("Ouverture Outputput");
                objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
                System.out.println("Écriture");
                objectOutputStream.writeObject(command);
                System.out.println("Requête de la liste de cours envoyé");
                System.out.println(command);
                //String receivedList = objectInputStream.readObject();
                objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
                ArrayList<Course> courseList= (ArrayList<Course>) objectInputStream.readObject();
                System.out.println("Cours reçu");

                int count = 0;
                while (courseList.size() > count) {
                    Course tempCourse = (Course) courseList.get(count);
                    String code = tempCourse.getCode();
                    String name = tempCourse.getName();
                    int orderCount = count + 1;
                    System.out.println(orderCount + "." + code + "\\t" + name);
                    count++;
            }
                System.out.println("Choix: \\n" +
                                    "1. Consulter les cours offerts pour une autre session \\n" +
                                    "2. Inscription à un cours");
            System.out.print("Choix: ");
                String choiceLine = scanner.nextLine();
                switch (choiceLine) {
                    case "1" -> {
                        chargerListe();
                    }
                    case "2" -> {
                        envoyerInscription(courseList);
                    }
                };
    } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
public static void envoyerInscription(ArrayList<Course> courseList) throws IOException {
    Scanner inscriptionScanner = new Scanner (System.in);
    System.out.print("Veuillez saisir votre prénom");
    String prenom = inscriptionScanner.nextLine();

    System.out.print("Veuillez saisir votre nom");
    String nom = inscriptionScanner.nextLine();

    System.out.print("Veuillez saisir votre email");
    String email = inscriptionScanner.nextLine();

    System.out.print("Veuillez saisir votre matricule");
    String matricule = inscriptionScanner.nextLine();

    System.out.print("Veuillez saisir le code du cours");
    String code = inscriptionScanner.nextLine();

    ArrayList<Course> currentCourseList = courseList;


    boolean courseFound = false;
    int count = 0;
    String tempName = null;
    String tempSession = null;
    while (currentCourseList.size() > count && courseFound == false) {

        Course tempCourse = currentCourseList.get(count);
        String tempCode = tempCourse.getCode().toString();
        if (tempCode.equals(code)) {
            courseFound = true;
            tempName = tempCourse.getName().toString();
            tempSession = tempCourse.getSession().toString();
            break;
        }
        count++;
    }
    if (courseFound == false){
        System.out.println("Le cours sélectionné n'existe pas dans la liste précédemment chargée. Veuillez réessayer");
        chargerListe();
    }
    Course inscriptionCourse = new Course(tempName, code, tempSession);

    RegistrationForm registrationForm = new RegistrationForm(prenom, nom, email, matricule, inscriptionCourse);
    InscriptionStreamObject inscriptionCommand = new InscriptionStreamObject("INSCRIRE", registrationForm);

    objectOutputStream.writeObject(inscriptionCommand);

    inscriptionScanner.close();

    chargerListe();
    }

    public static void main(String[] args) {
        client();
    }
}
