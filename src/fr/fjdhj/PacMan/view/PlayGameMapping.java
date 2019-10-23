package fr.fjdhj.PacMan.view;

import java.util.ArrayList;
import java.util.List;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Wall;
import fr.fjdhj.PacMan.gameLogic.PacMan.PacMan;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

public class PlayGameMapping{
	
	/*
	 * ----------------------------------------------
	 * PARTIE FXML
	 * ----------------------------------------------
	 */
	
	//  ATTENTION AU COORDONEES DE L'IMAGE VIEW
	
	// Limage view :
	//		  M(x,y)
	//			x--------
	//			|   M'  |
	//			|	x	|
	//			|		|
	//			---------
	//
	// x et y sont les coordonées de l'image view. Or on veut qu'elle point au centre de celle ci
	// x' et y' sont les coordonées de M' : x' = x+12
	//								        y' = y+12				
	//
	
	@FXML
	private ImageView PacMan;
	
	
	@FXML
	public AnchorPane pan;
	
	private PacMan player;
	private List<Wall> wall = new ArrayList<Wall>();
	
		
	@FXML
	public void initialize(){	
		//Mets a PacMan (ImageView) notre image de PacMan
		PacMan.setImage(new Image(PlayGameMapping.class.getResourceAsStream("ressource/PacMan.png")));
	}

	public PlayGameMapping(){}
	
	
	/*
	 * ---------------------------------------------------
	 * Quelque fonctions utils
	 * ---------------------------------------------------
	 */
	
	//Permé de fournir les entité : fantome et PacMan et de récupérer les murs
	//NOTE : les fantomes seront a ajouter via cette fonction
	public List<Wall> setEntityAndGetWall(PacMan player) {
		this.player = player; 
		
		//Récupère les murs
		Node w;
		//On aparcoure les objets notre AnchorPan pricipale
		for(int i =0; i<pan.getChildren().size(); i++) {
			//Si c'est un AnchorPan -> donc un mur
			if((w=pan.getChildren().get(i)) instanceof Rectangle) {
				wall.add(new Wall((Rectangle) w));
				System.out.println(w);
			}

		}
		return wall;
	}
	
	
	public void addListener() {
		//On écoute la direction de PacMan et si elle change :
		player.getDirection().addListener(new ChangeListener<Direction>(){
			@Override
			public void changed(ObservableValue<? extends Direction> arg0, Direction oldValue, Direction newValue) {
				//On mets a jour la rotation
				PacMan.setRotate(newValue.getRotate());				
			}});
		
		//On écoute les coordoonées Y de PacMan et si elles changent :
		player.getYPos().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//On mets a jour la position Y
				//NOTE : l'explication du -12 se trouve en haut
				PacMan.setLayoutY(((double) newValue) - 12);
				
			}});
		
		//On écoute les coordoonées X de PacMan et si elles changent :
		player.getXPos().addListener(new ChangeListener<Number>() {
			
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//On mets a jour la position X
				//NOTE : l'explication du -12 se trouve en haut
				PacMan.setLayoutX(((double) newValue) - 12);
				
			}});
		
		//On écoute le nombre de vie restante a PacMan et si elle change
		player.getLife().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//On met a jour l'affichage
				
			}});
		
		
		
	}
	
}
