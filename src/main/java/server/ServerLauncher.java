package server;

/**
 * Classe qui lance un serveur sur le port 1337.
 */
public class ServerLauncher {
    /**
     * Le port où sera lancé le serveur, initialisé à 1337.
     */
    public final static int PORT = 1337;

    /**
     * Méthode principale (main), lance un nouveau serveur sur le <code>PORT</code>.
     * Si une <code>Exception</code> est rencontrée, sa trace est imprimée.
     *
     * @param args laissé vide, pas utilisé dans la ligne de commande
     */
    public static void main(String[] args) {
        Server server;
        try {
            server = new Server(PORT);
            System.out.println("Server is running...");
            server.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}