package application;

	import javafx.animation.AnimationTimer;
	import javafx.application.Application;
	import javafx.scene.Scene;
	import javafx.scene.chart.LineChart;
	import javafx.scene.chart.NumberAxis;
	import javafx.scene.chart.XYChart;
	import javafx.stage.Stage;
import math.Quaternion;
import math.Vector3;
import serial.EDataType;
import serial.SerialDataManager;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;
	import java.util.concurrent.ExecutorService;
	import java.util.concurrent.Executors;
	import java.util.concurrent.ThreadFactory;


	public class LineChartTest extends Application {

	    private static final int MAX_DATA_POINTS = 250;
	    private XYChart.Series<Number, Number> sX = new XYChart.Series<>();
	    private XYChart.Series<Number, Number> sY = new XYChart.Series<>();
	    private XYChart.Series<Number, Number> sZ = new XYChart.Series<>();
	    private XYChart.Series<Number, Number> sW = new XYChart.Series<>();
	    private ExecutorService executor;
	    private int seriesPoint;
	    
	    private SerialDataManager serial;

	    private NumberAxis xAxis;

	    private void init(Stage primaryStage) {
	    	
	    	serial = new SerialDataManager(200);

	        xAxis = new NumberAxis(0, MAX_DATA_POINTS, MAX_DATA_POINTS / 10);
	        xAxis.setForceZeroInRange(false);
	        xAxis.setAutoRanging(false);
	        xAxis.setTickLabelsVisible(true);
	        xAxis.setTickMarkVisible(false);
	        xAxis.setMinorTickVisible(false);

	        NumberAxis yAxis = new NumberAxis();

	        // Create a LineChart
	        final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis) {
	            // Override to remove symbols on each data point
	            @Override
	            protected void dataItemAdded(Series<Number, Number> series, int itemIndex, Data<Number, Number> item) {
	            }
	        };

	        lineChart.setAnimated(false);
	        lineChart.setTitle("Animated Line Chart");
	        lineChart.setHorizontalGridLinesVisible(true);

	        // Set Name for Series
	        sX.setName("Euler X");
	        sY.setName("Euler Y");
	        sZ.setName("Euler Z");
	        sW.setName("Euler W");
	        // Add Chart Series
	        lineChart.getData().addAll(sX);
	        lineChart.getData().addAll(sY);
	        lineChart.getData().addAll(sZ);
	        lineChart.getData().addAll(sW);
	        seriesPoint = 0;
	        
	        primaryStage.setScene(new Scene(lineChart));
	        System.out.println("starting");
	       
	        serial.startRecording(new EDataType[]{EDataType.WorldAcceleration});
	    }


	    @Override
	    public void start(Stage stage) {
	        stage.setTitle("Animated Line Chart Sample");
	        init(stage);
	        stage.show();


	        executor = Executors.newCachedThreadPool(new ThreadFactory() {
	            @Override
	            public Thread newThread(Runnable r) {
	                Thread thread = new Thread(r);
	                thread.setDaemon(true);
	                return thread;
	            }
	        });

	        AddToQueue addToQueue = new AddToQueue();
	        executor.execute(addToQueue);
	        //-- Prepare Timeline
	        
	        
	        
	        prepareTimeline();
	    }

	    private class AddToQueue implements Runnable {
	        public void run() {
	            try {
	                // add a item of random data to queue

	                Thread.sleep(500);
	                executor.execute(this);
	            } catch (InterruptedException ex) {
	                ex.printStackTrace();
	            }
	        }
	    }

	    //-- Timeline gets called in the JavaFX Main thread
	    private void prepareTimeline() {
	        // Every frame to take any data from queue and add to chart
	        new AnimationTimer() {
	            @Override
	            public void handle(long now) {
	            	updateDataToSeries();
	            }
	        }.start();
	    }

	    private void updateDataToSeries() {
	        List<Vector3> euler = serial.getWorld(); 
	        for (Vector3 el : euler) {
	        	
	        	sX.getData().add(new XYChart.Data(seriesPoint, (Number)el.getX()));
	        	sY.getData().add(new XYChart.Data(seriesPoint, (Number)el.getY()));
	        	sZ.getData().add(new XYChart.Data(seriesPoint, (Number)el.getZ()));
	        	//sW.getData().add(new XYChart.Data(seriesPoint, (Number)el.getW()));
	        	seriesPoint++;
			}
	        if(sX.getData().size()>MAX_DATA_POINTS+1) {
	        	sX.getData().remove(0, sX.getData().size()-MAX_DATA_POINTS);
	        	sY.getData().remove(0, sY.getData().size()-MAX_DATA_POINTS);
	        	sZ.getData().remove(0, sZ.getData().size()-MAX_DATA_POINTS);
	        	//sW.getData().remove(0, sW.getData().size()-MAX_DATA_POINTS);
	        }
	        
	        xAxis.setLowerBound(seriesPoint-MAX_DATA_POINTS);
	        xAxis.setUpperBound(seriesPoint-1);
	    }

	    public static void main(String[] args) {
	        launch(args);
	    }
	}

