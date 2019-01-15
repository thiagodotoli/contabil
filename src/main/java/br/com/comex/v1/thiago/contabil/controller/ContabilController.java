package br.com.comex.v1.thiago.contabil.controller;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.comex.v1.thiago.contabil.dto.StatsDTO;
import br.com.comex.v1.thiago.contabil.model.Lancamento;
import br.com.comex.v1.thiago.contabil.model.LancamentoResponse;
import br.com.comex.v1.thiago.contabil.service.ContabilService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
public class ContabilController {

	@PostMapping("/lancamentos-contabeis")
	public ResponseEntity<LancamentoResponse> lancamentosContabeis(@Valid @RequestBody Lancamento lancamento) {

		UUID id = ContabilService.add(lancamento);

		LancamentoResponse lancamentoResponse = new LancamentoResponse();
		lancamentoResponse.setId(id.toString());
		return new ResponseEntity<>(lancamentoResponse, HttpStatus.CREATED);
	}

	@GetMapping("/lancamentos-contabeis/{uuid}")
	public ResponseEntity<Lancamento> lancamentoPorId(@NotNull @PathVariable("uuid") UUID id) {
		log.info("Busca de lançamento contábil por id {}",id.toString());
		Lancamento lancamento = ContabilService.findById(id);
		if(lancamento==null) {
			log.error("Lançamento não localizado");
			return new ResponseEntity<>(lancamento, HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(lancamento);
	}

	@GetMapping("/lancamentos-contabeis/")
	public ResponseEntity<List<Lancamento>> lancamentosPorContaContabil(@RequestParam(value="contaContabil",required=true) Long contaContabil) {
		return ResponseEntity.ok(ContabilService.findByContaContabil(contaContabil));
	}

	@GetMapping("/lancamentos-contabeis/_stats")
	public ResponseEntity<StatsDTO> stats(@RequestParam(value="contaContabil",required=false) Long contaContabil) {
		StatsDTO statsDTO = (contaContabil!=null) ? ContabilService.statsByContaContabil(contaContabil) : ContabilService.stats();
		return ResponseEntity.ok(statsDTO);
	}

}
