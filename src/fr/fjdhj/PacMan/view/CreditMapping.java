package fr.fjdhj.PacMan.view;

import java.io.File;
import java.net.MalformedURLException;

import fr.fjdhj.PacMan.MainClass;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

public class CreditMapping {

	@FXML
	WebView ressource;
	
	@FXML
	private void initialize() throws MalformedURLException {
		WebEngine webEngine = ressource.getEngine();
		JSObject jsobj = (JSObject) webEngine.executeScript("window");
		jsobj.setMember("app", new MainClass());
		webEngine.load(new File("src/fr/fjdhj/PacMan/view/ressource/Credit.html").toURI().toURL().toString());
	}
	
	public CreditMapping() {}
}
