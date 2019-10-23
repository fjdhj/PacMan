package fr.fjdhj.PacMan.view;

import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.PacMan.PacMan;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.RotateEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

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
	
		
	@FXML
	public void initialize(){	
		//Mets a PacMan (ImageView) notre image de PacMan
		PacMan.setImage(new Image(PlayGameMapping.class.getResourceAsStream("ressource/PacMan.png")));
		
		//On aparcoure les objets notre AnchorPan pricipale
		for(int i =0; i<pan.getChildren().size(); i++) {
			//Si ce n'est pas PacMan
			if(!pan.getChildren().get(i).equals(PacMan)) {
				
			}

		}
		
		/*//On créer un Timeline : pour déplacer le PacMan
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent t) {
            	//Déplace le PacMan graphiquement
            	PacMan.setLayoutX(player.getXPos());
            	PacMan.setLayoutY(player.getYPos());
            	
            	//Vérifie la rotation de PacMan
            	PacMan.setRotate(player.getDirection().getRotate());
            	
            	//Si le PacMan est bloquer l'arréter
            	//NOTE : les lignes qui suive ne SERVE A RIEN, LES IGNORER
                //Bounds bounds = pan.getBoundsInLocal();
            	
            	//If the ball reaches the left or right border make the step negative
                if(PacManEntity.getLayoutX() <= (bounds.getMinX() + PacManEntity.getRadius()) || 
                		PacManEntity.getLayoutX() >= (bounds.getMaxX() - PacManEntity.getRadius()) ){


                }

                //If the ball reaches the bottom or top border make the step negative
                if((PacManEntity.getLayoutY() >= (bounds.getMaxY() - PacManEntity.getRadius())) || 
                        (PacManEntity.getLayoutY() <= (bounds.getMinY() + PacManEntity.getRadius()))){


                }//
            }
        }));
        
        //Cycle infinie, pour que le PacMan BOUGE sans fin
        timeline.setCycleCount(Timeline.INDEFINITE);
        //Lancer l'animation
        timeline.play();*/
	}

	public PlayGameMapping(){}
	
	
	//Permé de fournir les entité : fantome et PacMan
	//NOTE : les fantomes seront a ajouter via cette fonction
	public void setEntity(PacMan player) {
		this.player = player; 
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
