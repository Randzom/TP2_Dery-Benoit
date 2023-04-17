package server;

import javafx.util.Pair;
import server.models.Course;

import java.io.*;
import java.util.Scanner;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Un serveur qui reçoit des requêtes pour visualiser les cours du fichier cours.txt
 * ou pour entrer des inscriptions dans le fichier inscription.txt.
 */
public class Server {

    /**
     * la <code>String</code> asoociée à la commande d'inscription
     */
    public final static String REGISTER_COMMAND = "INSCRIRE";
    /**
     * la <code>String</code> associée à la commande de chargement
     */
    public final static String LOAD_COMMAND = "CHARGER";
    private final ServerSocket server;
    private Socket client;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private final ArrayList<EventHandler> handlers;

    /**
     * l'unique constructeur de la classe <code>Server</code>. Il initialise ce serveur sur le port
     * entré en paramètre, pour un seul client à la fois, puis il initialise ses manipulateurs d'événements.
     *
     * @param port le port sur lequel ce serveur sera lancé
     * @throws IOException Si une exception d'entrée ou de sortie (input/output) se produit
     */
    public Server(int port) throws IOException {
        this.server = new ServerSocket(port, 1);
        this.handlers = new ArrayList<EventHandler>();
        this.addEventHandler(this::handleEvents);
    }

    /**
     * Ajoute un manipulateur d'événement dans la liste de manipulateurs de ce serveur.
     *
     * @param h le manipulateur d'événement à ajouter
     * @see EventHandler
     */
    public void addEventHandler(EventHandler h) {
        this.handlers.add(h);
    }

    /**
     * Appelle la méthode <code>handle</code> de chacun des manipulateurs d'événements de ce
     * serveur, avec une certaine commande une certaine ligne d'arguments.
     *
     * @param cmd la <code>String</code> de commande qui //TODO
     * @param arg
     */
    private void alertHandlers(String cmd, String arg) {
        for (EventHandler h : this.handlers) {
            h.handle(cmd, arg);
        }
    }

    /**
     *
     */
    public void run() {
        while (true) {
            try {
                client = server.accept();
                System.out.println("Connecté au client: " + client);
                objectInputStream = new ObjectInputStream(client.getInputStream());
                objectOutputStream = new ObjectOutputStream(client.getOutputStream());
                listen();
                disconnect();
                System.out.println("Client déconnecté!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *
      * @throws IOException
     * @throws ClassNotFoundException
     */
    public void listen() throws IOException, ClassNotFoundException {
        String line;
        if ((line = this.objectInputStream.readObject().toString()) != null) {
            Pair<String, String> parts = processCommandLine(line);
            String cmd = parts.getKey();
            String arg = parts.getValue();
            this.alertHandlers(cmd, arg);
        }
    }

    /**
     *
     * @param line
     * @return
     */
    public Pair<String, String> processCommandLine(String line) {
        String[] parts = line.split(" ");
        String cmd = parts[0];
        String args = String.join(" ", Arrays.asList(parts).subList(1, parts.length));
        return new Pair<>(cmd, args);
    }

    /**
     *
     * @throws IOException
     */
    public void disconnect() throws IOException {
        objectOutputStream.close();
        objectInputStream.close();
        client.close();
    }

    /**
     *
     * @param cmd
     * @param arg
     */
    public void handleEvents(String cmd, String arg) {
        if (cmd.equals(REGISTER_COMMAND)) {
            handleRegistration(arg);
        } else if (cmd.equals(LOAD_COMMAND)) {
            handleLoadCourses(arg);
        }
    }

    /**
     Lire un fichier texte contenant des informations sur les cours et les transformer en liste d'objets 'Course'.
     La méthode filtre les cours par la session spécifiée en argument.
     Ensuite, elle renvoie la liste des cours pour une session au client en utilisant l'objet 'objectOutputStream'.
     La méthode gère les exceptions si une erreur se produit lors de la lecture du fichier ou de l'écriture de l'objet dans le flux.
     @param arg la session pour laquelle on veut récupérer la liste des cours
     */
    public void handleLoadCourses(String arg) {
        try {
            Scanner scan = new Scanner(new File("src/main/java/server/data/cours.txt"));
            ArrayList<Course> courseList = new ArrayList<Course>();
            while (scan.hasNext()) {
                String line = scan.nextLine();
                String code = line.split("\\t")[0];
                String name = line.split("\\t")[1];
                String session = line.split("\\t")[2];
                if (session.equals(arg)) {
                    courseList.add(new Course(name, code, session));
                }
            }
            scan.close();
            objectOutputStream.writeObject(courseList);
            System.out.println("Liste de cours pour la session demandée a été envoyée");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            System.out.println("Exportation dans le flux échouée");
        }
    }

    /**
     Récupérer l'objet 'RegistrationForm' envoyé par le client en utilisant 'objectInputStream', l'enregistrer dans un fichier texte
     et renvoyer un message de confirmation au client.
     La méthode gére les exceptions si une erreur se produit lors de la lecture de l'objet, l'écriture dans un fichier ou dans le flux de sortie.
     */
    public void handleRegistration(String arg) {
        try {
            System.out.println("Inscription en cours");
            String prenom = arg.split("&")[0];
            String nom = arg.split("&")[1];
            String email = arg.split("&")[2];
            String matricule = arg.split("&")[3];
            String courseName = arg.split("&")[4];
            String code = arg.split("&")[5];
            String session = arg.split("&")[6];


            FileWriter registration = new FileWriter("src/main/java/server/data/inscription.txt", true);
            String newInscription = session + " " + code + " " + matricule + "   " + prenom + " " + nom + " " + email;
            BufferedWriter buffRegistration = new BufferedWriter(registration);
            buffRegistration.write(newInscription);
            buffRegistration.close();
            registration.close();
            System.out.println("Inscription enregistrée avec succès");
            String confirmation = "Inscription réussite";
            objectOutputStream.writeObject(confirmation);

            } catch (FileNotFoundException e) {
                System.out.println("Impossible d'écrire dans le fichier (inscription.txt)");
            } catch (IOException e) {
            System.out.println("Erreur lors de la réception du formulaire");
        }

    }
    }

