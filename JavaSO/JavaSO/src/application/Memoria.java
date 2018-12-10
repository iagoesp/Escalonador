package application;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.BevelBorder;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;

public class Memoria extends JFrame {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Memoria frame = new Memoria();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Memoria() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(700, 10, 265, 420);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		getContentPane().setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridx = 2;
		gbc_lblNewLabel.gridy = 3;
		getContentPane().add(lblNewLabel, gbc_lblNewLabel);
		
		int x = 0;
		int y = 0;
		UnidadeMemoria panel[] = new UnidadeMemoria[50];
		GridBagConstraints gbc_panel[] = new GridBagConstraints[50];
		for(int i = 0; i < 50; i++) {
			panel[i] = new UnidadeMemoria();
			gbc_panel[i] = new GridBagConstraints();
			gbc_panel[i].gridx = x;
			gbc_panel[i].gridy = y;
			panel[i].setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Integer.toString(i+1), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
			getContentPane().add(panel[i], gbc_panel[i]);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[]{40, 0, 0};
			gbl_panel.rowHeights = new int[]{15, 0};
			gbl_panel.columnWeights = new double[]{0.0, 0.0, Double.MIN_VALUE};
			gbl_panel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
			//panel[i].setPreferredSize(new Dimension(899, 500));
			panel[i].setSize(new Dimension(40, 20));
			panel[i].setLayout(gbl_panel);
			y++;
			if(y==10)
				x++;
			x = x%5;
			y = y%10;
		}
		
//		int x = 0;
//		int y = 0;
//		UnidadeMemoria um[] = new UnidadeMemoria[50];
//		GridBagConstraints gbc_panel_um[] = new GridBagConstraints[50];
//		for(int i = 0; i < 50; i++) {
//			um[i] = new UnidadeMemoria();
//			um[i].setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), Integer.toString(i+1), TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
//			gbc_panel_um[i] = new GridBagConstraints();
//			gbc_panel_um[i].insets = new Insets(0, 10, 0, 10);
//			gbc_panel_um[i].fill = GridBagConstraints.BOTH;
//			gbc_panel_um[i].gridx = x;
//			gbc_panel_um[i].gridy = y;
//			panel.add(um[i], gbc_panel_um[i]);
		
	}
}
