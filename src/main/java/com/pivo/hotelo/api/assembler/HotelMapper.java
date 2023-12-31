package com.pivo.hotelo.api.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.pivo.hotelo.api.DTO.input.HotelInput;
import com.pivo.hotelo.api.DTO.output.HotelOutput;
import com.pivo.hotelo.api.controller.HotelController;
import com.pivo.hotelo.api.controller.HotelRoomController;
import com.pivo.hotelo.domain.model.Hotel;

@Component
public class HotelMapper extends RepresentationModelAssemblerSupport<Hotel, HotelOutput> {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public HotelMapper() {
		super(HotelController.class, HotelOutput.class);
	}
	
	public Hotel toDomainModel(HotelInput hotelInput) {
		return modelMapper.map(hotelInput, Hotel.class);
	}
	
	public List<Hotel> toDomainCollectionModel(List<HotelInput> list) {
		return list.stream().map((models) -> toDomainModel(models)).collect(Collectors.toList());
	}

	//---------------------------------------------------------

	
	public HotelOutput toModel(Hotel hotel) {
		HotelOutput hotelModel = createModelWithId(hotel.getId(), hotel);
		
		modelMapper.map(hotel, hotelModel );
	
		hotelModel.add(linkTo(HotelController.class).withRel("hoteis"));
		
		hotelModel.add(linkTo(methodOn(HotelRoomController.class).findRoomsOfHotel(hotel.getId(), false)).withRel("quartos"));
	
		return hotelModel;
	}
	
	@Override
	public CollectionModel<HotelOutput> toCollectionModel(Iterable<? extends Hotel> entities) {
		return super.toCollectionModel(entities)
				.add(linkTo(HotelController.class).withSelfRel());
	}

	
	//----------------------------------------------------------
	public void copyToDomain(HotelInput hotelInput, Hotel hotel) {	 
		modelMapper.map(hotelInput,hotel);
	}

}
	

