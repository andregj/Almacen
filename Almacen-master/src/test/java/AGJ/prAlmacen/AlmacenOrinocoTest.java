package AGJ.prAlmacen;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import org.hamcrest.core.Every;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

public class AlmacenOrinocoTest {
	ListaPedidos listaPedidos1 = new ListaPedidos(50);
	IAgenteDistribuidor AgenteD1 = mock(IAgenteDistribuidor.class);

	@Before
	public void setUp() throws Exception {
		listaPedidos1 = new ListaPedidos(50);
	}

	@After
	public void tearDown() throws Exception {
		listaPedidos1 = null;
	}

	@Test
	public void siSeGeneraUnPedidoEnEsperaElEspacioLibreDeLaListaDisminuyeEnUno() {
		Pedido pedidobase = listaPedidos1.generarPedidoEnEspera();
		Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
		System.out.println(pedido1.getId());
		assertEquals(listaPedidos1.maxPedidosTotal() - pedido1.getId(), listaPedidos1.maxPedidosTotal() - 1);

	}

	@Test
	public void siSeGeneraUnPedidoEnEsperaYLaListaEstaVaciaSuIdentificadorEsCero() {
		Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
		listaPedidos1.generarPedidoEnEspera();
		assertEquals(pedido1.getId(), 0);
	}

	@Test(expected = ListaPedidosExcepcion.class)
	public void siSeGeneraUnPedidoEnEsperaYLaListaEstaLlenaSeElevaUnaExcepcion() {
		listaPedidos1 = new ListaPedidos(0);
		Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
	}
	@Test
	public void siSePasaUnPedidoADistribucionYHayAgentesEntoncesElEstadoDelProcesoEsDistribucion() {
		Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
		when(AgenteD1.hayAgenteDisponible()).thenReturn(true);
		listaPedidos1.pasarPedidoADistribucion(pedido1.getId(), AgenteD1);
		assertThat(pedido1.getEstado().toString(), containsString("DISTRIBUCION"));
	}
	
	@Test
	public void siSePasaUnPedidoADistribucionYNoHayAgentesEntoncesElEstadoDelProcesoEsEnEspera() {
		Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
		when(AgenteD1.hayAgenteDisponible()).thenReturn(false);
		listaPedidos1.pasarPedidoADistribucion(pedido1.getId(), AgenteD1);
		String ESTADO = pedido1.getEstado().toString();
		assertEquals("ESPERA", pedido1.getEstado().toString());
		assertNotEquals("DISTRIBUCION", pedido1.getEstado().toString());

	}
}