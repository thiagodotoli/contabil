package br.com.comex.v1.thiago.contabil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import br.com.comex.v1.thiago.contabil.dto.LancamentoResponse;
import br.com.comex.v1.thiago.contabil.model.Lancamento;
import br.com.comex.v1.thiago.contabil.repository.ContabilRepository;
import br.com.comex.v1.thiago.contabil.service.ContabilService;

@RunWith(PowerMockRunner.class) 
@PrepareForTest({ UUID.class, ContabilService.class })
//@PrepareForTest({ ContabilService.class }) 
public class ContabilApplicationTests {

	@Mock ContabilRepository contabilRepositoryMock;
	
	private UUID uuid;

	private Lancamento lancamento;

	@InjectMocks ContabilService contabilService;

   @Before
   public void initMocks() {
		MockitoAnnotations.initMocks(this);
		uuid = UUID.fromString("493410b3-dd0b-4b78-97bf-289f50f6e74f");
		lancamento = new Lancamento();
		lancamento.setId(uuid.toString());
		lancamento.setContaContabil(1L);
		lancamento.setData(20181230L);
		lancamento.setValor(245.09);
   }
	
   @Test
   public void addSucessoTest() {
		PowerMockito.mockStatic(UUID.class);
	    PowerMockito.when(UUID.randomUUID()).thenReturn(uuid);
	    PowerMockito.when(contabilRepositoryMock.save(Mockito.any(Lancamento.class))).thenReturn(lancamento);
	    
		LancamentoResponse lancamentoResponse = contabilService.add(lancamento);
		
		Assert.assertTrue(lancamentoResponse.getId().toString().equals(uuid.toString()));
	}
   
   @Test
   public void findByIdSucessoTest() {
	    PowerMockito.when(contabilRepositoryMock.findById(Mockito.anyString())).thenReturn(Optional.of(lancamento));
	    
		Lancamento lancamentoResult = contabilService.findById(uuid);
		
		Assert.assertTrue(lancamentoResult.equals(lancamentoResult));
	}
   
   @Test
   public void findByContaContabilSucessoTest() {
		Long contaContabil = 1L;
		
		List<Lancamento> lancamentos = new ArrayList<Lancamento>();
		lancamentos.add(lancamento);
	    PowerMockito.when(contabilRepositoryMock.findByContaContabil(Mockito.anyLong())).thenReturn(lancamentos);
	    
	    List<Lancamento> lancamentosResult = contabilService.findByContaContabil(contaContabil);
		
		Assert.assertEquals(lancamentos.size(), lancamentosResult.size());
	}
}

