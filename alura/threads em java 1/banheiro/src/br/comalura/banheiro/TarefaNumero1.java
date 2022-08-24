package br.comalura.banheiro;

public class TarefaNumero1 implements Runnable {

	private Banheiro banheiro;
	
	@Override
	public void run() {
		banheiro.fazNumero1();
	}

	public TarefaNumero1(Banheiro banheiro) {
		this.banheiro = banheiro;
	}

}
