
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

		Identificador a = new Identificador();

		int cont = 0;
		while (continua) {
			for (String linha : linhas) {
				ArrayList<Token> linha_com_token = new ArrayList<Token>();


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
				}
				cont++;

				// VERIFICAR SINTAXE DA LINHA
//				if (
//						linha_com_token.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_PRIMITIVA) && 
//						linha_com_token.get(1).getTipo().equalsIgnoreCase(a.IDENTIFICADOR) && 
//						linha_com_token.get(2).getTipo().equalsIgnoreCase(a.ATRIBUICAO) && 
//						linha_com_token.get(3).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA) && 
//						linha_com_token.get(3).getTexto().equalsIgnoreCase(";")						
//					) {
//					System.out.println("declaração de variavel correta!");
//					
//				}else {
//					System.err.println("declaração de variavel incorreta !"+linha_com_token.get(0).getLinha());
//				}
				System.out.println(linhaToString(linha_com_token));

			}
			
			continua = false;
		}
	}

	public static String linhaToString(ArrayList<Token> linha) {
		String str= linha.get(0).getLinha()+" | ";
		for (Token elemento : linha) {
			str += elemento.getTexto()+" | "+elemento.getTipo() + "   ";
		}
		return str;
	}
	
	// verifica se é uma palavra reservada
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

	// verifica se é uma atribuição
	public static boolean isAtribuicao(String palavra) {
		if (palavra.equalsIgnoreCase("=")) {
			return true;
		}
		return false;
	}

	// verifica se é um operador
	public static boolean isOperadorAritmetico(String palavra) {
		if (palavra.equalsIgnoreCase("+") || palavra.equalsIgnoreCase("-") || palavra.equalsIgnoreCase("/")
				|| palavra.equalsIgnoreCase("*")) {
			return true;
		}
		return false;
	}

}