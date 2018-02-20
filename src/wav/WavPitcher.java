package wav;

import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Handler;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.TargetDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import be.tarsos.dsp.AudioDispatcher;
import be.tarsos.dsp.AudioEvent;
import be.tarsos.dsp.AudioProcessor;
import be.tarsos.dsp.GainProcessor;
import be.tarsos.dsp.MultichannelToMono;
import be.tarsos.dsp.WaveformSimilarityBasedOverlapAdd;
import be.tarsos.dsp.WaveformSimilarityBasedOverlapAdd.Parameters;
import be.tarsos.dsp.io.jvm.AudioDispatcherFactory;
import be.tarsos.dsp.io.jvm.AudioPlayer;
import be.tarsos.dsp.io.jvm.JVMAudioInputStream;
import be.tarsos.dsp.resample.RateTransposer;
import math.BMath;

public class WavPitcher {
		
	private AudioDispatcher dispatcher;
	private WaveformSimilarityBasedOverlapAdd wsola;
	private GainProcessor gain;
	private AudioPlayer audioPlayer;
	private RateTransposer rateTransposer;
	private double sampleRate;
	private boolean loop;
	private boolean stop;

	public WavPitcher(){
		gain = new GainProcessor(1.0);
		stop = true;
		loop = false;
	}
	
	public static double centToFactor(double cents){
		return 1 / Math.pow(Math.E,cents*Math.log(2)/1200/Math.log(Math.E)); 
	}
	
	public static double factorToCents(double factor){
		return 1200 * Math.log(1/factor) / Math.log(2); 
	}
	
	public void SetGain(double gainValue) {
		gain.setGain(BMath.clamp(gainValue,0.0f,4.0f));
	}
	
	public void SetLoop(boolean doLoop) {
		loop= doLoop;
	}
	
	public void Stop() {
		stop = true;
		if(dispatcher != null){
		dispatcher.stop();
		}
	}
	
	public void playFile(final String inputFilePath,double pitchFactor){
		playFile(new File(inputFilePath),pitchFactor);
	}
	
	public boolean isPlaying() {
		return !stop;
	}
	
	
	public void playFile(final File inputFile,double pitchFactor){
		
		stop = false;
		if(dispatcher != null){
			loop = false;
			dispatcher.stop();
		}
		AudioFormat format;
		try {
			if(inputFile == null) {
				throw new UnsupportedAudioFileException("no media was selected");
			}
			
			format = AudioSystem.getAudioFileFormat(inputFile).getFormat();
			
			rateTransposer = new RateTransposer(pitchFactor);
			audioPlayer = new AudioPlayer(format);
			sampleRate = format.getSampleRate();
			
			
			wsola = new WaveformSimilarityBasedOverlapAdd(Parameters.musicDefaults(pitchFactor, sampleRate));
			 
			if(format.getChannels() != 1){
				dispatcher = AudioDispatcherFactory.fromFile(inputFile,wsola.getInputBufferSize() * format.getChannels(),wsola.getOverlap() * format.getChannels());
				dispatcher.addAudioProcessor(new MultichannelToMono(format.getChannels(),true));
			}else{
				dispatcher = AudioDispatcherFactory.fromFile(inputFile,wsola.getInputBufferSize(),wsola.getOverlap());
			}
			
			 
			wsola.setDispatcher(dispatcher);
			dispatcher.addAudioProcessor(wsola);
			dispatcher.addAudioProcessor(rateTransposer);
			dispatcher.addAudioProcessor(gain);
			dispatcher.addAudioProcessor(audioPlayer);
			dispatcher.addAudioProcessor(new AudioProcessor() {
				
				@Override
				public void processingFinished() {
					if(loop){
					dispatcher =null;
					playFile(inputFile,pitchFactor);
					}
					else {
						stop = true;
					}
					
				}
				
				
				@Override
				public boolean process(AudioEvent audioEvent) {
					if(stop) {
					
					loop = false;
					}
					return !stop;
				}
			});

			Thread t = new Thread(dispatcher);
			t.start();
		} catch (UnsupportedAudioFileException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
