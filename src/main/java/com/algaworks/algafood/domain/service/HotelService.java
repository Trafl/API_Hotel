package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.HotelNotFoundException;
import com.algaworks.algafood.domain.model.Hotel;
import com.algaworks.algafood.domain.repository.HotelRepository;

@Service
public class HotelService {

	@Autowired
	private HotelRepository hotelRepository;
	
	public List<Hotel> findAll() {
		return hotelRepository.findAll();
	}
	
	public Hotel findOne(Long hotelId) {
		return hotelRepository.findById(hotelId).orElseThrow(
				()-> new HotelNotFoundException(hotelId));
	}

	@Transactional
	public Hotel add(Hotel hotel) {
		return hotelRepository.save(hotel);
	}

}

