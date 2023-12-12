package br.com.cesar.cm.modelo;

public class ResultadoEvento {
	
	private final boolean ganhou;
	
	public ResultadoEvento(boolean ganhou) {
		this.ganhou = ganhou;
	}

	public boolean isGanhou() {
		return this.ganhou;
	}
}
