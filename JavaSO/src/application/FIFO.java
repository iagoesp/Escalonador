package application;

import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class FIFO {
	private ArrayList<Processo> listaProcessos;
	private ArrayList<Processo> finalizados;
	
	private ArrayList<Processo> main;
	private ArrayList<String> linha;
	
	private int quantum;
	private int sobrecarga;
	
	public FIFO(ArrayList<Processo> lista, int q, int s) {
		this.listaProcessos = new ArrayList<Processo>();
		this.listaProcessos = lista;
		this.quantum = q;
		this.sobrecarga = s;
		this.finalizados = new ArrayList<Processo>();
		this.main = new ArrayList<Processo>();
		this.linha = new ArrayList<String>();
	}
	public ArrayList<Processo> getLista(){
		return this.listaProcessos;
	}
		
	int buscarProcesso(){
        int index = 0;
        for(int i = 1; i < main.size(); i++) {
        	System.out.println(main.size() + ", " + i);
            if(main.get(i).getCheg() < main.get(index).getCheg()) {
            	System.out.println(i);
                index = i;
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
		while(finalizados.size() < quant){
			inserir(tempo);
			if(main.isEmpty()==false){
				int proximoProcesso = buscarProcesso();

				Processo p = main.get(proximoProcesso);
				main.remove(0 + proximoProcesso);

				int runtime = p.getExec(); //N�o Preemptivo.

				for(int i = 0; i < listaProcessos.size(); i++){
					if(listaProcessos.get(i).getCheg() <= tempo + runtime){
						main.add(listaProcessos.get(i));
						listaProcessos.remove(0 + (i--));
					}
				}
				int tempoRestante = p.getExec();
				p.setExec(0);
		
				p.inserirExecucao(tempo, tempo + tempoRestante - 1);
				finalizados.add(p);

				tempo += tempoRestante - 1;
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
    	Collections.sort(finalizados);
	}
}