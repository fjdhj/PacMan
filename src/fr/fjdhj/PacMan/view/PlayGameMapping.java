package fr.fjdhj.PacMan.view;

import fr.fjdhj.PacMan.gameLogic.PacMan.PacMan;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class PlayGameMapping{
	
	/*
	 * ----------------------------------------------
	 * PARTIE FXML
	 * ----------------------------------------------
	 */
	@FXML
	private Circle PacManEntity;
	@FXML
	private ImageView PacMan;
	@FXML
	public AnchorPane pan;
	
	
	private PacMan player;
	
		
	@FXML
	public void initialize(){	
		//On créer un Timeline : pour déplacer le PacMan
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
        	
            @Override
            public void handle(ActionEvent t) {
            	//Déplace le PacMan
            	PacMan.setLayoutX(player.getXPos());
            	PacMan.setLayoutY(player.getYPos());

                Bounds bounds = pan.getBoundsInLocal();
                
                //Si le PacMan est bloquer l'arréter
                //NOTE : les deux if qui suive se SERVE A RIEN, LES IGNORER
                
                //If the ball reaches the left or right border make the step negative
                if(PacManEntity.getLayoutX() <= (bounds.getMinX() + PacManEntity.getRadius()) || 
                		PacManEntity.getLayoutX() >= (bounds.getMaxX() - PacManEntity.getRadius()) ){


                }

                //If the ball reaches the bottom or top border make the step negative
                if((PacManEntity.getLayoutY() >= (bounds.getMaxY() - PacManEntity.getRadius())) || 
                        (PacManEntity.getLayoutY() <= (bounds.getMinY() + PacManEntity.getRadius()))){


                }
            }
        }));
        
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
	}

	public PlayGameMapping(){}
	
	
	//Permé de fournir les entité : fantome et PacMan
	//NOTE : les fantome
	public void setEntity(PacMan player) {
		this.player = player; 
	}
	

	
}
