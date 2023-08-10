package com.pivo.hotelo.api.DTO.output;

import java.util.List;

import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;


@Relation(collectionRelation = "hoteis")
@Getter
@Setter
public class HotelOutput extends RepresentationModel<HotelOutput>{

	@Schema(example = "HotelA")
	private String nome;
	
	@Schema(example = "Perto do centro comercial")
	private String descricao;
	
	private AddressOutput endereco;
	
	private List<RoomOutput> quartos;
}
