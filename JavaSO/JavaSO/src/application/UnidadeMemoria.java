package application;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class UnidadeMemoria extends JPanel {
	private boolean recente = false;
	JLabel indice;
	JTextField uProcesso;
	public JLabel getIndice() {
		return indice;
	}
	public JTextField getuProcesso() {
		return uProcesso;
	}
	public void setIndice(String i) {
		this.indice.setText(i);
	}
	public void setuProcesso(String i) {
		this.uProcesso.setText(i);
	}
	public UnidadeMemoria() {
		this.indice = new JLabel();
		this.uProcesso = new JTextField();
	}

}
