package br.com.comex.v1.thiago.contabil.dto;

import java.util.UUID;

import lombok.Data;

@Data
public class LancamentoResponse {

	public LancamentoResponse(UUID id) {
		this.id = id.toString();
	}
	
	private String id;
	
}
