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
		boolean continua=true;
		
		while(continua) {
			for (String linha : linhas) {
				for (int i=0; i<linha.length(); i++) {
				   char caracter = linha.charAt(i);
				   
				   if(Character.isLetter(caracter)) {
					   System.out.println("letra: "+caracter);
				   }
					if(Character.isDigit(caracter)) {
						   System.out.println("digito: "+caracter);
				   }
				  
				}
			}
			continua=false;
		}
	}
	
}
