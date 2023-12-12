package br.com.cesar.cm.visao;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;

import br.com.cesar.cm.modelo.Campo;
import br.com.cesar.cm.modelo.CampoEvento;
import br.com.cesar.cm.modelo.CampoObservador;

@SuppressWarnings("serial")
public class BotaoCampo extends JButton implements CampoObservador, MouseListener {

	private final Color BG_PADRAO = new Color(184, 184, 184);
	private final Color BG_MARCAR = new Color(8, 179, 247);
	private final Color BG_EXPLODIR = new Color(189, 66, 68);
	private final Color TEXTO_VERDE = new Color(0, 100, 0);

	private Campo campo;

	public BotaoCampo(Campo campo) {
		this.campo = campo;
		this.setBackground(BG_PADRAO);
		this.setOpaque(true);
		this.setBorder(BorderFactory.createBevelBorder(0));
		
		this.addMouseListener(this);
		campo.registrarObservador(this);
	}

	public void eventoOcorreu(Campo campo, CampoEvento evento) {

		switch (evento) {
		case ABRIR:
			this.aplicarEstiloAbrir();
			break;
		case MARCAR:
			this.aplicarEstiloMarcar();
			break;
		case EXPLODIR:
			this.aplicarEstiloExplodir();
			break;
		default:
			this.aplicarEstiloPadrao();
		}
	}

	private void aplicarEstiloPadrao() {
		this.setBackground(BG_PADRAO);
		this.setBorder(BorderFactory.createBevelBorder(0));
		this.setText("");
	}

	private void aplicarEstiloExplodir() {
		this.setBackground(BG_EXPLODIR);
		this.setForeground(Color.WHITE);
		this.setText("X");
	}

	private void aplicarEstiloMarcar() {
		this.setBackground(BG_MARCAR);
		this.setForeground(Color.BLACK);
		this.setText("M");
	}

	private void aplicarEstiloAbrir() {
		this.setBorder(BorderFactory.createLineBorder(Color.GRAY));
		
		if(campo.isMinado()) {
			this.setBackground(BG_EXPLODIR);
			return;
		}
		
		this.setBackground(BG_PADRAO);		

		switch (campo.minasNaVizinha()) {
		case 1:
			this.setForeground(TEXTO_VERDE);
			break;
		case 2:
			this.setForeground(Color.BLUE);
			break;
		case 3:
			this.setForeground(Color.YELLOW);
			break;
		case 4:
		case 5:
		case 6:
			this.setForeground(Color.RED);
			break;
		default:
			this.setForeground(Color.PINK);
		}

		String valor = !campo.vizinhaSegura() ? campo.minasNaVizinha() + "" : "";
		this.setText(valor);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (e.getButton() == 1) {
			campo.abrir();
		} else {
			campo.alternarMarcacao();
		}
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mouseReleased(MouseEvent e) {
	}
}
