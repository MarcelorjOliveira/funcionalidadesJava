package br.com.alura.banco;

public class TarefaAcessoProcedimento implements Runnable {

	private PoolDeConexao pool;
	private GerenciadorDeTransacao tx;

	public TarefaAcessoProcedimento(PoolDeConexao pool, GerenciadorDeTransacao tx) {
		this.pool = pool;
		this.tx = tx;
	}

	@Override
	public void run() {

		synchronized (pool) {
			System.out.println("peguei a conexao");
			pool.getConnection();

			synchronized (tx) {
				System.out.println("comecando a tx");

				tx.begin();

			}
		}

	}

}
