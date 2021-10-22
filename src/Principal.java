
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Principal {

	public static void main(String[] args) throws IOException {
		List<String> linhas = Files.readAllLines(Paths.get("teste.txt"), Charset.defaultCharset());
		percorreTexto(linhas);
	}

	public static void percorreTexto(List<String> linhas) {
		boolean continua = true;

		ArrayList<Token> linha_com_token = new ArrayList<Token>();
		Identificador a = new Identificador();

		int cont = 0;
		while (continua) {
			for (String linha : linhas) {

				if (linha.replaceAll("\\s", "").length() == 0) {
					continue;
				} // pula linha vazia

				String[] palavras = linha.split(" ");

				for (String palavra : palavras) {
					Token token = new Token();
					palavra = palavra.replaceAll("\\s", "");
					if (isOperadorLogico(palavra)) {
						token.setTipo(a.OPERADOR_LOGICO);
					} else if (isPrimitivo(palavra)) {
						token.setTipo(a.PALAVRA_PRIMITIVA);
					} else if (isReservada(palavra)) {
						token.setTipo(a.PALAVRA_RESERVADA);
					} else if (isOperadorAritmetico(palavra)) {
						token.setTipo(a.OPERADORA_ARITMETICO);
					} else if (isAtribuicao(palavra)) {
						token.setTipo(a.ATRIBUICAO);
					} else if (Character.isDigit(palavra.charAt(0))) {
						token.setTipo(a.NUMERO);
					} else if (Character.isLetter(palavra.charAt(0))) {
						token.setTipo(a.IDENTIFICADOR);
					} else {
						token.setTipo(a.DESCONHECIDO);
					}
					token.setTexto(palavra);
					token.setLinha(cont);
					linha_com_token.add(token);
					cont++;
				}

				// VERIFICAR SINTAXE DA LINHA
			}
			continua = false;
			System.out.println("-------------CONTEUDO_TOKEN-------------");
			for (int i = 0; i < linha_com_token.size(); i++) {
				if (!((i + 1) == linha_com_token.size())) {
					System.out.println("Item: " + linha_com_token.get(i).getTexto() + "\nTipo: "
							+ linha_com_token.get(i).getTipo());
					if (!(linha_com_token.get(i).getTipo().equalsIgnoreCase(a.PALAVRA_PRIMITIVA)
							&& linha_com_token.get(i + 1).getTipo().equalsIgnoreCase(a.IDENTIFICADOR))) {
						System.err.println("Ocorreu um erro: " + linha_com_token.get(i).getTexto());
					} else if (!(linha_com_token.get(i).getTipo().equalsIgnoreCase(a.IDENTIFICADOR)
							&& (linha_com_token.get(i + 1).getTipo().equalsIgnoreCase(a.NUMERO)
									|| linha_com_token.get(i + 1).getTipo().equalsIgnoreCase(a.STRING)))) {
						System.err.println("Ocorreu um erro: " + linha_com_token.get(i).getTexto());
					}
				}
			}
			System.out.println("------------------FIM-------------------");
		}
	}

	// verifica se � uma palavra reservada
	public static boolean isReservada(String palavra) {

		if (palavra.equalsIgnoreCase("if") || palavra.equalsIgnoreCase("for") || palavra.equalsIgnoreCase("while")
				|| palavra.equalsIgnoreCase(";") || palavra.equalsIgnoreCase("{") || palavra.equalsIgnoreCase("}")
				|| palavra.equalsIgnoreCase("[") || palavra.equalsIgnoreCase("]") || palavra.equalsIgnoreCase("(")
				|| palavra.equalsIgnoreCase(")") || palavra.equalsIgnoreCase("return") || palavra.equalsIgnoreCase("do")
				|| palavra.equalsIgnoreCase("foreach")) {
			return true;
		}
		return false;
	}

	public static boolean isPrimitivo(String palavra) {
		if (palavra.equalsIgnoreCase("float") || palavra.equalsIgnoreCase("int") || palavra.equalsIgnoreCase("string")
				|| palavra.equalsIgnoreCase("double") || palavra.equalsIgnoreCase("boolean")) {
			return true;
		}
		return false;
	}

	public static boolean isOperadorLogico(String palavra) {
		if (palavra.equalsIgnoreCase("<") || palavra.equalsIgnoreCase(">") || palavra.equalsIgnoreCase("=>")
				|| palavra.equalsIgnoreCase("<=") || palavra.equalsIgnoreCase("==")) {
			return true;
		}
		return false;
	}

	// verifica se � uma atribui��o
	public static boolean isAtribuicao(String palavra) {
		if (palavra.equalsIgnoreCase("=")) {
			return true;
		}
		return false;
	}

	// verifica se � um operador
	public static boolean isOperadorAritmetico(String palavra) {
		if (palavra.equalsIgnoreCase("+") || palavra.equalsIgnoreCase("-") || palavra.equalsIgnoreCase("/")
				|| palavra.equalsIgnoreCase("*")) {
			return true;
		}
		return false;
	}

}