package org.esiea.architecturelogiciel.tetris;

import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class App extends JFrame implements KeyListener, ActionListener, GameOverListener{
	
    private Accueil accueil;
    private Connexion connexion;
	private RunGame runGame;
	private RunGameMulti runGameMulti;
	private Thread trunGame;
	private Statistiques stats;
	private JPanel framePanel;
	private CardLayout frameLayout;
	private int witchRun = 0;
	
    public static final long serialVersionUID = 234567901;
    
    public App () {
    	accueil = new Accueil();
    	runGame = new RunGame();
    	stats = new Statistiques();
    	connexion = new Connexion();
    	
    	accueil.getSolo().addActionListener(this);
    	accueil.getStat().addActionListener(this);
    	accueil.getMulti().addActionListener(this);
    	runGame.addGameOverListener(this);
    	stats.getRetour().addActionListener(this);
    	connexion.addGameOverListener(this);
    	connexion.getConnect().addActionListener(this);
    	connexion.getStart().addActionListener(this);
    	connexion.getRetour().addActionListener(this);
    	
        frameLayout = new CardLayout();
        framePanel = new JPanel();
        framePanel.setLayout(frameLayout);
        framePanel.add(accueil, "accueil");
        framePanel.add(runGame.getGrille(), "grille");
        framePanel.add(stats, "stats");
        framePanel.add(connexion, "connexion");
    	
    	this.setTitle("Tetris");
        this.setSize(376,669);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setContentPane(framePanel);
        this.setVisible(true);
        this.addKeyListener(this);
    }
      
    public static void main( String[] args ){
        new App();
    }

	public void keyPressed(KeyEvent arg0) {
		switch (arg0.getKeyCode()){
		case KeyEvent.VK_LEFT :
			if(witchRun == 1)
				runGame.moveLeft();
			if(witchRun == 2)
				runGameMulti.moveLeft();
		break;
		
		case KeyEvent.VK_RIGHT :
			if(witchRun == 1)
				runGame.moveRight();
			if(witchRun == 2)
				runGameMulti.moveRight();
		break;
		
		case KeyEvent.VK_UP :
			if(witchRun == 1)
				runGame.turn();
			if(witchRun == 2)
				runGameMulti.turn();
		break;
		
		}
	}

	public void keyReleased(KeyEvent arg0) {
		switch (arg0.getKeyCode()){
		case KeyEvent.VK_DOWN :
			if(witchRun == 1)
				runGame.fallDown();
			if(witchRun == 2)
				runGameMulti.fallDown();
		break;
		}
	}

	public void keyTyped(KeyEvent arg0) {}

	public void actionPerformed(ActionEvent arg0) {
		if(arg0.getSource() == accueil.getSolo()){
	    	trunGame = new Thread(runGame);
	    	runGame.getGrille().cleanGrille();
	    	frameLayout.show(framePanel, "grille");
	    	this.requestFocus();
	    	trunGame.start();
	    	witchRun = 1;
		}	
		if(arg0.getSource() == accueil.getStat()){
	    	frameLayout.show(framePanel, "stats");
	    	this.requestFocus();
		}
		
		if(arg0.getSource() == accueil.getMulti()){
	    	frameLayout.show(framePanel, "connexion");
	    	this.requestFocus();
		}
		
		if(arg0.getSource() == stats.getRetour()){
	    	frameLayout.show(framePanel, "accueil");
	    	this.requestFocus();
		}
		
		if(arg0.getSource() == connexion.getRetour()){
			try {
				if(connexion.getSocket() != null)
					connexion.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			connexion.getStatut().setText("");
			connexion.getStart().setVisible(false);
	    	frameLayout.show(framePanel, "accueil");
	    	this.requestFocus();
		}
		
		if(arg0.getSource() == connexion.getConnect()){
			connexion.sartConnexion();
		}
		
		if(arg0.getSource() == connexion.getStart()){
	    	trunGame = new Thread(runGameMulti);
	    	runGameMulti.getGrille().cleanGrille();
	    	frameLayout.show(framePanel, "grilleMulti");
	    	this.requestFocus();
	    	trunGame.start();
	    	witchRun = 2;
		}
		
	}

	@Override
	public void gameIsOver() {
		String[] options = {"Rejouer", "Sortir"};
		
		stats.addScoreSolo(runGame.getGrille().getScore());
		stats.update();
		stats.saveToFile();
		
		int rang = JOptionPane.showOptionDialog(null, "Score : "+runGame.getGrille().getScore(),
				"Game Over !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, options, options[1]);
		
		if(rang == 0){
			trunGame = new Thread(runGame);
			runGame.getGrille().cleanGrille();
	    	frameLayout.show(framePanel, "grille");
	    	runGame.getGrille().resetScore();
	    	this.requestFocus();
	    	trunGame.start();
		}
		if(rang == 1){
			frameLayout.show(framePanel, "accueil");
			runGame.getGrille().resetScore();
	    	this.requestFocus();
		}
	}
	
	@Override
	public void gameMultiIsOver() {
		String[] options = {"Rejouer", "Sortir"};
		
		stats.addScoreMulti(runGameMulti.getGrille().getScore());
		stats.update();
		stats.saveToFile();
		
		int rang = JOptionPane.showOptionDialog(null, "Score : "+runGameMulti.getGrille().getScore(),
				"Game Over !", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE,
				null, options, options[1]);
		
		if(rang == 0){
			trunGame = new Thread(runGameMulti);
			runGameMulti.getGrille().cleanGrille();
	    	frameLayout.show(framePanel, "grilleMulti");
	    	runGameMulti.getGrille().resetScore();
	    	this.requestFocus();
	    	trunGame.start();
		}
		if(rang == 1){
			try {
				runGameMulti.getThread().stop();
				connexion.getSocket().close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			connexion.getStatut().setText("");
			connexion.getStart().setVisible(false);
			frameLayout.show(framePanel, "accueil");
			runGameMulti.getGrille().resetScore();
	    	this.requestFocus();
		}
	}
	
	@Override
	public void connected(){
	runGameMulti = new RunGameMulti(connexion.getSocket());
	runGameMulti.addGameOverListener(this);
	framePanel.add(runGameMulti.getGrille(), "grilleMulti");
	}
}
