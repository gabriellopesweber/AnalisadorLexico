import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Principal {

	static Identificador a = new Identificador();
	static String tipo_erro;
	
	public static void main(String[] args) throws IOException {
		List<String> linhas = Files.readAllLines(Paths.get("teste.txt"), Charset.defaultCharset());
		percorreTexto(linhas);
	}

	private static void percorreTexto(List<String> linhas) {
		boolean continua = true;

		int cont = 1;
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
					} else if (isString(palavra)) {
						token.setTipo(a.STRING);
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
				System.out.println(linhaToString(linha_com_token) + "\n");

//				if (!isDeclaracao(linha_com_token) && !isAtribuicao(linha_com_token) && !isCondicao(linha_com_token)) {
//					System.err.println("Erro: "+tipo_erro+" na linha " + linha_com_token.get(0).getLinha() + "\n");
//				}
				boolean achou_funcao = false;
				
				if(linha_com_token.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_PRIMITIVA)){
					if (!isDeclaracao(linha_com_token)) {
						System.err.println("Erro: "+tipo_erro+" na linha " + linha_com_token.get(0).getLinha() + "\n");
						continue;
					}else {
						achou_funcao = true;
					}
				}
				
				
				
				if(linha_com_token.get(0).getTipo().equalsIgnoreCase(a.IDENTIFICADOR) && !achou_funcao && !isAtribuicao(linha_com_token)) {
					System.err.println("Erro: "+tipo_erro+" na linha " + linha_com_token.get(0).getLinha() + "\n");
					continue;
				}else {
					achou_funcao = true;
				}
				

				if(linha_com_token.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA) && !achou_funcao && !isCondicao(linha_com_token)) {
					System.err.println("Erro: "+tipo_erro+" na linha " + linha_com_token.get(0).getLinha() + "\n");
					continue;
				}else {
					achou_funcao = true;
				}
				


			}

			continua = false;
		}
	}

	private static String linhaToString(ArrayList<Token> linha) {
		String str = "Linha: " + linha.get(0).getLinha() + "\n	";
		for (Token elemento : linha) {
			str += elemento.getTexto() + " <=[" + elemento.getTipo() + "]\n	";
		}
		return str;
	}

	// Esta funcao realiza a analise da sintaxe do IF, onde pode ocorrer o cenario
	// if ( a == b ) {
	private static boolean isCondicao(ArrayList<Token> elemento) {

		if (elemento.size() > 1) {
			if (elemento.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
					&& (elemento.get(1).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
							|| elemento.get(1).getTexto().equalsIgnoreCase("("))
					&& (elemento.get(elemento.size() - 2).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
							|| elemento.get(elemento.size() - 2).getTexto().equalsIgnoreCase(")"))
					&& (elemento.get(elemento.size() - 1).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
							|| elemento.get(elemento.size() - 1).getTexto().equalsIgnoreCase("{"))) {
				if (elemento.get(2).getTipo().equalsIgnoreCase(a.IDENTIFICADOR)
						&& elemento.get(3).getTipo().equalsIgnoreCase(a.OPERADOR_LOGICO)
						&& (elemento.get(4).getTipo().equalsIgnoreCase(a.IDENTIFICADOR) ||
								elemento.get(4).getTipo().equalsIgnoreCase(a.NUMERO) ||
								elemento.get(4).getTipo().equalsIgnoreCase(a.STRING))) {
					return true;
				} else {
					tipo_erro = "condicao invalida";
					return false;
				}

			}

		}
		return false;

	}

	private static boolean isAtribuicao(ArrayList<Token> elemento) { // Pensar num metodo mais composto, pois, caso
																		// tenha a inserção de mais uma variavel, pode
																		// gerar erro. Exemplo a = a + 2 + 2 + 2 + 2 ;
		if (elemento.size() >= 4) {
			if (elemento.get(0).getTipo().equalsIgnoreCase(a.IDENTIFICADOR)
					&& elemento.get(1).getTipo().equalsIgnoreCase(a.ATRIBUICAO)
					&& (elemento.get(2).getTipo().equalsIgnoreCase(a.NUMERO)
							|| elemento.get(2).getTipo().equalsIgnoreCase(a.STRING))
					&& elemento.get(3).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
					&& elemento.get(3).getTexto().equalsIgnoreCase(";")) {
				return true;
			}
		}
		tipo_erro = "atribuicao invalida";
		return false;
	}

	private static boolean isDeclaracao(ArrayList<Token> elemento) {
		try {
			if (elemento.size() >=5) {
				if (elemento.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_PRIMITIVA)
						&& elemento.get(1).getTipo().equalsIgnoreCase(a.IDENTIFICADOR)
						&& elemento.get(2).getTipo().equalsIgnoreCase(a.ATRIBUICAO)
						&& (elemento.get(3).getTipo().equalsIgnoreCase(a.NUMERO)
								|| elemento.get(3).getTipo().equalsIgnoreCase(a.STRING)
							)
						&& elemento.get(4).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
						&& elemento.get(4).getTexto().equalsIgnoreCase(";")
					) {
					return true;
				}
			}
			tipo_erro = "declaracao invalida";
			return false;
		} catch (Exception e) {
			tipo_erro = "declaracao invalida";
			return false;
		}
	}

	// verifica se é uma palavra reservada
	private static boolean isReservada(String palavra) {

		if (palavra.equalsIgnoreCase("if") || palavra.equalsIgnoreCase("for") || palavra.equalsIgnoreCase("while")
				|| palavra.equalsIgnoreCase(";") || palavra.equalsIgnoreCase("{") || palavra.equalsIgnoreCase("}")
				|| palavra.equalsIgnoreCase("[") || palavra.equalsIgnoreCase("]") || palavra.equalsIgnoreCase("(")
				|| palavra.equalsIgnoreCase(")") || palavra.equalsIgnoreCase("return") || palavra.equalsIgnoreCase("do")
				|| palavra.equalsIgnoreCase("foreach")) {
			return true;
		}
		return false;
	}

	private static boolean isPrimitivo(String palavra) {
		if (palavra.equalsIgnoreCase("float") || palavra.equalsIgnoreCase("int") || palavra.equalsIgnoreCase("string")
				|| palavra.equalsIgnoreCase("double") || palavra.equalsIgnoreCase("boolean")) {
			return true;
		}
		return false;
	}

	private static boolean isOperadorLogico(String palavra) {
		if (palavra.equalsIgnoreCase("<") || palavra.equalsIgnoreCase(">") || palavra.equalsIgnoreCase("=>")
				|| palavra.equalsIgnoreCase("<=") || palavra.equalsIgnoreCase("==") || palavra.equalsIgnoreCase("&&")
				|| palavra.equalsIgnoreCase("||")) {
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

	public static boolean isString(String palavra) {
		boolean pri = false;
		boolean ult = false;
		for (int i = 0, n = palavra.length(); i < n; i++) {
			char c = palavra.charAt(i);
			if (c == '"') {
				if (i == 0) {
					pri = true;
				} else if (i == (n - 1)) {
					ult = true;
				}
			}
		}
		if (pri == true && ult == true) {
			return true;
		} else {
			return false;
		}

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