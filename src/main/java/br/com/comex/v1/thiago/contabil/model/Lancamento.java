package br.com.comex.v1.thiago.contabil.model;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class Lancamento implements Serializable {

	private static final long serialVersionUID = -2711063964643152887L;

	@NotNull(message = "Conta Contábil é obrigatório")
	private Long contaContabil;

	@NotNull(message = "Data é obrigatório")
	private Long data;
	
	@NotNull(message = "Valor é obrigatório")
	private double valor;

}
