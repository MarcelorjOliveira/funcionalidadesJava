package br.com.alura.microservice.loja.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.alura.microservice.loja.client.FornecedorClient;
import br.com.alura.microservice.loja.controller.dto.CompraDTO;
import br.com.alura.microservice.loja.controller.repository.CompraRepository;
import br.com.alura.microservice.loja.dto.InfoFornecedorDTO;
import br.com.alura.microservice.loja.dto.InfoPedidoDTO;
import br.com.alura.microservice.loja.model.Compra;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CompraService {

	@Autowired
	private FornecedorClient fornecedorClient;
	
	@Autowired
	private CompraRepository compraRepository;
	
	private static final Logger LOG = LoggerFactory.getLogger(CompraService.class);
	
	/*
	@Autowired
	private RestTemplate client;
	
	@Autowired
	private DiscoveryClient eurekaClient;
	*/

	public Compra realizaCompraFallback(CompraDTO compra, Exception e) {
		Compra compraFallback = new Compra();
		compraFallback.setEnderecoDestino(compra.getEndereco().toString());
		return compraFallback;
	}
	
	@CircuitBreaker( name = "realizaCompraCircuitBreaker", fallbackMethod = "realizaCompraFallback")
	//@TimeLimiter( name = "realizaCompraTimeLimiter", fallbackMethod = "realizaCompraFallback")
	public Compra realizaCompra(CompraDTO compra) {		
		
		final String estado = compra.getEndereco().getEstado();
		
		LOG.info("buscando informações do fornecedor de {}", estado);
		
		InfoFornecedorDTO info = fornecedorClient.getInfoPorEstado(compra.getEndereco().getEstado());
		
		LOG.info("realizando um pedido");
		
		InfoPedidoDTO pedido = fornecedorClient.realizaPedido(compra.getItens());
	
		//if(1==1) throw new RuntimeException();
		
		System.out.println(info.getEndereco());
		
		Compra compraSalva = new Compra();
		compraSalva.setPedidoId(pedido.getId());
		compraSalva.setTempoDePreparo(pedido.getTempoDePreparo());
		compraSalva.setEnderecoDestino(compra.getEndereco().toString()); 
		
		compraRepository.save(compraSalva);
		
		//try {
		//	Thread.sleep(10000);
		//} catch (InterruptedException e) {
		//	e.printStackTrace();
		//}
		
		return compraSalva;
		/*
		ResponseEntity<InfoFornecedorDTO> exchange = client.exchange("http://fornecedor/info/"+compra.getEndereco().getEstado(), HttpMethod.GET, null, InfoFornecedorDTO.class);
		
		eurekaClient.getInstances("fornecedor").stream().forEach(fornecedor -> {
			System.out.println("localhost:"+fornecedor.getPort() );
		});		
		System.out.println(exchange.getBody().getEndereco() );
		*/
	}

	public Compra getById(Long id) {
		return compraRepository.findById(id).orElse(new Compra());
	}
	
}
