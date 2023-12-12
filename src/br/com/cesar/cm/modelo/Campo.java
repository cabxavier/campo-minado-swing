package br.com.cesar.cm.modelo;

import java.util.ArrayList;
import java.util.List;

public class Campo {

	private final int linha;
	private final int coluna;

	private boolean aberto = false;
	private boolean minado = false;
	private boolean marcado = false;

	private List<Campo> vizinhos = new ArrayList<>();
	private List<CampoObservador> observadores = new ArrayList<>();

	Campo(int linha, int coluna) {
		this.linha = linha;
		this.coluna = coluna;
	}

	public void registrarObservador(CampoObservador observador) {
		this.observadores.add(observador);
	}

	private void notificarObservadores(CampoEvento evento) {
		this.observadores.stream().forEach(o -> o.eventoOcorreu(this, evento));
	}

	boolean adicionarVizinho(Campo vizinho) {
		boolean linhaDiferente = this.linha != vizinho.linha;
		boolean colunaDiferente = this.coluna != vizinho.coluna;
		boolean diagonal = linhaDiferente && colunaDiferente;

		int deltaLinha = Math.abs(linha - vizinho.linha);
		int deltaColuna = Math.abs(coluna - vizinho.coluna);
		int deltaGeral = deltaColuna + deltaLinha;

		if (deltaGeral == 1 && !diagonal) {
			this.vizinhos.add(vizinho);
			return true;
		} else if (deltaGeral == 2 && diagonal) {
			this.vizinhos.add(vizinho);
			return true;
		} else {
			return false;
		}
	}

	public void alternarMarcacao() {
		if (!this.aberto) {
			this.marcado = !this.marcado;

			if (this.marcado) {
				this.notificarObservadores(CampoEvento.MARCAR);
			} else {
				this.notificarObservadores(CampoEvento.DESMARCAR);
			}
		}
	}

	public boolean abrir() {

		if (!this.aberto && !this.marcado) {
			if (this.minado) {
				this.notificarObservadores(CampoEvento.EXPLODIR);
				return true;
			}

			this.setAberto(true);

			if (this.vizinhaSegura()) {
				this.vizinhos.forEach(v -> v.abrir());
			}

			return true;

		} else {

			return false;
		}
	}

	public boolean vizinhaSegura() {
		return this.vizinhos.stream().noneMatch(v -> v.minado);
	}

	void minar() {
		this.minado = true;
	}

	public boolean isMinado() {
		return this.minado;
	}

	public boolean isMarcado() {
		return this.marcado;
	}

	void setAberto(boolean aberto) {
		this.aberto = aberto;

		if (this.aberto) {
			this.notificarObservadores(CampoEvento.ABRIR);
		}
	}

	public boolean isAberto() {
		return this.aberto;
	}

	public boolean isFechado() {
		return !this.isAberto();
	}

	public int getLinha() {
		return linha;
	}

	public int getColuna() {
		return coluna;
	}

	boolean objetivoAlcancado() {
		boolean desvendado = !this.minado && this.aberto;
		boolean protegido = this.minado && this.marcado;

		return desvendado || protegido;
	}

	public int minasNaVizinha() {
		return (int) this.vizinhos.stream().filter(v -> v.minado).count();
	}

	void reiniciar() {
		this.aberto = false;
		this.minado = false;
		this.marcado = false;
		this.notificarObservadores(CampoEvento.REINICIAR);
	}
}
