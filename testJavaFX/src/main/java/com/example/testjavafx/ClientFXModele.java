package com.example.testjavafx;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Scanner;

public class ClientFXModele {
    private static Socket clientSocket;
    private static ObjectInputStream objectInputStream;
    private static ObjectOutputStream objectOutputStream;

    public static boolean disconnect = false;

    public static final Scanner scanner = new Scanner(System.in);

    public ClientFXModele() {

    }

    public static ArrayList<Course> charger(String session) throws IOException, ClassNotFoundException {
        clientSocket = new Socket("127.0.0.1", 1337);
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject("CHARGER " + session);

        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
        ArrayList<Course> courseList= (ArrayList<Course>) objectInputStream.readObject();

        clientSocket.close();
        return courseList;
    }

    public static String envoyer(String prenom, String nom, String email, String matricule, Course cours) throws Exception {
        clientSocket = new Socket("127.0.0.1", 1337);

        String registrationString = prenom + "&" + nom + "&" + email + "&" + matricule + "&" +
                cours.getName() + "&" + cours.getCode() + "&" + cours.getSession();
        objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
        objectOutputStream.writeObject("INSCRIRE " + registrationString);
        objectInputStream = new ObjectInputStream(clientSocket.getInputStream());

        clientSocket.close();
        return objectInputStream.readObject().toString();
    }

    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~//

    public static void envoyerInscription(ArrayList<Course> courseList) {
        try {
            System.out.print("Veuillez saisir votre prénom: ");
            String prenom = scanner.nextLine();

            System.out.print("Veuillez saisir votre nom: ");
            String nom = scanner.nextLine();

            System.out.print("Veuillez saisir votre email: ");
            String email = scanner.nextLine();

            System.out.print("Veuillez saisir votre matricule: ");
            String matricule = scanner.nextLine();

            System.out.print("Veuillez saisir le code du cours: ");
            String code = scanner.nextLine();

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
                }
                count++;
            }
            if (courseFound == false) {
                System.out.println("Le cours sélectionné n'existe pas dans la liste précédemment chargée. Veuillez réessayer");

                chargerListe();
            }

            String registrationString = prenom + "&" + nom + "&" + email + "&" + matricule + "&" + tempName + "&" + code + "&" + tempSession;
            String command = "INSCRIRE " + registrationString;
            clientSocket = new Socket("127.0.0.1", 1337);
            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(command);
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            String message = objectInputStream.readObject().toString();
            System.out.println(message);


            //System.out.println(objectInputStream.readObject());
        } catch (UnknownHostException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        };
    }
    public static void client() {
        try {
            System.out.println("Bienvenue au portail d'inscription de l'UDEM");
            while (disconnect == false) {
                chargerListe();
                clientSocket.close();
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

            objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            objectOutputStream.writeObject(command);
            System.out.println(command);
            objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
            ArrayList<Course> courseList= (ArrayList<Course>) objectInputStream.readObject();

            int count = 0;
            while (courseList.size() > count) {
                Course tempCourse = (Course) courseList.get(count);
                String code = tempCourse.getCode();
                String name = tempCourse.getName();
                int orderCount = count + 1;
                System.out.println(orderCount + "." + code + " \t " + name);
                count++;
            }
            System.out.println("Veuillez entrer le numéro correspondant à l'action ciblé");
            System.out.println("1. Consulter les cours offerts pour une autre session");
            System.out.println("2. Inscription à un cours");
            System.out.println("3. Déconnecter");
            System.out.println("Choix: ");

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
    }


