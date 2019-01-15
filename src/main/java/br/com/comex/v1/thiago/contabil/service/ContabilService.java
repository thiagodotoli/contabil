package br.com.comex.v1.thiago.contabil.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Service;

import br.com.comex.v1.thiago.contabil.dto.StatsDTO;
import br.com.comex.v1.thiago.contabil.exception.LancamentoException;
import br.com.comex.v1.thiago.contabil.exception.NotFoundException;
import br.com.comex.v1.thiago.contabil.model.Lancamento;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ContabilService {

	private static Map<UUID,Lancamento> DB_LANCAMENTO = new HashMap<UUID,Lancamento>();

	public static UUID add(Lancamento lancamento) {
		log.info("DbContabil - Validando data");
		Matcher matcher = Pattern.compile("([12]\\d{3}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01]))").matcher(lancamento.getData().toString());
		
		
		if(!matcher.matches())
			throw new LancamentoException("Formato da data inválido");
			
		UUID id = UUID.randomUUID();
		log.info("DbContabil - Adicionando lançamento com UUID: {}",id.toString());
		DB_LANCAMENTO.put(id, lancamento);
		
		log.info("DbContabil - Processo finalizado");
		return id;
	}
	
	public static Lancamento findById(UUID id) {
		return DB_LANCAMENTO.get(id);
	}
	
	public static List<Lancamento> findByContaContabil(Long contaContabil) {
		Collection<Lancamento> lancamentos = DB_LANCAMENTO.values();

		return lancamentos.stream().filter(p -> p.getContaContabil()==contaContabil).collect(Collectors.toList());
	}

	public static StatsDTO stats() {
		StatsDTO satsDTO = new StatsDTO();

		if(DB_LANCAMENTO.values()==null || DB_LANCAMENTO.values().size()==0)
			return satsDTO;

		satsDTO.setSoma(DB_LANCAMENTO.values().stream().mapToDouble(i -> i.getValor()).sum());
		satsDTO.setMax(DB_LANCAMENTO.values().stream().mapToDouble(i -> i.getValor()).max().getAsDouble());
		satsDTO.setMin(DB_LANCAMENTO.values().stream().mapToDouble(i -> i.getValor()).min().getAsDouble());
		satsDTO.setMedia(DB_LANCAMENTO.values().stream().mapToDouble(i -> i.getValor()).average().getAsDouble());
		satsDTO.setQtde((int) DB_LANCAMENTO.values().stream().count());

		return satsDTO;
	}

	public static StatsDTO statsByContaContabil(@NotNull Long contaContabil) {
		StatsDTO satsDTO = new StatsDTO();

		if(DB_LANCAMENTO.values()==null || DB_LANCAMENTO.values().size()==0)
			return satsDTO;
			
		try {
			satsDTO.setSoma(DB_LANCAMENTO.values().stream().filter(p->p.getContaContabil()==contaContabil).mapToDouble(i -> i.getValor()).sum());
			satsDTO.setMax(DB_LANCAMENTO.values().stream().filter(p->p.getContaContabil()==contaContabil).mapToDouble(i -> i.getValor()).max().getAsDouble());
			satsDTO.setMin(DB_LANCAMENTO.values().stream().filter(p->p.getContaContabil()==contaContabil).mapToDouble(i -> i.getValor()).min().getAsDouble());
			satsDTO.setMedia(DB_LANCAMENTO.values().stream().filter(p->p.getContaContabil()==contaContabil).mapToDouble(i -> i.getValor()).average().getAsDouble());
			satsDTO.setQtde((int) DB_LANCAMENTO.values().stream().filter(p->p.getContaContabil()==contaContabil).count());
		} catch (NoSuchElementException e) {
			throw new NotFoundException("Conta contábil não existe");
		}
			
		return satsDTO;
	}


	private static void validFormat(Long value) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            date = sdf.parse(value.toString());
            if (!value.equals(sdf.format(date))) {
                date = null;
            }
        } catch (ParseException ex) {
            throw new LancamentoException("O formato da data deve ser YYYYMMDD",ex);
        }
        if(date == null)
        	throw new LancamentoException("O formato da data deve ser YYYYMMDD");
    }
}
