package ui;

import java.io.File;
import java.io.IOException;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import math.BMath;
import wav.WavPitcher;

public class WavUI extends AnchorPane {

	
	private SimpleStringProperty humFilePath  = new SimpleStringProperty("./bin/hum.wav"); //path to hum sound file
	private SimpleStringProperty clashFilePath = new SimpleStringProperty("./bin/clash.wav"); //path to clash sound file
	
	private SimpleDoubleProperty firstPitch = new SimpleDoubleProperty(50); //pitch for first hum
	private SimpleDoubleProperty secondPitch = new SimpleDoubleProperty(50); //pitch second hum

	//FXML Controls
	@FXML TextField tfHumPath;
	@FXML TextField tfClashPath;
	@FXML Slider slFirst;
	@FXML Slider slSecond;
	
	private WavPitcher pitcherHum = new WavPitcher();;
	private WavPitcher pitcherClash = new WavPitcher();;
	private WavPitcher pitcherFirst = new WavPitcher();;
	private WavPitcher pitcherSecond = new WavPitcher();;
	
	
	public WavUI() {
		FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/WavUI.fxml"));

		fxmlLoader.setRoot(this);
		fxmlLoader.setController(this);

		try {
			fxmlLoader.load();

		} catch (IOException exception) {
			throw new RuntimeException(exception);
		}

		tfHumPath.textProperty().bindBidirectional(humFilePath);
		tfClashPath.textProperty().bindBidirectional(clashFilePath);
		slFirst.valueProperty().bindBidirectional(firstPitch);	
		slSecond.valueProperty().bindBidirectional(secondPitch);
		
	}
	
	
	@FXML void bLoadHum() {
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Hum Wav File");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Audio Files", "*.wav"));
		 File selectedFile = fileChooser.showOpenDialog(null);
		 if (selectedFile != null) {
			 humFilePath.set(selectedFile.getAbsolutePath());
		 }
	}
	
	@FXML void bLoadClash() {
		FileChooser fileChooser = new FileChooser();
		 fileChooser.setTitle("Open Clash Wav File");
		 fileChooser.getExtensionFilters().addAll(
		         new ExtensionFilter("Audio Files", "*.wav"));
		 File selectedFile = fileChooser.showOpenDialog(null);
		 if (selectedFile != null) {
			 humFilePath.set(selectedFile.getAbsolutePath());
		 }
	}
	
	
	@FXML void bPlayHum() {
		stopAllAudio();
		pitcherHum.SetGain(1);
		pitcherHum.playFile(humFilePath.get(), 1.0);
	}
	
	@FXML void bPlayClash() {
		stopAllAudio();
		pitcherClash.SetGain(1);
		pitcherClash.playFile(humFilePath.get(), 1.0);
	}
	
	@FXML void bPlayFirst() {
		stopAllAudio();
		pitcherFirst.SetGain(1);
		pitcherFirst.playFile(humFilePath.get(), centToFact(firstPitch.get(),0.1,4.0));
	}
	
	@FXML void bPlaySecond() {
		stopAllAudio();
		pitcherSecond.SetGain(1);
		pitcherSecond.playFile(humFilePath.get(), centToFact(secondPitch.get(),0.4,2.5));
	}
	
	@FXML void bPlaySwing() {
		stopAllAudio();
	}
	
	@FXML void bPlaySwingClash() {
		stopAllAudio();
	}
	
	@FXML void bPlaySpin() {
		stopAllAudio();
	}
	
	private void stopAllAudio() {
		pitcherHum.SetLoop(false);
		pitcherHum.Stop();
		pitcherClash.SetLoop(false);
		pitcherClash.Stop();
		pitcherFirst.SetLoop(false);
		pitcherFirst.Stop();
		pitcherSecond.SetLoop(false);
		pitcherSecond.Stop();
	}
	
	public double centToFact(double cent,double min, double max){
		double val;
		if(cent >= 50) {
			val = (((cent*2.0-100.0)*(max-1.0))+100.0)/(100.0);
		}
		else {
			val =(((cent*2.0) *(1.0-min))+(min*100)) /100.0;
		}
		System.out.println(val);
		return val;
	}
	
}               
