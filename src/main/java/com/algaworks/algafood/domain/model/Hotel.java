package com.algaworks.algafood.domain.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Data
@EqualsAndHashCode
public class Hotel {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;

	private String nome;

	private String descricao;
	
	@Embedded
	private Address endereco; //Podendo integrar com uma API de Localização 
	

	@OneToMany(mappedBy = "hotel")
	private List<Room> quartos = new ArrayList<>(); 
	
/* Atributos pendentes, Avaliação dos clientes, Classificação */
	
}
