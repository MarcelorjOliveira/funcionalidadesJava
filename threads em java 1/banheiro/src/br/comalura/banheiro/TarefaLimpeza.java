package br.comalura.banheiro;

public class TarefaLimpeza implements Runnable {

	private Banheiro banheiro;
	
	@Override
	public void run() {
		while(true) {
			banheiro.limpa();
			
			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public TarefaLimpeza(Banheiro banheiro) {
		this.banheiro = banheiro;
	}

}
