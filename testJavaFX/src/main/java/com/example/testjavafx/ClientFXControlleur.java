package com.example.testjavafx;

import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class ClientFXControlleur {
    private ClientFXModele modele;
    private static ClientFXVue vue;
    public ClientFXControlleur(ClientFXModele modele, ClientFXVue vue) {
        this.modele = modele;
        this.vue = vue;
    }
    public void envoyer() throws Exception {
        try {Course e = new Course("name","code","session");
        String prenom = this.vue.champPrenom.getText();
        String nom = this.vue.champNom.getText();
        String email = this.vue.champEmail.getText();
        String matricule = this.vue.champMatricule.getText();
        String message = ClientFXModele.envoyer(prenom,nom,email,matricule,e);

        prenom = "";
        nom = "";
        email = "";
        matricule = "";

        Button ok = new Button("Ok");
        vue.notification.getContent().add(new Text(message));
        vue.notification.getContent().add(ok);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    }
