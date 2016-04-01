package org.esiea.architecturelogiciel.tetris;

import java.awt.Color;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Accueil extends JPanel{

	private JLabel titre;
	private JButton solo;
	private JButton multi;
	private JButton stat;
	
	private static final long serialVersionUID = 1L;
	
	public Accueil() {
		
		titre = new JLabel("TETRIS", SwingConstants.CENTER);
		titre.setForeground(new Color(0,102,255));
		titre.setBounds(98, 50, 180, 50);
		titre.setFont(new Font("Arial", Font.BOLD, 20));
		
		solo = new JButton("Partie solo");
		solo.setBackground(new Color(0,102,255));
		solo.setForeground(Color.white);
		solo.setBounds(98, 130, 180, 50);
		solo.setFocusPainted(false);
		
		multi = new JButton("Partie multi");
		multi.setBackground(new Color(0,102,255));
		multi.setForeground(Color.white);
		multi.setBounds(98, 210, 180, 50);
		multi.setFocusPainted(false);
		
		stat = new JButton("Statistiques");
		stat.setBackground(new Color(0,102,255));
		stat.setForeground(Color.white);
		stat.setBounds(98, 290, 180, 50);
		stat.setFocusPainted(false);
		
		this.setLayout(null);
		this.setBackground(new Color(223,223,223));
		
		this.add(titre);
		this.add(solo);
		this.add(multi);
		this.add(stat);
		
	}
	
	public JButton getSolo() { return solo; }
	public JButton getMulti() { return multi; }
	public JButton getStat() { return stat; }
}
