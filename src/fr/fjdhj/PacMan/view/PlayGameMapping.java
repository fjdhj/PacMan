package fr.fjdhj.PacMan.view;

import java.util.ArrayList;
import java.util.List;

import fr.fjdhj.PacMan.MainClass;
import fr.fjdhj.PacMan.gameLogic.Direction;
import fr.fjdhj.PacMan.gameLogic.Wall;
import fr.fjdhj.PacMan.gameLogic.Entity.Ghost;
import fr.fjdhj.PacMan.gameLogic.Entity.PacMan;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Scale;

public class PlayGameMapping {
	
	/*
	 * ----------------------------------------------
	 * PARTIE FXML
	 * ----------------------------------------------
	 */
	
	/*
	 * NOTE : Pour la creation d'un stage, le couloir doit faire 28 pixel de large
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
	// x et y sont les coordon�es de l'image view. Or on veut qu'elle point au centre de celle ci
	// x' et y' sont les coordon�es de M' : x' = x+12
	//								        y' = y+12				
	//
	
	@FXML
	private ImageView PacMan;
	@FXML
	private ImageView Blinky; //Fantome rouge
	@FXML
	public AnchorPane pan;
	@FXML
	private AnchorPane pointPane;
	@FXML
	private FlowPane lifePan;
	@FXML
	private Label point;
	
	private PacMan player;
	private Ghost BlinkyEntity;
	private List<Wall> wall = new ArrayList<Wall>();
	private List<Circle> pointCoords = new ArrayList<Circle>();
	
		
	@FXML
	public void initialize(){	
		//Mets a PacMan (ImageView) notre image de PacMan
		PacMan.setImage(new Image(PlayGameMapping.class.getResourceAsStream("ressource/PacMan.png")));
		PacMan.setRotate(180);
		point.setText("0");
		//Mets a Blinky (ImageView) notre image de Blinky
		Blinky.setImage(new Image(PlayGameMapping.class.getResourceAsStream("ressource/test.png")));
		
		
		AnimationTimer game = new AnimationTimer() {
			@Override
			public void handle(long now) {
				PacMan.setLayoutX(player.getXPos().get()-12);
				PacMan.setLayoutY(player.getYPos().get()-12);
				
				Blinky.setLayoutX(BlinkyEntity.getXPos().get()-12);
				Blinky.setLayoutY(BlinkyEntity.getYPos().get()-12);
				
				double coef = Math.min(MainClass.getFenWidth() / pan.getPrefWidth(), MainClass.getFenHeight() / pan.getPrefHeight());
				Scale scale = new Scale();
				scale.setPivotX(0); 
				scale.setPivotY(0);
				scale.setX(coef); 
				scale.setY(coef);
				pan.getTransforms().clear(); 
				pan.getTransforms().add(scale);
			}
			
			
		};
		game.start();
		
	}


	public PlayGameMapping() {}
	
	
	/*
	 * ---------------------------------------------------
	 * Quelque fonctions utils
	 * ---------------------------------------------------
	 */
	
	//Perm� de fournir les entit� : fantome et PacMan et de r�cup�rer les murs
	//NOTE : les fantomes seront a ajouter via cette fonction
	public List<Wall> setEntityAndGetWall(PacMan player, Ghost Blinky) {
		this.player = player; 
		BlinkyEntity = Blinky;
		
		//R�cup�re les murs
		Node a;
		//On aparcoure les objets notre AnchorPan pricipale
		for(int i =0; i<pan.getChildren().size(); i++) {
			//Si c'est un AnchorPan -> stock des murs
			if((a=pan.getChildren().get(i)) instanceof AnchorPane) {
				for(Node w : ((Pane) a).getChildren()) {
					if(w instanceof Rectangle) {
						wall.add(new Wall((Rectangle) w));
						System.out.println(w);
					}else {
						break;
					}
				}
			}
		}
		for(int i = 0; i<pointPane.getChildren().size(); i++) {
			pointCoords.add((Circle) pointPane.getChildren().get(i));
			
				
		}
		
		return wall;
	}
	
	/*
	 * Listeneur pour mettre a jour l'afichage
	 */
	public void addListener() {
		//On �coute la direction de PacMan et si elle change :
		player.getDirection().addListener(new ChangeListener<Direction>(){
			@Override
			public void changed(ObservableValue<? extends Direction> arg0, Direction oldValue, Direction newValue) {
				//On mets a jour la rotation
				PacMan.setRotate(newValue.getRotate());				
			}});
		
		//On �coute le nombre de vie restante a PacMan et si elle change
		player.getLife().addListener(new ChangeListener<Number>(){
			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				//On met a jour l'affichage
				if((int) newValue > (int) oldValue) {
					for(int i = 0; (int)oldValue+i < (int)newValue; i++) {
						ImageView img = new ImageView(new Image(PlayGameMapping.class.getResourceAsStream("ressource/PacMan.png")));
						img.setFitWidth(15);
						img.setFitHeight(15);
						lifePan.getChildren().add(img);
					}
				}else {
					for(int i = 0; (int)newValue+i < (int)oldValue; i++) {
						lifePan.getChildren().remove(lifePan.getChildren().size()-1);
					}
				}
				
			}});
		//On �coute le nombre de point et si il change
		player.getPoint().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> arg0, Number arg1, Number arg2) {
				//On met a jour l'affichage
				point.setText(arg2.toString());
				
				//On ajoute une vie si on est a 10 000 pts
				if(player.getPointMultValue()+1 <= player.getPoint().get()) {
					player.addLife(1);
					player.addPointMult(1);
				}
			}
			
		});	
		
		BlinkyEntity.getDirection().addListener(new ChangeListener<Direction>() {

			@Override
			public void changed(ObservableValue<? extends Direction> arg0, Direction arg1, Direction arg2) {
				switch(arg2) {
				case DOWN:
					break;
				case LEFT:
					break;
				case RIGHT:
					break;
				case UP:
					break;
				default:
					break;
				
				}
				
			}		
		});
		
		pan.setOnMouseExited(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent arg0) {
				System.out.println("La souris quite la fenetre");
				
			}
			
		});		
	}
	
	public ImageView getImageViewPlayer() {
		return PacMan;
	}
	
	public ImageView getImageViewBlinky() {
		return Blinky;
	}

	public List<Circle> getPointCoords() {
		return pointCoords;
	}

	
}
