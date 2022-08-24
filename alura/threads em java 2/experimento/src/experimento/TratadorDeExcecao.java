package experimento;

import java.lang.Thread.UncaughtExceptionHandler;

public class TratadorDeExcecao implements UncaughtExceptionHandler {

	@Override
	public void uncaughtException(Thread thread, Throwable throwable) {
		System.out.println("Deu excecao na thread "+ thread.getName() + ", " + throwable.getMessage() );
	}

}
