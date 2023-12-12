package br.com.cesar.cm.visao;

import javax.swing.JFrame;

import br.com.cesar.cm.modelo.Tabuleiro;

@SuppressWarnings("serial")
public class TelaPrincipal extends JFrame{
	
	public TelaPrincipal() {
		Tabuleiro tabuleiro = new Tabuleiro(16,30,50);
		this.add(new PainelTabuleiro(tabuleiro));
		this.setTitle("Campo Minado");
		this.setSize(690,438);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setVisible(true);
	}

	public static void main(String[] args) {		
		new TelaPrincipal();
	}
}
