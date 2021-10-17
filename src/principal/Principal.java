package principal;

import javax.swing.JOptionPane;

public class Principal {

	public static void main(String[] args) {
		
		String texto = JOptionPane.showInputDialog(null, "Digite aqui: ");
		
		System.out.println("texto: "+texto);
	}
}
