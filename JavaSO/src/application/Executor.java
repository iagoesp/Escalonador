package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.CountDownLatch;

import application.GChart.ExtraData;

public class Executor extends Application {
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static Executor startUpTest = null;

    final NumberAxis xAxis = new NumberAxis();
    final CategoryAxis yAxis = new CategoryAxis();
    
	private XYChart.Data<Integer, String> f;
	private XYChart.Series serie = new XYChart.Series();
	private ArrayList<Processo> processo = new ArrayList<Processo>();
    
    int j=0;
    ArrayList<XYChart.Data> list = new ArrayList<XYChart.Data>();
    String[] machines;
//    String machine = machines[0];
    private XYChart.Data<Integer, String> e;// = new XYChart.Data<Integer, String>(2, machine, new ExtraData( 1, "status-red"));
    
    public static Executor waitForStartUpTest() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return startUpTest;
    }

    public static void setStartUpTest(Executor startUpTest0) {
        startUpTest = startUpTest0;
        latch.countDown();
    }

    public Executor() {
        setStartUpTest(this);
    }

    public void setList(ArrayList<Processo> p) {
        this.processo = p;
        String[] machines = new String[p.size()];
        for(int i = 0; i< p.size(); i++) {
        	machines[i] = p.get(i).getID();
        }
        xAxis.setLabel("");
        xAxis.setTickLabelFill(Color.CHOCOLATE);
        xAxis.setMinorTickCount(4);

        yAxis.setLabel("");
        yAxis.setTickLabelFill(Color.CHOCOLATE);
        yAxis.setTickLabelGap(10);
        yAxis.setCategories(FXCollections.<String>observableArrayList(Arrays.asList(machines)));
    }

    @Override
    public void start(Stage stage) throws Exception {

        stage.setTitle("Gantt Chart Sample");
        
        
        final GChart<Number,String> chart = new GChart<Number,String>(xAxis,yAxis);

        chart.setTitle("Machine Monitoring");
        chart.setLegendVisible(false);
        chart.setBlockHeight( 50);

       // machine = machines[0];        
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), ev -> {
           for(Processo p : getProcesso()) {
        	   //System.out.println("Entrou aqui");
        	   if(j<p.getGanttChar().length) {
        		   String status = null;        	   
	        	   if(p.getGanttChar()[j]=='*') {
	        		   status = "status-blank";
	        	   }
	        	   else if(p.getGanttChar()[j]=='#') {
					   status = "status-yellow";
	    		   }
	        	   else if(p.getGanttChar()[j]=='-') {
				   		status = "status-red";
				   }
	        	   else if(p.getGanttChar()[j]=='!') {
					   status = "status-espera";	   
				   }
	        	   System.out.println("Processo " + p.getID() + ": " + j + ", " + status);
	        	   e = new XYChart.Data(j, p.getID(), new ExtraData(1, status));                	   
				   list.add(e);
				   if(!list.isEmpty() && e.equals(f)==false) {
		        	   serie.getData().add(e);
		        	   f = e;
		           }
        	   }
			   //serie.getData().add(list.get(list.size()-1));
		   }
           chart.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

           
           j++;
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        chart.getData().addAll(serie);           

       Scene scene  = new Scene(chart,620,350);
           stage.setScene(scene);
           stage.show();    }

    private ArrayList<Processo> getProcesso() {
		return processo;
	}

	public static void main(String[] args) {
        Application.launch(args);
    }
}