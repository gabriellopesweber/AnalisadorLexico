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

				if (linha.replaceAll("\\s", "").length() == 0) { // pula linha vazia
					continue;
				} 

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

				boolean achou_funcao = false;

				
				
				if (linha_com_token.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_PRIMITIVA)) {
					if (!isDeclaracao(linha_com_token)) {
						System.err.println(
								"Erro: " + tipo_erro + " na linha " + linha_com_token.get(0).getLinha() + "\n");
						continue;
					} else {
						achou_funcao = true;
					}
				}	
				
				
				if (linha_com_token.get(0).getTipo().equalsIgnoreCase(a.IDENTIFICADOR)) {
					if (!achou_funcao && !isAtribuicao(linha_com_token)) {
						System.err.println("Erro: " + tipo_erro + " na linha " + linha_com_token.get(0).getLinha());
						continue;
					}else {
						achou_funcao = true;
					}
				}
	
				
				System.out.println("achou_funcao "+achou_funcao);
				
				if(linha_com_token.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)){
										
					if(linha_com_token.get(0).getTexto().equalsIgnoreCase("if") && !achou_funcao
							&& !isCondicao(linha_com_token)) {
						System.err.println("Erro: " + tipo_erro + " na linha " + linha_com_token.get(0).getLinha() + "\n");
						continue;
					}else if(linha_com_token.get(0).getTexto().equalsIgnoreCase("for") 
							&& !achou_funcao && !isLoop(linha_com_token)){
						System.err.println("Erro: " + tipo_erro + " na linha " + linha_com_token.get(0).getLinha() + "\n");
						continue;
					}else {
						achou_funcao = true;
					}
				}
				System.out.println("--------------------------------\n");
				
				
				
				
				
				
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

	// Funcao responsavel pela analise do ArrayList passado por parametro, se a
	// operacao executada é uma atribuicao correta.
	private static boolean isCondicao(ArrayList<Token> elemento) {
		System.out.println(" -- isCondicao.");

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
						&& (elemento.get(4).getTipo().equalsIgnoreCase(a.IDENTIFICADOR)
								|| elemento.get(4).getTipo().equalsIgnoreCase(a.NUMERO)
								|| elemento.get(4).getTipo().equalsIgnoreCase(a.STRING))) {
					return true;
				} else {
					tipo_erro = "condicao invalida";
					return false;
				}

			}

		}
		return false;

	}

	// Funcao responsavel pela analise do ArrayList passado por parametro, se a
	  // operacao executada é um loop correto.
	  private static boolean isLoop(ArrayList<Token> elemento) { // Falta ajustar as possições e terminar a logica.
	    System.out.println(" -- Analisando loop.");
	    if (elemento.size() > 1 && elemento.size() <= 11) {
	      if (elemento.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
	          && (elemento.get(1).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
	              || elemento.get(1).getTexto().equalsIgnoreCase("("))
	          && (elemento.get(elemento.size() - 2).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
	              || elemento.get(elemento.size() - 2).getTexto().equalsIgnoreCase(")"))
	          && (elemento.get(elemento.size() - 1).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
	              || elemento.get(elemento.size() - 1).getTexto().equalsIgnoreCase("{"))) {
	        return true;
	      } else {
	        tipo_erro = "condicao invalida";
	        return false;

	      }
	    }
	    return false;

	  }

	// Funcao responsavel pela analise do ArrayList passado por parametro, se a
	// operacao executada é uma atribuicao correta.
	private static boolean isAtribuicao(ArrayList<Token> elemento) {
		System.out.println(" -- isAtribuicao.");
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

	// Funcao responsavel pela analise do ArrayList passado por parametro, se a
	// operacao executada é uma declaracao correta.
	private static boolean isDeclaracao(ArrayList<Token> elemento) {
		System.out.println(" -- isDeclaracao.");

		try {
			if (elemento.size() >= 5) {
				if (elemento.get(0).getTipo().equalsIgnoreCase(a.PALAVRA_PRIMITIVA)
						&& elemento.get(1).getTipo().equalsIgnoreCase(a.IDENTIFICADOR)
						&& elemento.get(2).getTipo().equalsIgnoreCase(a.ATRIBUICAO)
						&& (elemento.get(3).getTipo().equalsIgnoreCase(a.NUMERO)
								|| elemento.get(3).getTipo().equalsIgnoreCase(a.STRING))
						&& elemento.get(4).getTipo().equalsIgnoreCase(a.PALAVRA_RESERVADA)
						&& elemento.get(4).getTexto().equalsIgnoreCase(";")) {
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

	// Funcao responsavel pela analise da palavra passada por parametro, se é uma
	// palavra reservada.
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

	// Funcao responsavel pela analise da palavra passada por parametro, se é um
	// valor primitivo.
	private static boolean isPrimitivo(String palavra) {
		if (palavra.equalsIgnoreCase("float") || palavra.equalsIgnoreCase("int") || palavra.equalsIgnoreCase("string")
				|| palavra.equalsIgnoreCase("double") || palavra.equalsIgnoreCase("boolean")) {
			return true;
		}
		return false;
	}

	// Funcao responsavel pela analise da palavra passada por parametro, se é um
	// operador logico.
	private static boolean isOperadorLogico(String palavra) {
		if (palavra.equalsIgnoreCase("<") || palavra.equalsIgnoreCase(">") || palavra.equalsIgnoreCase("=>")
				|| palavra.equalsIgnoreCase("<=") || palavra.equalsIgnoreCase("==") || palavra.equalsIgnoreCase("&&")
				|| palavra.equalsIgnoreCase("||") || palavra.equalsIgnoreCase("!=")) {
			return true;
		}
		return false;
	}

	// Funcao responsavel pela analise da palavra passada por parametro, se é uma
	// atribuicao.
	public static boolean isAtribuicao(String palavra) {
		if (palavra.equalsIgnoreCase("=")) {
			return true;
		}
		return false;
	}

	// Funcao responsavel pela analise da palavra passada por parametro, se é uma
	// String.
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

	// Funcao responsavel pela analise da palavra passada por parametro, se é um
	// operador aritimetico.
	public static boolean isOperadorAritmetico(String palavra) {
		if (palavra.equalsIgnoreCase("+") || palavra.equalsIgnoreCase("-") || palavra.equalsIgnoreCase("/")
				|| palavra.equalsIgnoreCase("*")) {
			return true;
		}
		return false;
	}

}