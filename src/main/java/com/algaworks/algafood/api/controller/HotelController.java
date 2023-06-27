package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.DTO.HotelInput;
import com.algaworks.algafood.api.DTO.HotelOneOutput;
import com.algaworks.algafood.api.DTO.HotelOutput;
import com.algaworks.algafood.api.assembler.HotelIMapper;
import com.algaworks.algafood.domain.model.Hotel;
import com.algaworks.algafood.domain.service.HotelService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/hotel")
public class HotelController {

	@Autowired
	private HotelService hotelService;
	
	@Autowired
	private HotelIMapper hotelMapper;
	
	@GetMapping()
	public List<HotelOutput> findAll() {
		return hotelMapper.ToCollectionInputModel(hotelService.findAll()); 	
	}
	
	@GetMapping(value = "/{hotelId}")
	public HotelOneOutput findOne(@PathVariable Long hotelId) {
		return hotelMapper.ToOneOutputModel(hotelService.findOne(hotelId)); 	
	}
	
	@PostMapping()
	@ResponseStatus(HttpStatus.CREATED)
	public HotelOutput Add(@RequestBody @Valid HotelInput hotelInput) {
		Hotel hotel = hotelMapper.ToDomainModel(hotelInput);
		return  hotelMapper.ToOutputModel(hotelService.add(hotel));
	}
	
	@PutMapping(value = "/{hotelId}")
	public HotelOutput Update(@RequestBody @Valid HotelInput hotelInput, @PathVariable Long hotelId) {	
		Hotel hotel = hotelService.findOne(hotelId);
		hotelMapper.CopyToDomain(hotelInput, hotel);
		hotelService.add(hotel);
		
		return hotelMapper.ToOutputModel(hotel);
	}
	
	
	@DeleteMapping(value = "/{hotelId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@PathVariable Long hotelId) {
		hotelService.delete(hotelId);
	}
}
