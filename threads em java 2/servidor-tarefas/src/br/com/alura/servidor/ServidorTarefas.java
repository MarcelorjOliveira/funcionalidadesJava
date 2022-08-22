package br.com.alura.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServidorTarefas {

	private ExecutorService threadPool;
	private ServerSocket servidor;
	private AtomicBoolean estaRodando;

	public ServidorTarefas() throws IOException {
		System.out.println("Iniciando servidor");
		this.servidor = new ServerSocket(12345);
		this.threadPool = Executors.newFixedThreadPool(4); //newCachedThreadPool();
		this.estaRodando = new AtomicBoolean(true);
	}

	public static void main(String[] args) throws Exception {

		ServidorTarefas servidor = new ServidorTarefas();
		servidor.rodar();
		servidor.parar();

	}

	public void parar() throws IOException {
		this.estaRodando.set(false);
		servidor.close();
		threadPool.shutdown();
	}

	private void rodar() throws IOException {
		while (this.estaRodando.get()) {
			try {
				Socket socket = servidor.accept();
				System.out.println("Aceitando novo cliente na porta " + socket.getPort());
	
				DistribuirTarefas distribuirTarefas = new DistribuirTarefas(threadPool, socket, this);
				Thread threadCliente = new Thread(distribuirTarefas);
				threadCliente.start();
			} catch(SocketException e) {
				System.out.println("Socket Exceptiom, Esta rodando? "+ this.estaRodando);
			}

		}
	}

}
