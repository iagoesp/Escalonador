package application;

import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Escalonador {
	private ArrayList<Processo> listaProcessos;
	private ArrayList<Processo> finalizados;
	
	private ArrayList<Processo> main;
	private ArrayList<String> linha;
	
	private String processo;
	private int quantum;
	private int sobrecarga;
	
	public Escalonador(ArrayList<Processo> lista, String p, int q, int s) {
		this.listaProcessos = new ArrayList<Processo>();
		this.listaProcessos = lista;
		this.processo = p;
		this.quantum = q;
		this.sobrecarga = s;
		this.finalizados = new ArrayList<Processo>();
		this.main = new ArrayList<Processo>();
		this.linha = new ArrayList<String>();
	}
	public ArrayList<Processo> getLista(){
		return this.listaProcessos;
	}
		
	boolean FIFO(Processo a, Processo b){
		return a.getCheg() < b.getCheg();
	}

	boolean SJF(Processo a, Processo b){
		return a.getExec() < b.getExec();
	}

	boolean RoundRobin(Processo a, Processo b){
		return false;
	}

	boolean EDF(Processo a, Processo b){
		int deadLineA = a.getCheg() + a.getDead();
		int deadLineB = b.getCheg() + b.getDead();
		return deadLineA < deadLineB;
	}

	int buscarProcesso(String p){
        System.out.println("Buscar " + p);
        int index = 0;
        for(int i = 1; i < main.size(); i++) {
        	System.out.println(main.size() + ", " + i);
            if(p=="FIFO") {
                if(main.get(i).getCheg() < main.get(index).getCheg()) {
                	System.out.println(i);
                    index = i;
                }
            }
            else if(p=="SJF") {
                if(main.get(i).getExec() < main.get(index).getExec()) {
                	System.out.println(i);
                	index = i;
                }
            }
            else if(p=="EDF") {
                if(main.get(i).getCheg() + main.get(i).getDead() < 
                        main.get(index).getCheg() + main.get(index).getDead()) {
                	System.out.println(i);
                	index = i;
                }
            }
        }
        return index;
    }
	
	public void inserir(int t) {
		for(int i = 0; i < listaProcessos.size(); i++)
			if(listaProcessos.get(i).getCheg() <= t){
				main.add(listaProcessos.get(i));
				listaProcessos.remove((listaProcessos.get(0 + i--)));
			}
	}
	
	public void main() throws IOException {
		int quant = listaProcessos.size();
		int tempo = 0;
		System.out.println("Tipo " + processo);
		while(finalizados.size() < quant){
			inserir(tempo);
			if(main.isEmpty()==false){
				int proximoProcesso = buscarProcesso(this.processo);

				Processo p = main.get(proximoProcesso);
				main.remove(0 + proximoProcesso);

				int runtime = 0;
				if(processo.equals("FIFO")==true || processo.equals("SJF")==true)
					runtime = p.getExec(); //N�o Preemptivo.
				else
					runtime = (p.getExec() <= quantum) ? p.getExec() - 1 : quantum + sobrecarga - 1; //Preemptivo

				for(int i = 0; i < listaProcessos.size(); i++){
					if(listaProcessos.get(i).getCheg() <= tempo + runtime){
						main.add(listaProcessos.get(i));
						listaProcessos.remove(0 + (i--));
					}
				}
				if(p.getExec() <= quantum || processo.equals("FIFO")==true || processo.equals("SJF")==true){
					int tempoRestante = p.getExec();
					p.setExec(0);
			
					p.inserirExecucao(tempo, tempo + tempoRestante - 1);
					finalizados.add(p);

					tempo += tempoRestante - 1;
				}
				else{
					p.setExec(p.getExec() - quantum);
					
					p.inserirExecucao(tempo, tempo + quantum - 1);
					p.inserirExecucao(tempo + quantum, tempo + quantum + sobrecarga - 1);
					main.add(p);
					tempo += quantum + sobrecarga - 1;
				}
			}
			tempo++;
		}
		Collections.sort(finalizados);
		//ordenarProcessos();
				
		for(Processo p : finalizados) {
			//System.out.print(p.getID());
	
			String imprimir = p.Gantt();
			System.out.println(imprimir);
			
			//System.out.print(": (" + p.tempoEspera.getKey() + ", " + p.tempoEspera.getValue() + "), ");
			//for(AbstractMap.SimpleEntry<Integer, Integer> e : p.getPair())
			//	System.out.print("(" + e.getKey() + ", " + e.getValue() + "), ");

			linha.add(imprimir);
		}
		
		/*
		Exportar exportar = new Exportar(linha);
		exportar.escrever();
		*/

		new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(Executor.class);
            }
        }.start();
        Executor startUpTest = Executor.waitForStartUpTest();
        startUpTest.setList(finalizados);
        //startUpTest.printSomething();
	}
	public void ordenarProcessos() {
	    if(processo.equals("FIFO") || processo.equals("Round Robin")){
	    	Collections.sort(finalizados);
	    }
	    else {
	    	if(processo.equals("SJF")) {
	    		int j;
	    	    Processo key;
	    	    int i;
	    		for (j = 1; j < finalizados.size(); j++){
	    		      key = finalizados.get(j);
	    		      for (i = j - 1; (i >= 0) && (finalizados.get(i).getExec() > key.getExec()); i--){
	    		    	  finalizados.set(i + 1, finalizados.get(i));
	    		         
	    		       }
	    		      finalizados.set(i + 1, key);
	    		    }
	    	}
	    }
	}
}