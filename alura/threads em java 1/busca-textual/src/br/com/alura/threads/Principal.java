package br.com.alura.threads;

public class Principal {
	
	public static void main(String[] args) {

		String nome = "Jon";
		
		Thread threadAssinaturas1 = new Thread(new TarefaBuscaTextual("assinaturas1.txt", nome.toLowerCase()));
		Thread threadAssinaturas2 = new Thread(new TarefaBuscaTextual("assinaturas2.txt", nome.toLowerCase()));
		Thread threadAutores = new Thread(new TarefaBuscaTextual("autores.txt", nome.toLowerCase()));
		
		threadAssinaturas1.start();
		threadAssinaturas2.start();
		threadAutores.start();
		
	}

}
