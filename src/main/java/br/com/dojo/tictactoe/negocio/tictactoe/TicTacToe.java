package br.com.dojo.tictactoe.negocio.tictactoe;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class TicTacToe {

	private static final int QUANTIDADE_JOGADORES = 2;
	private int tamanho;
	private int jogadasDisponiveis;
	private List<String> jogadores = new ArrayList<>();
	private int jogadorDaVez = 0;
	private Integer[][] tabuleiro;
	private boolean jogoFinalizado = false;
	private String vencedor;

	public TicTacToe(int tamanho) {
		this.tamanho = tamanho;
		this.jogadasDisponiveis = getQuantidadeDePosicoes();
		this.tabuleiro = new Integer[tamanho][tamanho];
	}

	public int getTamanhoTabuleiro() {
		return tamanho;
	}

	public ResultadoJogada realizarJogada(int linha, int coluna) {
		if (!estaProntoParaIniciar()) {
			throw new IllegalStateException("Apressadinho!!");
		}
		if (isPosicaoPreenchida(linha, coluna)) {
			throw new RuntimeException("Posição já marcada!!");
		}
		
		if (jogoFinalizado) {
			throw new RuntimeException("Esse jogo ja acabou!! Perdeu playboy!!");
		}

		tabuleiro[linha][coluna] = jogadorDaVez;

		jogadasDisponiveis--;

		if (jogadorDaVez == 0) {
			jogadorDaVez = 1;
		} else {
			jogadorDaVez = 0;
		}
		
		jogoFinalizado = verificarSeHouveVencedor();
		
		if (jogoFinalizado && vencedor != null) {
			return new ResultadoJogada("finalizado",vencedor);			
		} else {
			return new ResultadoJogada("velha",null);
			
		}
		
	}

	public int getQuantidadeDePosicoes() {
		return (int) Math.pow(tamanho, 2);
	}

	public int getQuantidadeDePosicoesDisponiveis() {
		return jogadasDisponiveis;
	}

	public void adicionarJogador(String jogador) {
		verificarQuantidadeDeJogadores();
		verificaSeJogadorJaEstaAdicionado(jogador);
		this.jogadores.add(jogador);
	}

	private void verificaSeJogadorJaEstaAdicionado(String jogador) {
		if (this.jogadores.contains(jogador)) {
			throw new RuntimeException("clone");
		}
	}

	private void verificarQuantidadeDeJogadores() {
		if (jogadores.size() == QUANTIDADE_JOGADORES)
			throw new RuntimeException("biscate no ninho");
	}

	public int getQuantidadeDeJogadores() {
		return this.jogadores.size();
	}

	public boolean estaProntoParaIniciar() {
		return this.jogadores.size() == QUANTIDADE_JOGADORES;
	}

	public boolean isVezDoJogador(String jogador) {
		return jogadores.get(jogadorDaVez).equals(jogador);
	}

	public boolean isPosicaoPreenchida(int linha, int coluna) {

		return getPosicao(linha, coluna) != null;

	}

	public String getJogadorNaPosicao(int linha, int coluna) {
		Integer indiceJogador = getPosicao(linha, coluna);
		if (indiceJogador == null) {
			return null;
		} else {
			return jogadores.get(indiceJogador);
		}
	}

	private Integer getPosicao(int linha, int coluna) {
		return tabuleiro[linha][coluna];
	}

	public boolean verificarSeHouveVencedor() {

		for (int coluna = 0; coluna < tamanho; coluna++) {
			if (verificarSeHouveVencedorVertical(coluna) || verificarSeHouveVencedorHorizontal(coluna)) {
				return true;
			}
		}
		
		if(verificarSeHouveVencedorNaDiagonalDireitaParaEsquerda() || verificarSeHouveVencedorNaDiagonalEsquerdaParaDireita()){
			return true;
		}
		
		return false;
	}

	private boolean verificarSeHouveVencedorHorizontal(int linha) {
		
		Set<Integer> ganhador = new HashSet<>();
		for (int coluna = 0; coluna < tamanho; coluna++) {
			ganhador.add(getPosicao(linha,coluna));
		}
		return verificaGanhador(ganhador);
	}

	private boolean verificarSeHouveVencedorVertical(int coluna) {
		Set<Integer> ganhador = new HashSet<>();
		for (int linha = 0; linha < tamanho; linha++) {
			ganhador.add(getPosicao(linha, coluna));
		}
		return verificaGanhador(ganhador);

	}
	
	private boolean verificarSeHouveVencedorNaDiagonalEsquerdaParaDireita(){
		
		Set<Integer> ganhador = new HashSet<Integer>();
		
		for (int i = 0; i < tamanho; i++) {
			ganhador.add(tabuleiro[i][i]);
		}
		return verificaGanhador(ganhador);
		
	}

	private boolean verificarSeHouveVencedorNaDiagonalDireitaParaEsquerda(){
		
		Set<Integer> ganhador = new HashSet<Integer>();
		
		int coluna = tamanho -1;
		
		for (int linha = 0; linha < tamanho ; linha++) {
			ganhador.add(getPosicao(linha, coluna));
			
			coluna--;
		}
		return verificaGanhador(ganhador);
		
	}
	
	private boolean verificaGanhador(Set<Integer> ganhador) {
		if(ganhador.size() == 1){			
			Iterator<Integer> it = ganhador.iterator();
			Integer vencedor = it.next();
			if (vencedor != null) {
				this.vencedor = jogadores.get(vencedor);
				return true;
			}
		}
		
		return false;
	}

}
