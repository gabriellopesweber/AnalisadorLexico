package principal;

public class Token {
	private double tokenInicial;
	private double tokenFinal;

	private String[] tipoDeAcao; // Valida/Atribui/Remove

	private double perna;

	public double getTokenInicial() {
		return tokenInicial;
	}

	public void setTokenInicial(double tokenInicial) {
		this.tokenInicial = tokenInicial;
	}

	public double getTokenFinal() {
		return tokenFinal;
	}

	public void setTokenFinal(double tokenFinal) {
		this.tokenFinal = tokenFinal;
	}

	public String[] getTipoDeAcao() {
		return tipoDeAcao;
	}

	public void setTipoDeAcao(String[] tipoDeAcao) {
		this.tipoDeAcao = tipoDeAcao;
	}

	public double getPerna() {
		return perna;
	}

	public void setPerna(double perna) {
		this.perna = perna;
	}

}
