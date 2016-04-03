package org.esiea.architecturelogiciel.tetris;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.event.EventListenerList;

public class Connexion extends JPanel {
    
	private static final long serialVersionUID = 1L;
	private	JLabel pseudoLabel, ipServerLabel, statut; 
    private JTextField pseudo, ipServer;
    private JButton connect, start, retour;
    private Socket socket;
    private final EventListenerList listeners = new EventListenerList();
    
    public Connexion() {
    	pseudoLabel = new JLabel("Pseudo", SwingConstants.CENTER);
		pseudoLabel.setForeground(new Color(0,102,255));
		pseudoLabel.setBounds(10, 80, 100, 30);
		pseudoLabel.setFont(new Font("Arial", Font.BOLD, 15));
		
		pseudo = new JTextField();
		pseudo.setBounds(110, 80, 220, 30);
		pseudo.setFont(new Font("Arial", Font.BOLD, 15));
		
		ipServerLabel = new JLabel("IP Serveur", SwingConstants.CENTER);
		ipServerLabel.setForeground(new Color(0,102,255));
		ipServerLabel.setBounds(10, 130, 100, 30);
		ipServerLabel.setFont(new Font("Arial", Font.BOLD, 15));
    
		ipServer = new JTextField();
		ipServer.setBounds(110, 130, 220, 30);
		ipServer.setFont(new Font("Arial", Font.BOLD, 15));
		
		connect = new JButton("connect");
		connect.setBackground(new Color(0,102,255));
		connect.setForeground(Color.white);
		connect.setBounds(98, 200, 180, 30);
		connect.setFocusPainted(false);
		
		statut = new JLabel("", SwingConstants.CENTER);
		statut.setForeground(new Color(0,102,255));
		statut.setBounds(98, 300, 180, 30);
		statut.setFont(new Font("Arial", Font.BOLD, 15));
		
		start = new JButton("lancer partie");
		start.setBackground(new Color(0,102,255));
		start.setForeground(Color.white);
		start.setBounds(98, 400, 180, 30);
		start.setFocusPainted(false);
		start.setVisible(false);
		
		retour = new JButton("retour");
		retour.setBackground(new Color(0,102,255));
		retour.setForeground(Color.white);
		retour.setBounds(98, 550, 180, 30);
		retour.setFocusPainted(false);
		
		this.setLayout(null);
		this.setBackground(new Color(223,223,223));
		this.add(pseudoLabel);
		this.add(pseudo);
		this.add(ipServerLabel);
		this.add(ipServer);
		this.add(connect);
		this.add(start);
		this.add(retour);
		this.add(statut);
    
    }
    
    public void sartConnexion() {
    	try {
    		statut.setText(pseudo.getText()+" en attente de connexion...");
			socket = new Socket(ipServer.getText(), 2009);
			PrintWriter out = new PrintWriter(socket.getOutputStream());
			out.println(pseudo.getText());
			out.flush();
			statut.setText(pseudo.getText()+" connecté !");
			start.setVisible(true);
			fireConnected();
		} catch (UnknownHostException e) {
			statut.setText("Impossible de se connecter à l'adresse " +socket.getLocalAddress());
			e.printStackTrace();
		} catch (IOException e) {
			statut.setText("Aucun serveur à l'écoute du port "+socket.getLocalPort());
			e.printStackTrace();
		}
    }
    
    public void addGameOverListener(GameOverListener listener){
		listeners.add(GameOverListener.class, listener);
	}
	
	public GameOverListener[] getGameOverListeners() {
		return listeners.getListeners(GameOverListener.class);
	}
	
	protected void fireConnected() {
		for(GameOverListener listener : getGameOverListeners()){
			listener.connected();
		}
	}
    
    public JButton getConnect() { return connect; }
	public JButton getStart() { return start; }
	public JButton getRetour() { return retour; }
	public JLabel getStatut() { return statut; }
	public Socket getSocket() {  return socket; }
}
