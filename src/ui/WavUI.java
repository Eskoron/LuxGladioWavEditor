package ui;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
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
import javafx.util.Duration;
import math.BMath;
import wav.WavPitcher;

public class WavUI extends AnchorPane {

	
	private SimpleStringProperty humFilePath  = new SimpleStringProperty("./bin/hum.wav"); //path to hum sound file
	private SimpleStringProperty clashFilePath = new SimpleStringProperty("./bin/clash.wav"); //path to clash sound file
	
	private SimpleDoubleProperty firstPitch = new SimpleDoubleProperty(60); //pitch for first hum
	private SimpleDoubleProperty secondPitch = new SimpleDoubleProperty(46); //pitch second hum
	private SimpleDoubleProperty humPitch = new SimpleDoubleProperty(50);
	
	//FXML Controls
	@FXML TextField tfHumPath;
	@FXML TextField tfClashPath;
	@FXML Slider slFirst;
	@FXML Slider slSecond;
	
	private WavPitcher pitcherHum = new WavPitcher();;
	private WavPitcher pitcherClash = new WavPitcher();;
	private WavPitcher pitcherFirst = new WavPitcher();;
	private WavPitcher pitcherSecond = new WavPitcher();;
	
	private Timeline timeline;
	private AnimationTimer timer;
	private long startedTime;
	
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
		pitcherHum.playFile(humFilePath.get(), centToFact(humPitch.get(),0.1,4.0));
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
		
		pitcherHum.playFile(humFilePath.get(), centToFact(secondPitch.get(),0.1,4.0));
		pitcherHum.SetLoop(true);
		pitcherFirst.playFile(humFilePath.get(), centToFact(firstPitch.get(),0.1,4.0));
		pitcherFirst.SetGain(0.0);
		pitcherFirst.SetLoop(true);
		//pitcherSecond.playFile(humFilePath.get(), centToFact(firstPitch.get(),0.1,4.0));
		//pitcherSecond.SetGain(0.0);
		
		double swingTime = 1800;
		
			timeline = new Timeline(
			new KeyFrame(Duration.seconds(1), e -> {
		    	timer.start();
		    	startedTime = System.nanoTime();
		    }),
			    new KeyFrame(Duration.millis(1000+swingTime), e -> {
			    	timer.stop();
			    	pitcherFirst.SetGain(0.0);
			    	pitcherHum.SetGain(1.0);
			    }),
			    new KeyFrame(Duration.millis(swingTime+1000), e -> {
			    	timeline.playFromStart();
			    })
			);
			timeline.play();
			
			timer = new AnimationTimer() {
	            @Override
	            public void handle(long l) {
	            	long time = TimeUnit.MILLISECONDS.convert(System.nanoTime()-startedTime,TimeUnit.NANOSECONDS);
	            	//double f = sineWave(time,(long) swingTime);
	            	
	            	if(time >= swingTime/2.0) {
	            		time = (long) (swingTime-time);
	            	}
	            	double f = expoinout(time, 0.0, 1.0, (long)(swingTime/2.0));
	            	
	            	System.out.println(time);
	            	System.out.println(f);
	            	pitcherHum.SetGain(1-(f/2.0));
	            	pitcherFirst.SetGain(f*f*f*f*2);
	            }
	 
	        };
	        
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
		pitcherHum.SetGain(1.0);
		pitcherClash.SetLoop(false);
		pitcherClash.Stop();
		pitcherClash.SetGain(1.0);
		pitcherFirst.SetLoop(false);
		pitcherFirst.Stop();
		pitcherFirst.SetGain(1.0f);
		pitcherSecond.SetLoop(false);
		pitcherSecond.Stop();
		pitcherSecond.SetGain(1.0f);
		if (timeline != null) {
            timeline.stop();
        }
		if(timer!=null) {
			timer.stop();
		}
	}
	
	public double sineWave(long curTime,long intervalltime) {
		double k = (double)curTime /  (double)intervalltime;
		return Math.abs((Math.sin(Math.PI*k)));
		
	}
	
	public double expoinout (long time,double b,double c, long duration) {
		
		double t = time / (double)( duration/2.0);
		if (t < 1) return c/2 * Math.pow( 2, 10 * (t - 1) ) + b;
		t--;
		return c/2 * ( -Math.pow( 2, -10 * t) + 2 ) + b;
	}
	
	public double centToFact(double ce,double min, double max){
		double cent = 100-ce;
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
