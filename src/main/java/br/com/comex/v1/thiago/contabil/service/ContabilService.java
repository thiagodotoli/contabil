package br.com.comex.v1.thiago.contabil.service;

import java.util.Collection;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.comex.v1.thiago.contabil.dto.LancamentoResponse;
import br.com.comex.v1.thiago.contabil.dto.StatsDTO;
import br.com.comex.v1.thiago.contabil.exception.LancamentoException;
import br.com.comex.v1.thiago.contabil.model.Lancamento;
import br.com.comex.v1.thiago.contabil.repository.ContabilRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContabilService {

	@Autowired
	private ContabilRepository contabilRepository;
	
	public LancamentoResponse add(Lancamento lancamento) {
		log.info("Validando data");
		Matcher matcher = Pattern.compile("([12]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))").matcher(lancamento.getData().toString());
		
		if(!matcher.matches())
			throw new LancamentoException("Formato da data inválido");
		
		UUID id = UUID.randomUUID();
		try {
			log.info("Adicionando lançamento com UUID: {}",id.toString());
			
			lancamento.setId(id.toString());
			contabilRepository.save(lancamento);  
		} catch (Exception e) {
			log.error(e.getMessage());
			throw e;
		}
		log.info("Processo finalizado");
		return new LancamentoResponse(id);
	}
	
	public Lancamento findById(UUID id) {
		return contabilRepository.findById(id.toString()).get();
	
	}
	
	public List<Lancamento> findByContaContabil(Long contaContabil) {
		Collection<Lancamento> lancamentos = contabilRepository.findByContaContabil(contaContabil);

		return lancamentos.stream().filter(p -> p.getContaContabil()==contaContabil).collect(Collectors.toList());
	}

	public StatsDTO stats(Long contaContabil) {
		StatsDTO satsDTO = new StatsDTO();
		
		Collection<Lancamento> lancamentos = null;
		if(contaContabil==null)
			lancamentos = contabilRepository.findAll();
		else
			lancamentos = contabilRepository.findByContaContabil(contaContabil);

		if(lancamentos==null || lancamentos.size()==0)
			return satsDTO;

		satsDTO.setSoma(lancamentos.stream().mapToDouble(i -> i.getValor()).sum());
		satsDTO.setMax(lancamentos.stream().mapToDouble(i -> i.getValor()).max().getAsDouble());
		satsDTO.setMin(lancamentos.stream().mapToDouble(i -> i.getValor()).min().getAsDouble());
		satsDTO.setMedia(lancamentos.stream().mapToDouble(i -> i.getValor()).average().getAsDouble());
		satsDTO.setQtde(lancamentos.size());

		return satsDTO;
	}

}
