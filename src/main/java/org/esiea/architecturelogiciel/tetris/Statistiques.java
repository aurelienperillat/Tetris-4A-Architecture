package org.esiea.architecturelogiciel.tetris;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Statistiques extends JPanel {
	
	private int[] topSolo, topMulti;
	private JButton retour;
	private JLabel un,deux,trois,unM,deuxM, troisM;
	private File save;
	
	private static final long serialVersionUID = 1L;
	
	public Statistiques() {
		topSolo = new int[3];
		topMulti = new int[3];
		
		try {
			save = new File("save.txt");
			save.createNewFile();
			loadFromFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		JLabel titreS = new JLabel("Meilleurs Scores solo", SwingConstants.CENTER);
		titreS.setForeground(new Color(0,102,255));
		titreS.setBounds(98, 50, 180, 30);
		titreS.setFont(new Font("Arial", Font.BOLD, 15));
		
		un = new JLabel(""+topSolo[0], SwingConstants.CENTER);
		un.setForeground(new Color(0,102,255));
		un.setBounds(98, 80, 180, 30);
		un.setFont(new Font("Arial", Font.BOLD, 20));
		
		deux = new JLabel(""+topSolo[1], SwingConstants.CENTER);
		deux.setForeground(new Color(0,102,255));
		deux.setBounds(98, 110, 180, 30);
		deux.setFont(new Font("Arial", Font.BOLD, 20));
		
		trois = new JLabel(""+topSolo[2], SwingConstants.CENTER);
		trois.setForeground(new Color(0,102,255));
		trois.setBounds(98, 140, 180, 30);
		trois.setFont(new Font("Arial", Font.BOLD, 20));
		
		JLabel titreM = new JLabel("Meilleurs Scores multi", SwingConstants.CENTER);
		titreM.setForeground(new Color(0,102,255));
		titreM.setBounds(98, 180, 180, 30);
		titreM.setFont(new Font("Arial", Font.BOLD, 15));
		
		unM = new JLabel(""+topMulti[0], SwingConstants.CENTER);
		unM.setForeground(new Color(0,102,255));
		unM.setBounds(98, 210, 180, 30);
		unM.setFont(new Font("Arial", Font.BOLD, 20));
		
		deuxM = new JLabel(""+topMulti[1], SwingConstants.CENTER);
		deuxM.setForeground(new Color(0,102,255));
		deuxM.setBounds(98, 240, 180, 30);
		deuxM.setFont(new Font("Arial", Font.BOLD, 20));
		
		troisM = new JLabel(""+topMulti[2], SwingConstants.CENTER);
		troisM.setForeground(new Color(0,102,255));
		troisM.setBounds(98, 270, 180, 30);
		troisM.setFont(new Font("Arial", Font.BOLD, 20));
		
		retour = new JButton("Retour");
		retour.setBackground(new Color(0,102,255));
		retour.setForeground(Color.white);
		retour.setBounds(98, 550, 180, 30);
		retour.setFocusPainted(false);
		
		this.setLayout(null);
		this.setBackground(new Color(223,223,223));
		this.add(titreS);
		this.add(un);
		this.add(deux);
		this.add(trois);
		this.add(titreM);
		this.add(unM);
		this.add(deuxM);
		this.add(troisM);
		this.add(retour);
	}
	
	public void addScoreSolo(int score) {
		int i,val=0,temp=0;
		
		for(i=0; i<topSolo.length; i++){
			val = i + 1;
			if(score > topSolo[i]){
				temp = topSolo[i];
				topSolo[i] = score;
				break;
			}
		}
		for(i=val; i<topSolo.length; i++){
			score = topSolo[i];
			topSolo[i] = temp;
			temp = score;
		}
	}
	
	public void addScoreMulti(int score) {
		int i,val=0,temp=0;
		
		for(i=0; i<topMulti.length; i++){
			val = i + 1;
			if(score > topMulti[i]){
				temp = topMulti[i];
				topMulti[i] = score;
				break;
			}
		}
		for(i=val; i<topMulti.length; i++){
			score = topMulti[i];
			topMulti[i] = temp;
			temp = score;
		}
	}
	
	public void update() {
		un.setText(""+topSolo[0]);
		deux.setText(""+topSolo[1]);
		trois.setText(""+topSolo[2]);
		unM.setText(""+topMulti[0]);
		deuxM.setText(""+topMulti[1]);
		troisM.setText(""+topMulti[2]);
	}
	
	public void saveToFile(){
		try {
			BufferedWriter fw = new BufferedWriter(new FileWriter("save.txt"));
			fw.write(topSolo[0]+"\n");
			fw.write(topSolo[1]+"\n");
			fw.write(topSolo[2]+"\n");
			fw.write(topMulti[0]+"\n");
			fw.write(topMulti[1]+"\n");
			fw.write(topMulti[2]+"\n");
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFromFile() {
		String str = null;
		int score;
		try {
			BufferedReader fr = new BufferedReader(new FileReader(save));
			
			score = 0;
			str = fr.readLine();
			if(str != null){
				for(int i=0; i<str.length(); i++)
					score = (score * 10) + (str.charAt(i) - 48);
				topSolo[0] = score;
			}

			score = 0;
			str = null;
			str = fr.readLine();
			if(str != null){
				for(int i=0; i<str.length(); i++)
					score = (score * 10) + (str.charAt(i) - 48);
				topSolo[1] = score;
			}

			
			score = 0;
			str = null;
			str = fr.readLine();
			if(str != null){
				for(int i=0; i<str.length(); i++)
					score = (score * 10) + (str.charAt(i) - 48);
				topSolo[2] = score;
			}
			
			score = 0;
			str = fr.readLine();
			if(str != null){
				for(int i=0; i<str.length(); i++)
					score = (score * 10) + (str.charAt(i) - 48);
				topMulti[0] = score;
			}

			score = 0;
			str = null;
			str = fr.readLine();
			if(str != null){
				for(int i=0; i<str.length(); i++)
					score = (score * 10) + (str.charAt(i) - 48);
				topMulti[1] = score;
			}

			
			score = 0;
			str = null;
			str = fr.readLine();
			if(str != null){
				for(int i=0; i<str.length(); i++)
					score = (score * 10) + (str.charAt(i) - 48);
				topMulti[2] = score;
			}

			fr.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public JButton getRetour() { return retour; }
}
