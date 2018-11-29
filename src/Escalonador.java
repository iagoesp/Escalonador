import java.io.IOException;
import java.util.*;
import java.util.AbstractMap.SimpleEntry;

public class Escalonador{
	private ArrayList<Processo> listaProcessos;
	private ArrayList<Processo> finalizados;
	
	private ArrayList<Processo> main;
	private ArrayList<String> linha;
	
	private String processo;
	private int quantum;
	private int sobrecarga;
	
	private int count = 0;
	
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
		int index = 0;
		for(int i = 1; i < main.size(); i++) {
			if(p=="FIFO")
				if(main.get(i).getCheg() < main.get(index).getCheg()) {
					index = i;
				}
			else if(p=="SJF")
				if(main.get(i).getExec() < main.get(index).getExec()) {
					index = i;
				}
			else if(p=="EDF")
				if(main.get(i).getCheg() + main.get(i).getDead() < 
						main.get(index).getCheg() + main.get(index).getDead()) {
					index = i;
				}
		}
		return index;
	}
	
	public void b(int t) {
		System.out.println("b " + t + " " + listaProcessos.size());
		for(int i = 0; i < listaProcessos.size(); i++)
			if(listaProcessos.get(i).getCheg() <= t){
				main.add(listaProcessos.get(i));
				listaProcessos.remove((listaProcessos.get(0 + i--)));
			}
	}
	
	public void a() throws IOException {
		int quant = listaProcessos.size();
		int tempo = 0;
		System.out.println(processo);
		while(finalizados.size() < quant){
			b(tempo);
			if(main.isEmpty()==false){
				int proximoProcesso = buscarProcesso(this.processo);

				Processo p = main.get(proximoProcesso);
				main.remove(0 + proximoProcesso);

				int runtime = 0;
				if(processo.equals("FIFO")==true || processo.equals("SJF")==true)
					runtime = p.getExec(); //Não Preemptivo.
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
				
		System.out.println("chegou aqui");

		double contadorTempos = 0;
		for(Processo p : finalizados) {
			contadorTempos += p.getExec() - p.getCheg();
			String imprimir = p.Gantt();
			linha.add(imprimir);
		}
		Exportar exportar = new Exportar(linha);
		for(int i =0; i < linha.size(); i++)
			System.out.println(linha.get(i));
		exportar.escrever();
	}

	
}