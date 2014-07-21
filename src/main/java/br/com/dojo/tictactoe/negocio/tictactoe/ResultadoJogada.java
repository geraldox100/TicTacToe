package br.com.dojo.tictactoe.negocio.tictactoe;

public class ResultadoJogada {

	private String status;
	private String vencedor;

	public ResultadoJogada(String status, String vencedor) {
		this.status = status;
		this.vencedor = vencedor;
	}

	public String getStatus() {
		return status;
	}
	
	public String getVencedor() {
		return vencedor;
	}

}
