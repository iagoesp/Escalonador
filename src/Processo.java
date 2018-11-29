import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.AbstractMap.SimpleEntry;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

class Processo extends JPanel  implements CaretListener, Comparable<Processo> {
	JLabel lblTempoDeChegada;
	JLabel lblTempoDeExecuo;
	JLabel lblDeadline;
	JLabel lblPrioridade;
	JTextField tChegTF;
	JTextField tExecTF;
	JTextField tDeadTF;
	JTextField tPriorTF;
	
	JButton okProcBT;
	boolean ready;

	private String id;
	private int sobrecarga;
	private int quantum;
	private int tCheg;
	private int tExec;
	private int tDead;
	private int tPrior;
	private ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> exec;
		
	public Processo() {
	}
	public Processo(String id, int tCheg, int tExec, int tDead, int tPrior) {
		super();
		this.id = id;
		this.tCheg = tCheg;
		this.tExec = tExec;
		this.tDead = tDead;
		this.tPrior = tPrior;
		this.exec = new ArrayList<AbstractMap.SimpleEntry<Integer, Integer>>();
	}
	public ArrayList<AbstractMap.SimpleEntry<Integer, Integer>> getPair(){
		return this.exec;
	}
	public String getID() {
		return this.id;
	}
	public void setID(String i) {
		this.id  = i;
	}
	public int getQ() {
		return quantum;
	}
	public void setQ(int q) {
		this.quantum = q;
	}
	public int getS() {
		return sobrecarga;
	}	
	public void setS(int s) {
		this.sobrecarga = s;
	}
	public int getCheg() {
		return this.tCheg;
	}
	public void setCheg(int tCheg) {
		this.tCheg = tCheg;
	}
	public int getExec() {
		return this.tExec;
	}
	public void setExec(int tExec) {
		this.tExec = tExec;
	}
	public int getDead() {
		return this.tDead;
	}
	public void setDead(int tDead) {
		this.tDead = tDead;
	}
	public int getPrior() {
		return this.tPrior;
	}
	public void setPrior(int tPrior) {
		this.tPrior = tPrior;
	}
	public boolean getReady() {
		return this.ready;
	}
	public void setReady(boolean r) {
		this.ready = r;
	}
	
	public void addCaret() {
		tPriorTF.addCaretListener(this);
		tDeadTF.addCaretListener(this);
		tChegTF.addCaretListener(this);
		tExecTF.addCaretListener(this);
	}
	@Override
	public void caretUpdate(CaretEvent arg0) {
		// TODO Auto-generated method stub
		int c;
		try{
			c = Integer.parseInt(tChegTF.getText());
		}
		catch(NumberFormatException f){
			c = -1;
		}
		
		int e;
		try{
			e = Integer.parseInt(tExecTF.getText());
		}
		catch(NumberFormatException f){
			e = -1;
		}
		
		int d;
		try{
			d = Integer.parseInt(tDeadTF.getText());
		}
		catch(NumberFormatException f){
			d = -1;
		}
		
		int p;
		try{
			p = Integer.parseInt(tPriorTF.getText()); 
		}
		catch(NumberFormatException f){
			p = -1;
		}
		 

		if(c >= 0 && e > 0 && d > 0 && p >= 0)
			okProcBT.setEnabled(true);
		else
			okProcBT.setEnabled(false);
	}
	public void inserirExecucao(int i, int f) {
		AbstractMap.SimpleEntry<Integer, Integer> a 
		  = new AbstractMap.SimpleEntry<>(i, f);
		exec.add(a);		
	}

	String Gantt() {
		String nome = this.getID() + ":";
		int a = this.getPair().get(this.getPair().size() - 1).getValue() + 1;
		char[] gantt = new char[a];
		boolean dentro = false;
		for(int i = 0; i<gantt.length; i++) {
			if(i >= tCheg && i <= this.getPair().get(0).getKey())
				dentro = true;
			else
				dentro = false;
			if(dentro)
				gantt[i]='!';
			else
				gantt[i]='*';
		}
		char indicador = '#';
		ArrayList<SimpleEntry<Integer, Integer>> b = this.getPair();
		for(int k = 0; k < b.size(); k++) {
			for(int m = b.get(k).getKey(); m <= b.get(k).getValue(); m++)
				gantt[m] = indicador;

			indicador = (indicador == '#') ? '-' : '#';
		}
		for(int i = 0; i < gantt.length; i++)
			nome += gantt[i];
		return nome;
	}
	
	@Override
	public int compareTo(Processo o) {
		if(this.getCheg() == o.getCheg()) {
			return o.getID().compareTo(this.getID());
		}
		if(this.getCheg() < (o.getCheg()))
			return -1;
		else if(this.getCheg() > (o.getCheg()))
			return 1;
		return 0;
	}
}					