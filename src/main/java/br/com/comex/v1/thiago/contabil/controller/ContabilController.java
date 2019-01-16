package br.com.comex.v1.thiago.contabil.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.comex.v1.thiago.contabil.dto.LancamentoResponse;
import br.com.comex.v1.thiago.contabil.dto.StatsDTO;
import br.com.comex.v1.thiago.contabil.model.Lancamento;
import br.com.comex.v1.thiago.contabil.service.ContabilService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/lancamentos-contabeis")
public class ContabilController {

	@Autowired
	private ContabilService contabilService;
	
	@PostMapping
	public ResponseEntity<LancamentoResponse> lancamentosContabeis(@Valid @RequestBody Lancamento lancamento) {
		try {
			LancamentoResponse lancamentoResponse = contabilService.add(lancamento);
			return new ResponseEntity<>(lancamentoResponse, HttpStatus.CREATED);
		} catch (RuntimeException e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}

	@GetMapping("/{uuid}")
	public ResponseEntity<Lancamento> lancamentoPorId(@NotNull @PathVariable("uuid") UUID id) {
		try {
			log.info("Busca de lançamento contábil por id {}",id.toString());
			Lancamento lancamento = contabilService.findById(id);
			if(lancamento==null) {
				log.error("Lançamento não localizado");
				return new ResponseEntity<>(lancamento, HttpStatus.NOT_FOUND);
			}
			return ResponseEntity.ok(lancamento);
		} catch (RuntimeException e) {
			log.error(e.getMessage(),e);
			throw e;
		}	}

	@GetMapping
	public ResponseEntity<List<Lancamento>> lancamentosPorContaContabil(@RequestParam(value="contaContabil",required=true) Long contaContabil) {
		try {
			return ResponseEntity.ok(contabilService.findByContaContabil(contaContabil));
		} catch (RuntimeException e) {
			log.error(e.getMessage(),e);
			throw e;
		}	
	}

	@GetMapping("/_stats")
	public ResponseEntity<StatsDTO> stats(@RequestParam(value="contaContabil",required=false) Long contaContabil) {
		try {
			StatsDTO statsDTO = contabilService.stats(contaContabil);
			return ResponseEntity.ok(statsDTO);
		} catch (RuntimeException e) {
			log.error(e.getMessage(),e);
			throw e;
		}
	}

}
