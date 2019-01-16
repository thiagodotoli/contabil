package br.com.comex.v1.thiago.contabil.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@SuppressWarnings("serial")
@Entity
public class Lancamento implements Serializable {
	
	@Id
	@JsonIgnore
	private String id;
	
	@NotNull(message = "Conta Contábil é obrigatório")
	private Long contaContabil;

	@NotNull(message = "Data é obrigatório")
	private Long data;
	
	@NotNull(message = "Valor é obrigatório")
	private double valor;

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}	
}
