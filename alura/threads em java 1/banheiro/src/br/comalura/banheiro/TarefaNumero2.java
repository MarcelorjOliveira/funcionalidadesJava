package br.comalura.banheiro;

public class TarefaNumero2 implements Runnable {

	private Banheiro banheiro;
	
	@Override
	public void run() {
		banheiro.fazNumero2();
	}

	public TarefaNumero2(Banheiro banheiro) {
		this.banheiro = banheiro;
	}

}
