package principal;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Principal {

	public static void main(String[] args) throws IOException {
		List<String> linhas = Files.readAllLines(Paths.get("teste.txt"), Charset.defaultCharset());
		percorreTexto(linhas);
	}

	public static void percorreTexto(List<String> linhas) {
		boolean continua = true;

		
		
		while (continua) {
			for (String linha : linhas) {
				
				if (linha.replaceAll("\\s", "").length() == 0) { continue; } // pula linha vazia

				String[] palavras = linha.split(" ");
				
				
				List<Token> linha_com_token;

				for (String palavra : palavras) {

					System.out.println(" --" + palavra + "-- ");
					palavra = palavra.replaceAll("\\s", "");

					if (isOperadorLogico(palavra)) {
						System.out.println("Operador Logico: " + palavra);
					} else if (isReservada(palavra)) {
						System.out.println("Palavra Reservada: " + palavra);
					} else if (isOperadorAritmetico(palavra)) {
						System.out.println("Operador Aritmetico: " + palavra);
					} else if (isAtribuicao(palavra)) {
						System.out.println("Atribuicao: " + palavra);
					} else if (Character.isDigit(palavra.charAt(0))) {
						System.out.println("N�mero: " + palavra);
					} else if (Character.isLetter(palavra.charAt(0))) {
						System.out.println("Identificador: " + palavra);
					} else if (isPrimitivo(palavra)) {
						System.out.println("Palavra Primitiva: " + palavra);
					} else {
						System.err.println("Palavra n�o encontrada: " + palavra);
					}
					
					// CRIANDO OBJ TOKEN, SALVANDO NO LIST
				}
				System.out.println("");
	
				// VERIFICAR SINTAXE DA LINHA
			}
			continua = false;
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