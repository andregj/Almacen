package AGJ.prAlmacen;

import static org.junit.Assert.*;
import org.hamcrest.core.Every;
import static org.hamcrest.Matchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AlmacenOrinocoTest {
  ListaPedidos listaPedidos1 = new ListaPedidos(50);
  
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
	  Pedido pedido1= listaPedidos1.generarPedidoEnEspera();
	  System.out.println(pedido1.getId());
  assertEquals(listaPedidos1.maxPedidosTotal()-pedido1.getId(), listaPedidos1.maxPedidosTotal());
   
  }
  
  @Test
  public void siSeGeneraUnPedidoEnEsperaYLaListaEstaVaciaSuIdentificadorEsCero() {
	 Pedido pedido1= listaPedidos1.generarPedidoEnEspera();
	 listaPedidos1.generarPedidoEnEspera();
	 assertEquals(pedido1.getId(), 0);
  }  
  
  @Test (expected = ListaPedidosExcepcion.class)
  public void siSeGeneraUnPedidoEnEsperaYLaListaEstaLlenaSeElevaUnaExcepcion() {
	 listaPedidos1 = new ListaPedidos(0);
	  Pedido pedido1= listaPedidos1.generarPedidoEnEspera();
}
  
}