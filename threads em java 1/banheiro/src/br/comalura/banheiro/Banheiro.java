package br.comalura.banheiro;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banheiro {
	
	private boolean ehSujo = true;
	
	//private Lock lock = new ReentrantLock();

	public void fazNumero1() {

		String nome = Thread.currentThread().getName();

		System.out.println(nome + " batendo na porta");
		
		//lock.lock();
		
		synchronized (this) {
			System.out.println(nome + " entrado no banheiro");
			
			while(ehSujo) {
				esperaLaFora(nome);
			}
			
			System.out.println(nome + " fazendo coisa rapida");

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.ehSujo = true;

			System.out.println(nome + " dando descarga");
			System.out.println(nome + " lavando mao");
			System.out.println(nome + " saindo do banheiro");
		}
		//lock.unlock();
	}

	private void esperaLaFora(String nome) {
		try {
			System.out.println(nome + ", eca, banheiro tá sujo");
			this.wait();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void limpa() {
		String nome = Thread.currentThread().getName();

		System.out.println(nome + " batendo na porta");
		
		//lock.lock();
		synchronized (this) {
			System.out.println(nome + " entrado no banheiro");
			
			if(!ehSujo) {
				System.out.println(nome + ", não está sujo, vou sair");
				return;
			}
			
			System.out.println(nome + " limpando banheiro");

			this.ehSujo = false;
			
			try {
				Thread.sleep(13000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.notifyAll();

			System.out.println(nome + " saindo do banheiro");
		}
		
	}

	public void fazNumero2() {
		String nome = Thread.currentThread().getName();

		System.out.println(nome + " batendo na porta");
		
		//lock.lock();
		synchronized (this) {
			System.out.println(nome + " entrado no banheiro");
			
			while(ehSujo) {
				esperaLaFora(nome);
			};
 			System.out.println(nome + " fazendo coisa demorada");

			try {
				Thread.sleep(10000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			this.ehSujo = true;

			System.out.println(nome + " dando descarga");
			System.out.println(nome + " lavando mao");
			System.out.println(nome + " saindo do banheiro");
		}
		//lock.unlock();
	}

}
