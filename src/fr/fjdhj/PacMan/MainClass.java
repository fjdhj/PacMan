package fr.fjdhj.PacMan;

import java.io.IOException;
import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainClass extends Application {
	
	private static Stage stagePrincipal;
	private static AnchorPane conteneurPrincipal;
	
	@Override
	public void start(Stage primaryStage) {
		stagePrincipal = primaryStage;
		//Nom de la fenêtre
		stagePrincipal.setTitle("PacMan");		
		//Charge le fichier FXML pour afficher le contenu, ici mainMenu
		initScene(MainClass.class.getResource("view/MainMenu.fxml"));
	}
	
	public static void main(String[] args) {
		launch(args);
		
	}
	
	
	/*
	 * ------------------------------------------
	 * FONCTION SUPPLEMENTAIRE
	 * ------------------------------------------ 
	 */
	
	
	/**
	 * Change la scene principale
	 * @param path : Le chemin d'accées au fichier FXML
	 * @return Object : Retourn le controleur
	 */
	public static Object initScene(URL path) {
		//Charge le contenue du fichier FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(path);
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
		return loader.getController();
	}
}
