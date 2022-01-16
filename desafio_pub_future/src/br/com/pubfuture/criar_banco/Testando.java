package br.com.pubfuture.criar_banco;

public class Testando {

	public static void main(String[] args) {
		Conn.start();
		//Funn.inserirConta("Pedro Figueiredo", "Banco Safra", 3);
		//Funn.inserirReceita(80.00, "03/03/2024", 4, 1);
		//Funn.inserirDespesa(50.00, "20/02/2023", 7, 1 );
		System.out.println(Funn.calcularSaldo(1));
	}

}
