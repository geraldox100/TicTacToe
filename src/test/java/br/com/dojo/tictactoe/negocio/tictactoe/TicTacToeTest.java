package br.com.dojo.tictactoe.negocio.tictactoe;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TicTacToeTest {

	private TicTacToe jogoPadrao;

	@Before
	public void setUpTicTacToe() {
		jogoPadrao = criarJogo();
	}

	@Test
	public void iniciarPartida() {
		Assert.assertEquals(jogoPadrao.getQuantidadeDePosicoes(), 9);
	}

	@Test
	public void verificarPosicoesDisponiveis() {
		Assert.assertEquals(jogoPadrao.getQuantidadeDePosicoesDisponiveis(), 9);
	}

	@Test
	public void adicionarJogadores() {
		TicTacToe ttt = new TicTacToe(3);
		ttt.adicionarJogador("Eu");
		Assert.assertEquals(ttt.getQuantidadeDeJogadores(), 1);
	}

	@Test
	public void adicionarDoisJogadores() {
		TicTacToe ttt = criarJogo();

		Assert.assertEquals(ttt.getQuantidadeDeJogadores(), 2);
	}

	@Test(expected = RuntimeException.class)
	public void adicionarTresJogadores() {
		jogoPadrao.adicionarJogador("Biscate");

	}

	@Test(expected = RuntimeException.class)
	public void adicionarOMesmoJogador() {
		TicTacToe ttt = new TicTacToe(3);

		ttt.adicionarJogador("Eu");
		ttt.adicionarJogador("Eu");
	}

	@Test
	public void verificarSePartidaSemJogadoresPodeSerIniciada() {
		TicTacToe ttt = new TicTacToe(3);
		Assert.assertFalse(ttt.estaProntoParaIniciar());
	}

	@Test
	public void verificarSePartidaComUmJogadorPodeSerIniciada() {
		TicTacToe ttt = new TicTacToe(3);
		ttt.adicionarJogador("Eu");
		Assert.assertFalse(ttt.estaProntoParaIniciar());
	}

	@Test
	public void verificarSePartidaComDoisJogadoresPodeSerIniciada() {
		Assert.assertTrue(jogoPadrao.estaProntoParaIniciar());
	}

	@Test(expected = RuntimeException.class)
	public void lancarJogadaComJogoSemEstarPronto() {

		TicTacToe ttt = new TicTacToe(3);
		ttt.adicionarJogador("Eu");
		ttt.realizarJogada(1, 1);

	}

	@Test
	public void realizarUmaJogadaNaPrimeiraPosicao() {

		Assert.assertEquals(jogoPadrao.getQuantidadeDePosicoesDisponiveis(), 9);

		jogoPadrao.realizarJogada(1, 1);

		Assert.assertEquals(jogoPadrao.getQuantidadeDePosicoesDisponiveis(), 8);
		Assert.assertTrue(jogoPadrao.isPosicaoPreenchida(1, 1));

	}

	@Test
	public void verificarMinhaVezNoJogo() {
		Assert.assertTrue(jogoPadrao.isVezDoJogador("Eu"));
		Assert.assertFalse(jogoPadrao.isVezDoJogador("Voce"));

	}

	@Test
	public void verificarJogadorDaProximaJogada() {
		jogoPadrao.realizarJogada(1, 1);

		Assert.assertFalse(jogoPadrao.isVezDoJogador("Eu"));
		Assert.assertTrue(jogoPadrao.isVezDoJogador("Voce"));
	}

	@Test
	public void verificarInversaoDaJogada() {
		jogoPadrao.realizarJogada(1, 1);
		jogoPadrao.realizarJogada(2, 2);

		Assert.assertFalse(jogoPadrao.isVezDoJogador("Voce"));
		Assert.assertTrue(jogoPadrao.isVezDoJogador("Eu"));
	}

	@Test
	public void verificarExecucaoDeUmaJogada() {
		jogoPadrao.realizarJogada(0, 0);

		Assert.assertTrue(jogoPadrao.isPosicaoPreenchida(0, 0));
		verificarQueTodasEstaoMarcadasMenos(new int[][] { { 0, 0 } });

	}

	@Test
	public void verificarExecucaoDeDuasJogadas() {
		jogoPadrao.realizarJogada(0, 0);
		jogoPadrao.realizarJogada(0, 1);

		Assert.assertTrue(jogoPadrao.isPosicaoPreenchida(0, 0));
		Assert.assertTrue(jogoPadrao.isPosicaoPreenchida(0, 1));
		verificarQueTodasEstaoMarcadasMenos(new int[][] { { 0, 0 }, { 0, 1 } });

	}

	@Test(expected = RuntimeException.class)
	public void verificarValidacaoDeMarcacaoNoMesmoLugar() {
		jogoPadrao.realizarJogada(0, 0);
		jogoPadrao.realizarJogada(0, 0);

	}
	
	@Test
	public void tentarVerificarPosicaoQueNaoTemJogada() {
		TicTacToe ttt = new TicTacToe(3);

		ttt.adicionarJogador("Eu");
		ttt.adicionarJogador("Voce");

		Assert.assertEquals(jogoPadrao.getJogadorNaPosicao(0, 0), null);
	}

	@Test
	public void verificarSeJogadaFoiRealizaPeloJogadorCerto() {
		TicTacToe ttt = new TicTacToe(3);

		ttt.adicionarJogador("Eu");
		ttt.adicionarJogador("Voce");
		jogoPadrao.realizarJogada(0, 0);
		jogoPadrao.realizarJogada(2, 2);
		jogoPadrao.realizarJogada(1, 1);

		Assert.assertEquals(jogoPadrao.getJogadorNaPosicao(0, 0), "Eu");
		Assert.assertEquals(jogoPadrao.getJogadorNaPosicao(2, 2), "Voce");
		Assert.assertEquals(jogoPadrao.getJogadorNaPosicao(1, 1), "Eu");
	}
	
	@Test
	public void verificarSeHouveVencedorNaVertical(){
		jogoPadrao.realizarJogada(0,0);//1
		jogoPadrao.realizarJogada(1,1);//2
		jogoPadrao.realizarJogada(1,0);//1
		jogoPadrao.realizarJogada(1,2);//2
		jogoPadrao.realizarJogada(2,0);//1
		
		
		/*
		 * X  |   |
		 * X  | 0 | 0
		 * X  |   |
		 */
		
		Assert.assertTrue(jogoPadrao.verificarSeHouveVencedor());
	}
	
	@Test
	public void verificarSeHouveVencedorNaVerticalNoCentro(){
		jogoPadrao.realizarJogada(0,1);//1
		jogoPadrao.realizarJogada(1,0);//2
		jogoPadrao.realizarJogada(1,1);//1
		jogoPadrao.realizarJogada(1,2);//2
		jogoPadrao.realizarJogada(2,1);//1
		
		
		/*
		 *    | X |
		 * 0  | X | 0
		 *    | X |
		 */
		
		Assert.assertTrue(jogoPadrao.verificarSeHouveVencedor());
	}
	
	@Test
	public void verificarSeHouveVencedorNaVerticalNaDireitas(){
		jogoPadrao.realizarJogada(0,2);//1
		jogoPadrao.realizarJogada(1,0);//2
		jogoPadrao.realizarJogada(1,2);//1
		jogoPadrao.realizarJogada(1,1);//2
		jogoPadrao.realizarJogada(2,2);//1
		
		
		/*
		 *    |   | X
		 * 0  | 0 | X
		 *    |   | X
		 */
		
		Assert.assertTrue(jogoPadrao.verificarSeHouveVencedor());
	}
	
	@Test
	public void verificarSeHouveVencedorNaVerticalNaDireitasSemVencedor(){
		jogoPadrao.realizarJogada(0,2);//1
		/*
		 *    |   | X
		 *    |   | 
		 *    |   |  
		 */
		
		Assert.assertFalse(jogoPadrao.verificarSeHouveVencedor());
	}
	
	@Test
	public void verificarSeHouverVencedorNaHorizontalNaLinhaTopo(){
			jogoPadrao.realizarJogada(0,0);//1
			jogoPadrao.realizarJogada(1,0);//2
			jogoPadrao.realizarJogada(0,1);//1
			jogoPadrao.realizarJogada(1,2);//2
			jogoPadrao.realizarJogada(0,2);//1
			
			
			/*
			 *  X | X | X
			 *  0 |   | 0
			 *    |   |
			 */
			
			Assert.assertTrue(jogoPadrao.verificarSeHouveVencedor());
	}
	
	@Test
	public void verificarSeHouverVencedorNaHorizontalNaLinhaDoMeio(){
			jogoPadrao.realizarJogada(1,0);//1
			jogoPadrao.realizarJogada(0,0);//2
			jogoPadrao.realizarJogada(1,1);//1
			jogoPadrao.realizarJogada(0,2);//2
			jogoPadrao.realizarJogada(1,2);//1
			
			
			/*
			 *  0 |   | 0
			 *  X | X | X
			 *    |   |
			 */
			
			Assert.assertTrue(jogoPadrao.verificarSeHouveVencedor());
	}
	
	@Test
	public void verificarSeHouverVencedorNaHorizontalNaLinhaDoBaixo(){
			jogoPadrao.realizarJogada(2,0);//1
			jogoPadrao.realizarJogada(0,0);//2
			jogoPadrao.realizarJogada(2,1);//1
			jogoPadrao.realizarJogada(0,2);//2
			jogoPadrao.realizarJogada(2,2);//1
			
			
			/*
			 *  0 |   | 0
			 *    |   | 
			 *  X | X | X
			 */
			
			Assert.assertTrue(jogoPadrao.verificarSeHouveVencedor());
	}
	
	@Test
	public void verificarSeHouverVencedorNaDiagonalEsquerdaParaDireita(){
			jogoPadrao.realizarJogada(0,0);//1
			jogoPadrao.realizarJogada(2,0);//2
			jogoPadrao.realizarJogada(1,1);//1
			jogoPadrao.realizarJogada(0,2);//2
			jogoPadrao.realizarJogada(2,2);//1
			
			
			/*
			 *  X |   | 0
			 *    | X | 
			 *  0 |   | X
			 */
			
			Assert.assertTrue(jogoPadrao.verificarSeHouveVencedor());
	}
	
	@Test
	public void verificarSeHouverVencedorNaDiagonalDireitaParaEsquerda(){
			jogoPadrao.realizarJogada(0,2);//X
			jogoPadrao.realizarJogada(0,0);//0
			jogoPadrao.realizarJogada(1,1);//X
			jogoPadrao.realizarJogada(2,2);//0
			ResultadoJogada resultadoJogada = jogoPadrao.realizarJogada(2,0);//X
			
			
			/*
			 *  0 |   | X
			 *    | X | 
			 *  X |   | 0
			 */
			
			Assert.assertEquals(resultadoJogada.getStatus(),"finalizado");
			Assert.assertEquals(resultadoJogada.getVencedor(),"Eu");
	}
	
	@Test
	public void verificaQueDeuVeia(){
		jogoPadrao.realizarJogada(0,0);//X
		jogoPadrao.realizarJogada(0,1);//0
		jogoPadrao.realizarJogada(0,2);//X
		
		jogoPadrao.realizarJogada(1,0);//0
		jogoPadrao.realizarJogada(1,1);//X
		jogoPadrao.realizarJogada(2,0);//0
		
		jogoPadrao.realizarJogada(1,2);//X
		jogoPadrao.realizarJogada(2,2);//0
		ResultadoJogada resposta = jogoPadrao.realizarJogada(2,1);//X
		
		/*
		 *  	0   1   2
		 *  
		 *  0	X | 0 | X
		 *  1	0 | X | X
		 *  2	0 | X | 0
		 */
		
		Assert.assertEquals(resposta.getStatus(),"velha");
		
		
	}
	
	@Test(expected = RuntimeException.class)
	public void verificarQueNaoEhPossivelRealizarJogadaAposVencedor(){
			jogoPadrao.realizarJogada(0,2);//X
			jogoPadrao.realizarJogada(0,0);//0
			jogoPadrao.realizarJogada(1,1);//X
			jogoPadrao.realizarJogada(2,2);//0
			jogoPadrao.realizarJogada(2,0);//X
			
			
			/*
			 *  0 |   | X
			 *    | X | 
			 *  X |   | 0
			 */
			
			jogoPadrao.realizarJogada(0,1);//0
	}

	private void verificarQueTodasEstaoMarcadasMenos(int[]... posicoesExcluidas) {
		for (int linha = 0; linha < jogoPadrao.getTamanhoTabuleiro(); linha++) {
			for (int coluna = 0; coluna < jogoPadrao.getTamanhoTabuleiro(); coluna++) {

				if (verificarSePosicaoNaoExcluida(linha, coluna,
						posicoesExcluidas)) {
					Assert.assertFalse(jogoPadrao.isPosicaoPreenchida(linha,
							coluna));
				}

			}
		}
	}

	private boolean verificarSePosicaoNaoExcluida(int linha, int coluna,
			int[][] posicoesExcluidas) {

		for (int[] posicaoExcluida : posicoesExcluidas) {
			if (posicaoExcluida[0] == linha && posicaoExcluida[1] == coluna) {
				return false;
			}
		}

		return true;
	}

	private TicTacToe criarJogo() {
		TicTacToe ttt = new TicTacToe(3);

		ttt.adicionarJogador("Eu");
		ttt.adicionarJogador("Voce");
		return ttt;
	}
}
