import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Exportar {
	ArrayList<String> listaTexto;
	Processo[] listaProcesso;
	String tipo;
	File file;
	public Exportar(ArrayList<String> t) {
		this.listaTexto = t;
		// memória virtual, entrada e saída, i node, 324 / 4 - deadlock  / substituição de página
	}
	public Exportar(Processo[] j, String p) {
		this.listaProcesso = j;
		this.tipo = p;
	}
	public void escrever() throws IOException {
		file = new File("C:\\Users\\iagop\\Desktop\\input00.txt");
		if(file.exists()==true) {
			int i = 01;
			while(file.exists()) {
				file.renameTo(new File("C:\\Users\\iagop\\Desktop\\input" + i + ".txt"));
				i++;
			}
		}
		
		//file.renameTo(new File("/Desktop"));
		String newLine = System.getProperty("line.separator");

        // Se o arquivo nao existir, ele gera
        if (!file.exists()) {
            file.createNewFile();
        }
        FileWriter fw = new FileWriter(file.getAbsoluteFile());
        BufferedWriter bw = new BufferedWriter(fw);

        for(int i = 0; i< listaTexto.size(); i++){
        	System.out.println(listaTexto.get(i));
        	bw.write(listaTexto.get(i)+ newLine);
        }
        bw.close();
        
        /*else if(escrever.get(0) instanceof Processo) { 
	        bw.write(tipo + newLine + lista.length + " " + lista.get(0).getS() + " " + lista.get(0).getQ() + " " + newLine);
	        for(int i = 0; i< lista.size(); i++){
	        	bw.write(i + " " + lista.get(i).getCheg() + " " + lista.get(i).getExec() + " " + lista.get(i).getDead() + " " + lista.get(i).getPrior()+ newLine);
	        }
	        bw.close();
        }*/
    }
}
