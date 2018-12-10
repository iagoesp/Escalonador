package application;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.embed.swing.JFXPanel;
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

import javax.swing.JDialog;
import javax.swing.JFrame;

import application.GChart.ExtraData;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

public class Executor extends Application {
    public static final CountDownLatch latch = new CountDownLatch(1);
    public static Executor startUpTest = null;

    final NumberAxis xAxis = new NumberAxis();
    final CategoryAxis yAxis = new CategoryAxis();
    
    private String counter = null;
	private XYChart.Data<Integer, String> f;
	private XYChart.Series serie = new XYChart.Series();
	private ArrayList<Processo> processo = new ArrayList<Processo>();
    
    private int j=0;
    private int tempoDeProcessamento=0;
    private int segundos = 1;
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

    /**
     * @wbp.parser.entryPoint
     */
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
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(segundos), ev -> {
           for(Processo p : getProcesso()) {
        	   //System.out.println("Entrou aqui");
        	   if(j<p.getGanttChar().length) {
        		   String status = null;      
	        	   if(p.getGanttChar()[j]=='*') {
	        		   status = "status-blank";
	        	   }
	        	   else if(p.getGanttChar()[j]=='#') {
					   status = "status-yellow";
					   tempoDeProcessamento++;
	    		   }
	        	   else if(p.getGanttChar()[j]=='-') {
				   		status = "status-red";
				   		tempoDeProcessamento++;
				   }
	        	   else if(p.getGanttChar()[j]=='!') {
					   status = "status-espera";
					   tempoDeProcessamento++;
				   }
	        	   System.out.println(tempoDeProcessamento);
	        	   System.out.println("Processo " + p.getID() + ": " + j + ", " + status);
	        	   e = new XYChart.Data(j, p.getID(), new ExtraData(1, status));                	   
				   list.add(e);
				   if(!list.isEmpty() && e.equals(f)==false) {
		        	   serie.getData().add(e);
		        	   f = e;
		           }
        	   }
        	   else if(j==p.getGanttChar().length && p == getProcesso().get(getProcesso().size()-1)) {
        		   float turnAround = (tempoDeProcessamento);
        		   turnAround = (turnAround)/(float)(getProcesso().size());
        		   System.out.println(j + " " + turnAround + " " + getProcesso().size());
        		   String text = "TurnAround = " + Float.toString(turnAround);
        		   TurnAround ta = new TurnAround(null, counter, text);
        		   
        		   System.out.println("Dialog");
        		   JDialog tAround = new JDialog();
        		   tAround.setLocationRelativeTo(null);
        		   tAround.setBounds(370, 500, 200, 200);
        		   Dimension d = new Dimension();
        		   //d.setSize(200, 200);
        		   tAround.setMinimumSize(d);
        		   tAround.setVisible(true);
        	   }
			   //serie.getData().add(list.get(list.size()-1));
		   }
           chart.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
           
           setJ();
           showJ();
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        chart.getData().addAll(serie);           

       Scene scene  = new Scene(chart,620, 380);
       stage.setX(10);
       stage.setY(10);
       stage.setScene(scene);
       stage.show();
    }
    public int getJ() {
    	return j;
    }
    public void setJ() {
    	this.j+=1;
    }
    public void showJ() {
    	System.out.println(getJ());
    }
    private ArrayList<Processo> getProcesso() {
		return processo;
	}

	public static void main(String[] args) {
        Application.launch(args);
    }
}