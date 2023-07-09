package com.algaworks.algafood.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.DTO.input.RentRoomInput;
import com.algaworks.algafood.api.DTO.output.RentRoomOutput;
import com.algaworks.algafood.api.assembler.RentMapper;
import com.algaworks.algafood.domain.model.RentRoom;
import com.algaworks.algafood.domain.service.RentRoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "/reservas")
public class RentController {

	
	@Autowired
	private RentRoomService rentRoomService;
	
	@Autowired
	private RentMapper rentMapper;
	
	@GetMapping()
	public List<RentRoomOutput> findAll() {
		return rentMapper.toCollectionOuputModel(rentRoomService.findAll()); 	
	}
	
	@GetMapping(value = "/{rentId}")
	public RentRoomOutput findOne(@PathVariable Long rentId) {
		return rentMapper.toOutputModel(rentRoomService.findOne(rentId)); 	
	}
	
	@GetMapping(value = "/cliente/{clientId}")
	public RentRoomOutput findRentByClient(@PathVariable Long clientId) {
		return rentMapper.toOutputModel(rentRoomService.findRentByClient(clientId)); 	
	}
	
	@PostMapping()
	public RentRoomOutput rentThisRoom(@Valid @RequestBody RentRoomInput rentInput) {
		RentRoom rent =  rentRoomService.add(rentMapper.toDomainModel(rentInput));
		return rentMapper.toOutputModel(rent);
	}
	
}