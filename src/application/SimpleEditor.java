package application;

import java.io.File;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import wav.WavPitcher;


public class SimpleEditor  extends AnchorPane  {
	
	
	
	@FXML Button LoadHum;
	@FXML Button LoadClash;
	@FXML Slider HighPitch;
	@FXML Slider LowPitch;
	@FXML Button Swing;
	@FXML Button Spin;
	@FXML Button SwingClash;
	
	public File HumWav;
	
	private WavPitcher pitcher;
	
	
	
	public SimpleEditor(){
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/SimpleEditor.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();

		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}
		pitcher= new WavPitcher();
		HumWav = new File("./bin/hum.wav");
	}
	
	@FXML
	public void LoadHum() {
		WavPitcher pitch = new WavPitcher();
		pitch.playFile(HumWav, 1.0);
	}
	
	@FXML
	public void LoadClash() {
		pitcher.playFile(HumWav, 0.5);
	}
	
	@FXML public void PlaySwing() {
		
		
	}
	
	@FXML public void PlaySpin() {
		
	}
	
	@FXML public void PlayClash() {
		
	}
	
	

}
