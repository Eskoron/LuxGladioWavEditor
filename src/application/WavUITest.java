package application;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import ui.WavUI;

public class WavUITest extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("LuxGladio");
			BorderPane root = new BorderPane();
			Scene scene = new Scene(root,640,400);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.show();
			root.setCenter(new WavUI());
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}