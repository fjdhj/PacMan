package fr.fjdhj.PacMan;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainClass extends Application {

	/*
	 * Note : ici, le conteneur principale est la menuBar
	 */
	
	private Stage stagePrincipal;
	private AnchorPane conteneurPrincipal;
	
	@Override
	public void start(Stage primaryStage) {
		stagePrincipal = primaryStage;
		//Nom de la fenêtre
		stagePrincipal.setTitle("PacMan");
		
		//Charge les fichiers FXML pour afficher le contenu
		initMainMenu();
	}
	
	private void initMainMenu() {
		//Charge le contenue du fichier FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(MainClass.class.getResource("view/MainMenu.fxml"));
		try {
			conteneurPrincipal = (AnchorPane) loader.load();
			//Création scene principale
			Scene scene = new Scene(conteneurPrincipal);
			//Ajout de la scene dans le stage
			stagePrincipal.setScene(scene);
			//Affiche le stage
			stagePrincipal.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}


	public static void main(String[] args) {
		launch(args);
	}
}
