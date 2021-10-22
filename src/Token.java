
public class Token {

	private String tipo;
	private String texto;
	private int linha;

	public int getLinha() {
		return linha;
	}

	public void setLinha(int cont) {
		this.linha = cont;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getTexto() {
		return texto;
	}

	public void setTexto(String texto) {
		this.texto = texto;
	}

}
