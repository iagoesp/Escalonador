import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;
import java.awt.Insets;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.SoftBevelBorder;
import javax.swing.JButton;
import javax.swing.border.TitledBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JRadioButton;
import org.eclipse.wb.swing.FocusTraversalOnArray;
import java.awt.Component;
import javax.swing.border.MatteBorder;
import javax.swing.border.EtchedBorder;

public class ProcessoFrame implements CaretListener{

	private JFrame frame;
	private JTextField quantProcTF;
	private JTextField sobrecargaTF;
	private JTextField quantumTF;
	private int quantProc;
	private int sobrecarga;
	private int quantum;
	private Processo jpanels[];
	private String tipo;
	
	private JPanel panel_1;
	private JPanel panel_2;
	private JRadioButton fifoRB;
	private JRadioButton sjfRB;
	private JRadioButton roundRRB;
	private JRadioButton edfRB;
	private JButton criarLB;
	private JButton okBT;
	private boolean procB = false, sobreB = false, quantB = false, algB = false;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProcessoFrame window = new ProcessoFrame();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ProcessoFrame() {	
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(10, 10, 1350, 350);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 850, 10, 0};
		gridBagLayout.rowHeights = new int[]{20, 40, -1, 40, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 1.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 10.0, 0.0, Double.MIN_VALUE};
		frame.getContentPane().setLayout(gridBagLayout);
		
		JPanel panel = new JPanel();
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, null, null, null, null));
		
		JLabel lblQuantProcessos = new JLabel("Quantidade de Processos");
		panel.add(lblQuantProcessos);
		
		quantProcTF = new JTextField();
		quantProcTF.setText("2");
		panel.add(quantProcTF);
		quantProcTF.setColumns(10);
		
		JLabel lblSobrecarga = new JLabel("Sobrecarga");
		panel.add(lblSobrecarga);
		
		sobrecargaTF = new JTextField();
		sobrecargaTF.setText("1");
		panel.add(sobrecargaTF);
		sobrecargaTF.setColumns(10);
		
		JLabel lblQuantum = new JLabel("Quantum");
		panel.add(lblQuantum);
		
		quantumTF = new JTextField();
		quantumTF.setText("2");
		panel.add(quantumTF);
		quantumTF.setColumns(10);
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.insets = new Insets(0, 0, 5, 5);
		gbc_panel.fill = GridBagConstraints.HORIZONTAL;
		gbc_panel.gridx = 1;
		gbc_panel.gridy = 1;
		frame.getContentPane().add(panel, gbc_panel);
		
		quantProcTF.addCaretListener(this);
		sobrecargaTF.addCaretListener(this);
		quantumTF.addCaretListener(this);	
				
		panel_1 = new JPanel();
		panel_1.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		GridBagConstraints gbc_panel_1 = new GridBagConstraints();
		gbc_panel_1.insets = new Insets(0, 0, 5, 5);
		gbc_panel_1.fill = GridBagConstraints.BOTH;
		gbc_panel_1.gridx = 1;
		gbc_panel_1.gridy = 2;
		frame.getContentPane().add(panel_1, gbc_panel_1);
				
		criarLB = new JButton("Criar");
		criarLB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				if(criarLB.isEnabled()==true) {
					ArrayList<Processo> lista = new ArrayList<Processo>();
					for(int i = 0; i < jpanels.length; i++) {
						int c = jpanels[i].getCheg();
						int e = jpanels[i].getExec();
						int d = jpanels[i].getDead();
						int p = jpanels[i].getPrior();
						String id = jpanels[i].getID();
						Processo p1 = new Processo(id, c, e, d, p);
						lista.add(p1);
					}
					
					Escalonador esc = new Escalonador(lista, tipo, quantum, sobrecarga);
					try {
						esc.a();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		criarLB.setEnabled(false);
		
		okBT = new JButton("Definir");
		okBT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				String aux = quantProcTF.getText();
				int tam = aux.length();
				quantProc = 0;
				sobrecarga = Integer.parseInt(sobrecargaTF.getText());
				quantum = Integer.parseInt(quantumTF.getText());
				panel_1.removeAll();
				for(int i = 0; i < tam; i++)
					quantProc += (int) ((aux.charAt(tam-i-1)-48)*Math.pow(10,i));
				if(quantProc>0) {
					jpanels = new Processo[quantProc];
					tipo = defineTipo();
					GridBagConstraints gbc_panel[] = new GridBagConstraints[quantProc];
					panel_1.setPreferredSize(new Dimension(899, 100*quantProc));
					panel_1.setSize(new Dimension(400, 100*quantProc));
					for(int i = 0; i <quantProc; i++) {
						jpanels[i] = new Processo();
						jpanels[i].setQ(quantum);
						jpanels[i].setS(sobrecarga);
						jpanels[i].setBorder(new TitledBorder(null, "Processo " + (i + 1), TitledBorder.LEADING, TitledBorder.TOP, null, null));
						gbc_panel[i] = new GridBagConstraints();
						gbc_panel[i].insets = new Insets(0, 0, 0, 5);
						gbc_panel[i].fill = GridBagConstraints.HORIZONTAL;
						gbc_panel[i].gridx = 1;
						gbc_panel[i].gridy = 3 + i;
						
						jpanels[i].lblTempoDeChegada = new JLabel("Tempo de Chegada");
						jpanels[i].add(jpanels[i].lblTempoDeChegada);
						
						jpanels[i].tChegTF = new JTextField();
						jpanels[i].add(jpanels[i].tChegTF);
						jpanels[i].tChegTF.setColumns(10);
						
						jpanels[i].lblTempoDeExecuo = new JLabel("Tempo de Execu\u00E7\u00E3o");
						jpanels[i].add(jpanels[i].lblTempoDeExecuo);
						
						jpanels[i].tExecTF = new JTextField();
						jpanels[i].add(jpanels[i].tExecTF);
						jpanels[i].tExecTF.setColumns(10);
						
						jpanels[i].lblDeadline = new JLabel("Deadline");
						jpanels[i].add(jpanels[i].lblDeadline);
						
						jpanels[i].tDeadTF = new JTextField();
						jpanels[i].add(jpanels[i].tDeadTF);
						jpanels[i].tDeadTF.setColumns(10);
						
						jpanels[i].lblPrioridade = new JLabel("Prioridade");
						jpanels[i].add(jpanels[i].lblPrioridade);
						
						jpanels[i].tPriorTF = new JTextField();
						jpanels[i].add(jpanels[i].tPriorTF);
						jpanels[i].tPriorTF.setColumns(10);
						
						jpanels[i].tChegTF.setText("");
						jpanels[i].tExecTF.setText("");
						jpanels[i].tDeadTF.setText("");
						jpanels[i].tPriorTF.setText("");
						jpanels[i].addCaret();
						
						jpanels[i].okProcBT = new JButton("Definir");
						jpanels[i].okProcBT.setEnabled(false);
						
						final int j = i;
						
						jpanels[i].okProcBT.addMouseListener(new MouseAdapter() {
							boolean clicked = false;
							@Override
							public void mouseClicked(MouseEvent arg0) {
								if(jpanels[j].okProcBT.isEnabled()==true) {
									clicked = true;
									jpanels[j].tChegTF.setEditable(false);
									jpanels[j].tDeadTF.setEditable(false);
									jpanels[j].tExecTF.setEditable(false);
									jpanels[j].tPriorTF.setEditable(false);
									jpanels[j].setPrior(Integer.parseInt(jpanels[j].tPriorTF.getText()));
									jpanels[j].setCheg(Integer.parseInt(jpanels[j].tChegTF.getText()));
									jpanels[j].setDead(Integer.parseInt(jpanels[j].tDeadTF.getText()));
									jpanels[j].setExec(Integer.parseInt(jpanels[j].tExecTF.getText()));
									int s = j + 1;
									jpanels[j].setID(Integer.toString((s)));
								}
							}
							public void mouseExited(MouseEvent arg0) {
								if(clicked) {
								    jpanels[j].setReady(true);
							    	boolean ok = true;
								    for(int k = 0; k < jpanels.length; k++) {
								    	if(jpanels[k].getReady()==false) {
								    		ok = false;
								    		break;
								    	}
								    }
								    if(ok){
							    		criarLB.setEnabled(true);
							    	}	    		
								    try { Thread.sleep (500);
								    	panel_1.remove(jpanels[j]);
								    }catch (InterruptedException ex) {}
							        frame.revalidate();
									frame.repaint();
									
//									for(int i = 0; i < jpanels.length;i++) {
//										System.out.println(jpanels[i].getCheg() +", " + jpanels[i].getExec() +", " +  jpanels[i].getDead() +", " +  jpanels[i].getPrior());
//									}
								}
								clicked = false;
							}
						});
						jpanels[i].add(jpanels[i].okProcBT);
						
						panel_1.add(jpanels[i], gbc_panel[i]);
						panel_1.revalidate();
						panel_1.repaint();
						frame.revalidate();
						frame.repaint();
						
					}
				}						
			}

			private String defineTipo() {
				if(fifoRB.isSelected())
					return "FIFO";
				else if(roundRRB.isSelected())
					return "Round Robin";
				else if(sjfRB.isSelected())
					return "SJF";
				return "EDF";
			}
		});
		
		panel_2 = new JPanel();
		panel_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(0, 0, 0)));
		panel_2.setToolTipText("");
		panel.add(panel_2);
		
		fifoRB = new JRadioButton("FIFO");
		fifoRB.setSelected(true);
		panel_2.add(fifoRB);
		
		sjfRB = new JRadioButton("SJF");
		panel_2.add(sjfRB);
		
		roundRRB = new JRadioButton("Round Robin");
		panel_2.add(roundRRB);
		
		edfRB = new JRadioButton("EDF");		
		panel_2.add(edfRB);
		panel_2.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{fifoRB, sjfRB, roundRRB, edfRB}));
		
		fifoRB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeState(sjfRB, roundRRB, edfRB, fifoRB);
			}
		});
		sjfRB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeState(fifoRB, roundRRB, edfRB, sjfRB);
			}
		});
		roundRRB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeState(fifoRB, sjfRB, edfRB, roundRRB);
			}
		});
		edfRB.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				changeState(fifoRB, sjfRB, roundRRB, edfRB);
			}
		});
		
		panel.add(okBT);
		
		
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 1;
		gbc_btnNewButton.gridy = 3;
		frame.getContentPane().add(criarLB, gbc_btnNewButton);
	
	}

	@Override
	public void caretUpdate(CaretEvent arg0) {
		String proc = quantProcTF.getText();
		String sobre = sobrecargaTF.getText();
		String quant = quantumTF.getText(); 
		procB = false; sobreB = false; quantB = false;
		if(proc.isEmpty()==false && !proc.equals("0"))
			procB = true;
		if(sobre.isEmpty()==false)
			sobreB = true;
		if(quant.isEmpty()==false && quant != "0")
			quantB = true;
		if(fifoRB.isSelected()==true || sjfRB.isSelected()==true || roundRRB.isSelected()==true || edfRB.isSelected()==true)
			algB = true;
		else
			algB = false;
		if(procB && sobreB && quantB && algB) {
			okBT.setEnabled(true);
		}
		else
			okBT.setEnabled(false);
		criarLB.setEnabled(false);
	}
	public void changeState(JRadioButton a, JRadioButton b, JRadioButton c, JRadioButton d) {
		a.setSelected(false);
		c.setSelected(false);
		b.setSelected(false);
		d.setSelected(true);
		algB = true;
		caretUpdate(null);
	}
}
