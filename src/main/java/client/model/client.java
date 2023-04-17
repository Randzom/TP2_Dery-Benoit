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

    public static boolean disconnect = false;

    public static final Scanner scanner = new Scanner(System.in);

    public static void client() {
        try {
            System.out.println("Bienvenue au portail d'inscription de l'UDEM");
            while (disconnect == false) {
                chargerListe();
            }
            scanner.close();
            clientSocket.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void chargerListe(){
        try{
            clientSocket = new Socket("127.0.0.1", 1337);
            System.out.println("Veuillez choisir la session pour laquelle vous voulez consulter la liste de cours (entrez le nombre correspondant au choix):");
            System.out.println("1. Automne");
            System.out.println("2. Hiver");
            System.out.println("3. Ete");
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
                    System.out.println(orderCount + "." + code + " \t " + name);
                    count++;
            }
                System.out.println("Choix:");
            System.out.println("1. Consulter les cours offerts pour une autre session");
            System.out.println("2. Inscription à un cours");
            System.out.println("3. Déconnecter");

                String choiceLine = scanner.nextLine();
                switch (choiceLine) {
                    case "1" -> {
                    }
                    case "2" -> {
                        envoyerInscription(courseList);
                    }
                    case "3" -> {
                        disconnect = true;
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
    try {
        clientSocket = new Socket("127.0.0.1", 1337);
        System.out.print("Veuillez saisir votre prénom");
        String prenom = scanner.nextLine();

        System.out.print("Veuillez saisir votre nom");
        String nom = scanner.nextLine();

        System.out.print("Veuillez saisir votre email");
        String email = scanner.nextLine();

        System.out.print("Veuillez saisir votre matricule");
        String matricule = scanner.nextLine();

        System.out.print("Veuillez saisir le code du cours");
        String code = scanner.nextLine();

        ArrayList<Course> currentCourseList = courseList;


        boolean courseFound = false;
        int count = 0;
        String tempName = null;
        String tempSession = null;
        while (currentCourseList.size() > count && courseFound == false) {

            Course tempCourse = currentCourseList.get(count);
            String tempCode = tempCourse.getCode().toString();
            System.out.println(tempCourse + " et " + tempCode + " égal " + code);
            if (tempCode.equals(code)) {
                System.out.println("Trouvé");
                courseFound = true;
                tempName = tempCourse.getName().toString();
                tempSession = tempCourse.getSession().toString();
            }
            count++;
        }
        if (courseFound == false) {
            System.out.println("Le cours sélectionné n'existe pas dans la liste précédemment chargée. Veuillez réessayer");
            chargerListe();
        }
        System.out.println("Boucle de recherche terminé");
        Course inscriptionCourse = new Course(tempName, code, tempSession);


        String registrationString = prenom + "." + nom + "." + email + "." + matricule + "." + tempName + "." + code + "." + tempSession;
        String command = "INSCRIRE " + registrationString;
        System.out.println("Envoie de la demmande d'inscription");
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject(command);
        System.out.println("Rechargement de la liste");

        //System.out.println(objectInputStream.readObject());
    } finally {

    }
    ;
}

    public static void main(String[] args) {
        client();
    }
}
