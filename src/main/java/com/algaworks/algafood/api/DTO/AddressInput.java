package com.algaworks.algafood.api.DTO;

import jakarta.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class AddressInput {

	@NotBlank
	private String cep;
	
	@NotBlank
	private String numero;

	private String complemento;
	
	@NotBlank
	private String bairro;
	
	@NotBlank
	private String cidade;
}
