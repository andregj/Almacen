package AGJ.prAlmacen;

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AlmacenOrinocoTest {
  ListaPedidos listaPedidos1 = new ListaPedidos(50);
  
  @Before
  public void setUp() throws Exception {}

  @After
  public void tearDown() throws Exception {}

  @Test
  public void siSeGeneraUnPedidoEnEsperaElEspacioLibreDeLaListaDisminuyeEnUno() {
   Pedido pedido1= new Pedido(listaPedidos1.buscarEntradaLibre());
   listaPedidos1.generarPedidoEnEspera();
  assertEquals(listaPedidos1.maxPedidosTotal()-pedido1.getId(), listaPedidos1.maxPedidosTotal()-listaPedidos1.buscarEntradaLibre()+1);
   
  }

  
  
}
