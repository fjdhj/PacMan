package fr.fjdhj.PacMan;

import java.io.IOException;
import java.net.URL;

import fr.fjdhj.PacMan.view.CreditMapping;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MainClass extends Application {
	
	private static Stage stagePrincipal;
	private static AnchorPane conteneurPrincipal;
	private final HostServices services = this.getHostServices();
	
	@Override
	public void start(Stage primaryStage) {
		
		
		stagePrincipal = primaryStage;
		//Nom de la fenêtre
		stagePrincipal.setTitle("PacMan");	
		//Resout bug affichage au début d'une partie ?
		stagePrincipal.requestFocus();
		
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
			//Mets le stage au centre
			stagePrincipal.centerOnScreen();
			//Affiche le stage
			stagePrincipal.show();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loader.getController();
	}
	
	public static void creditDialog() {
		try {
	        FXMLLoader loader = new FXMLLoader();
	        loader.setLocation(MainClass.class.getResource("view/Credit.fxml"));
	        AnchorPane page = (AnchorPane) loader.load();
	        
	        // Création d'un nouveau Stage qui sera dépendant du Stage principal
	        Stage stageDialogue = new Stage();
	        stageDialogue.setTitle("Credit");
	        stageDialogue.initModality(Modality.WINDOW_MODAL);
	        
	        //Avec cette instruction, notre fenêtre modifiée sera modale
	        //par rapport à notre stage principal
	        stageDialogue.initOwner(stagePrincipal);
	        Scene scene = new Scene(page);
	        stageDialogue.setScene(scene);
	        
	        CreditMapping controller = loader.getController();
	        
	        
	        // Show the dialog and wait until the user closes it
	        stageDialogue.showAndWait();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void openInBrowser(String URI) {
		services.showDocument(URI);
	}
}
