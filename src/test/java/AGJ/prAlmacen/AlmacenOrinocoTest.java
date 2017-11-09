package AGJ.prAlmacen;

import static org.junit.Assert.*;
import static org.junit.Assert.assertThat;
import org.hamcrest.core.Every;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.BDDMockito.Then;
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
    assertEquals(listaPedidos1.maxPedidosTotal() - pedido1.getId(),
        listaPedidos1.maxPedidosTotal() - 1);

  }

  @Test
  public void siSeGeneraUnPedidoEnEsperaYLaListaEstaVaciaSuIdentificadorEsCero() {
    Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
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

  @Test
  public void  siSePasaUnPedidoADistribucionYSeEliminaEntoncesSeHaLiberadoAlAgenteDistribuidor() {
    Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
    when(AgenteD1.hayAgenteDisponible()).thenReturn(true);
    listaPedidos1.pasarPedidoADistribucion(pedido1.getId(), AgenteD1);
    listaPedidos1.eliminaPedido(pedido1.getId(), AgenteD1);
    verify(AgenteD1, times(1)).liberarAgente();
  }
    @Test 
    public void siSeEliminaUnPedidoEnEsperaNoSeLiberaNingunAgenteDistribuidor() {
    Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
    when(AgenteD1.hayAgenteDisponible()).thenReturn(false);
    listaPedidos1.eliminaPedido(pedido1.getId(), AgenteD1);
    verify(AgenteD1, times(0)).liberarAgente();
  }

    @Test (expected = ListaPedidosExcepcion.class)
    public void siSeBuscaUnPedidoQueSeHaEliminadoSeElevaUnaExcepcion() {
      Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
      when(AgenteD1.hayAgenteDisponible()).thenReturn(false);
      listaPedidos1.eliminaPedido(pedido1.getId(), AgenteD1);
      listaPedidos1.buscaPedido(pedido1.getId());
    }
    @Test (expected = ListaPedidosExcepcion.class)
    public void siSeEliminaPedidoYNoExisteSeElevaUnaExcepcion() {
      Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
      when(AgenteD1.hayAgenteDisponible()).thenReturn(false);
      listaPedidos1.eliminaPedido(pedido1.getId()+1, AgenteD1);
    }
    @Test 
    public void siSeEliminaPedidoExistenteEntoncesSuIdentificadorQuedaLibre() {
      Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
      listaPedidos1.eliminaPedido(pedido1.getId(), AgenteD1);
     assertEquals(0, listaPedidos1.buscarEntradaLibre());
    }
    @Test 
    public void siSePasaUnPedidoADistribucionYHayAgentesEntoncesSeSolicitaAgente() {
      Pedido pedido1 = listaPedidos1.generarPedidoEnEspera();
      when(AgenteD1.hayAgenteDisponible()).thenReturn(true);
      verify(AgenteD1, times(0)).solicitarAgente();;
    }

}
