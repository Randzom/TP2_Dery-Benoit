package com.example.testjavafx;

import com.example.testjavafx.ClientFXControlleur;
import com.example.testjavafx.ClientFXModele;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Popup;
import javafx.stage.Stage;

public class ClientFXVue extends Application {
    Popup notification = new Popup();
    public TextField champPrenom = new TextField();
    public TextField champNom = new TextField();
    public TextField champEmail = new TextField();
    public TextField champMatricule = new TextField();

    public ClientFXVue(TextField champPrenom, TextField champNom, TextField champEmail, TextField champMatricule) {
        this.champPrenom = champPrenom;
        this.champNom = champNom;
        this.champEmail = champEmail;
        this.champMatricule = champMatricule;
    }


    @Override
    public void start(Stage stage) throws Exception {
        ClientFXControlleur ctrl = new ClientFXControlleur(new ClientFXModele(), this);

        try {
            GridPane root = new GridPane();

            Scene scene = new Scene(root, 600, 600);

            VBox chargementBox = new VBox();
            chargementBox.setPadding(new Insets(0,10,10,10));

            VBox inscriptionBox = new VBox();
            inscriptionBox.setAlignment(Pos.TOP_CENTER);

            Course courstest = new Course("prog1", "IFT1015", "Hiver");

            TableView<Course> tableauDesCours = new TableView<>();
            TableColumn<Course, String> coloneCode = new TableColumn<Course, String>("Code");
            TableColumn<Course, String> coloneCours = new TableColumn<Course, String>("Cours");

            coloneCode.setCellValueFactory(new PropertyValueFactory("code"));
            coloneCours.setCellValueFactory(new PropertyValueFactory("name"));

            tableauDesCours.getColumns().add(coloneCode);
            tableauDesCours.getColumns().add(coloneCours);


            GridPane chargementOptions = new GridPane();
            ComboBox<String> sessions = new ComboBox<>();
            sessions.getItems().addAll("Automne", "Hiver", "Ete");
            sessions.getSelectionModel().select(0);

            Button boutonCharger = new Button("charger");

            chargementOptions.add(sessions,0,0);
            chargementOptions.add(boutonCharger,1,0);

            Button boutonFormulaire = new Button("envoyer");
            boutonFormulaire.setOnAction((action) -> {
                try {
                    ctrl.envoyer();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            chargementBox.getChildren().add(titre("Liste des cours"));
            chargementBox.getChildren().add(tableauDesCours);
            chargementBox.getChildren().add(chargementOptions);

            inscriptionBox.getChildren().add(titre("Formulaire d'inscription"));
            /*GridPane champPrenom = champFormulaire("Prénom");
            GridPane champNom = champFormulaire("Nom");
            GridPane champEmail = champFormulaire("Email");
            GridPane champMatricule = champFormulaire("Matricule");*/
            inscriptionBox.getChildren().add(champFormulaire("Prénom",this.champPrenom));
            inscriptionBox.getChildren().add(champFormulaire("Nom", this.champNom));
            inscriptionBox.getChildren().add(champFormulaire("Email", this.champEmail));
            inscriptionBox.getChildren().add(champFormulaire("Matricule", this.champMatricule));
            inscriptionBox.getChildren().add(boutonFormulaire);

           /* boutonFormulaire.setOnAction((action) -> {
                ClientFXControlleur.envoyer();
            });*/

            root.add(chargementBox,0,0);
            root.add(inscriptionBox,1,0);


            stage.setTitle("Inscription UdeM");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public Text titre(String titre) {
        Text result = new Text(titre);
        result.setFont(Font.font(24));
        return result;
    }
    public GridPane champFormulaire(String nom, TextField champ) {
        Text text = new Text(nom);
        champ = new TextField();
        GridPane result = new GridPane();
        result.getColumnConstraints().add(new ColumnConstraints(75));

        result.add(text, 0, 0);
        result.add(champ, 1, 0);

        return result;
    }
}
    public static void main(String[] args) {

        ClientFXVue.start();
    }
