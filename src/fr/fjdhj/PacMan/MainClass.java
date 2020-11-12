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
	
	private final static boolean VERBOSE = true;
	
	@Override
	public void start(Stage primaryStage) {
		
		
		stagePrincipal = primaryStage;
		//Nom de la fen�tre
		stagePrincipal.setTitle("PacMan");	
		//Resout bug affichage au d�but d'une partie ?
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
	 * @param path : Le chemin d'acc�es au fichier FXML
	 * @return Object : Retourn le controleur
	 */
	public static Object initScene(URL path) {
		//Charge le contenue du fichier FXML
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(path);
		try {
			conteneurPrincipal = (AnchorPane) loader.load();
			//Cr�ation scene principale
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
	        
	        // Cr�ation d'un nouveau Stage qui sera d�pendant du Stage principal
	        Stage stageDialogue = new Stage();
	        stageDialogue.setTitle("Credit");
	        stageDialogue.initModality(Modality.WINDOW_MODAL);
	        
	        //Avec cette instruction, notre fen�tre modifi�e sera modale
	        //par rapport � notre stage principal
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
	
	/**
	 * Ouvre dans le navigateur
	 * @param URI
	 */
	public void openInBrowser(String URI) {
		services.showDocument(URI);
	}
	
	/**
	 * En fonction de la variable boolean VERBOSE, afficher ou non le messages
	 * @param str Message a afficher
	 */
	public static void verbose(String str) {
		if(VERBOSE)
			System.out.println(str);
	}
}
