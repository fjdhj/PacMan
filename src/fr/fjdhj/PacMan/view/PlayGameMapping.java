package fr.fjdhj.PacMan.view;

import fr.fjdhj.PacMan.gameLogic.PacMan.PacMan;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class PlayGameMapping{
	
	/*
	 * ----------------------------------------------
	 * PARTIE FXML
	 * ----------------------------------------------
	 */
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
		//On créer un Timeline : pour déplacer le PacMan
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
                /*Bounds bounds = pan.getBoundsInLocal();
            	
            	//If the ball reaches the left or right border make the step negative
                if(PacManEntity.getLayoutX() <= (bounds.getMinX() + PacManEntity.getRadius()) || 
                		PacManEntity.getLayoutX() >= (bounds.getMaxX() - PacManEntity.getRadius()) ){


                }

                //If the ball reaches the bottom or top border make the step negative
                if((PacManEntity.getLayoutY() >= (bounds.getMaxY() - PacManEntity.getRadius())) || 
                        (PacManEntity.getLayoutY() <= (bounds.getMinY() + PacManEntity.getRadius()))){


                }*/
            }
        }));
        
        //Cycle infinie, pour que le PacMan BOUGE sans fin
        timeline.setCycleCount(Timeline.INDEFINITE);
        //Lancer l'animation
        timeline.play();
	}

	public PlayGameMapping(){}
	
	
	//Permé de fournir les entité : fantome et PacMan
	//NOTE : les fantomes seront a ajouter via cette fonction
	public void setEntity(PacMan player) {
		this.player = player; 
	}
	

	
}
