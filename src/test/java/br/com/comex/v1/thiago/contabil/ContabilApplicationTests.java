package br.com.comex.v1.thiago.contabil;

import static org.junit.Assert.assertNotNull;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.comex.v1.thiago.contabil.model.Lancamento;
import br.com.comex.v1.thiago.contabil.service.ContabilService;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ContabilApplicationTests {

	@InjectMocks ContabilService contabilService;
	
	@Test
	public void addTestSucesso() {
	
        // define return value for method getUniqueId()
        //when(test.getUniqueId()).thenReturn(43);
//		Lancamento lancamento = new Lancamento(1L,20181001L,111.22D);
//		UUID id = contabilService.add(lancamento);
//        // use mock in test....
//        assertNotNull(id);
//	
		}
//
//	@Test
//	public void multipleAddTestSucesso() {
//	
//        // define return value for method getUniqueId()
//        //when(test.getUniqueId()).thenReturn(43);
//		Lancamento lancamento = new Lancamento(1L,20181001L,111.22D);
//		contabilService.add(lancamento);
//		contabilService.add(lancamento);
//		contabilService.add(lancamento);
//		
//		D
//        // use mock in test....
//        assertNotNull(id);
//	}
//
}

