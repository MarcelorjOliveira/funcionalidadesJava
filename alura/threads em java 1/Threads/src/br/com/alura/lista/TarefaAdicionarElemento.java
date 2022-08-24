package br.com.alura.lista;

import java.util.List;

public class TarefaAdicionarElemento implements Runnable {

	//private List<String> lista;
	private Lista lista;
	
	private int numeroDoThread;	
	public TarefaAdicionarElemento(Lista lista /* List<String> lista */, int numeroDoThread) {
		this.lista = lista;
		this.numeroDoThread = numeroDoThread;
	}

	@Override
	public void run() {
		for (int i=0; i < 100; i++) {
			lista.adicionaElementos("Thread " + numeroDoThread + " - " + i);
		}
	}

}
