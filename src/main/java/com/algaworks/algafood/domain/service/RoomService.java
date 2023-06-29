package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.RoomNotFoundException;
import com.algaworks.algafood.domain.model.Hotel;
import com.algaworks.algafood.domain.model.Room;
import com.algaworks.algafood.domain.repository.RoomRepository;

@Service
public class RoomService {

	@Autowired
	private RoomRepository roomRepository;
	
	@Autowired
	private HotelService hotelService;
	
	@Transactional
	public Room add(Room room) {
		Long hotelId = room.getHotel().getId();
		
		Hotel hotel = hotelService.findOne(hotelId);
		hotel.getQuartos().add(room);
		
		return roomRepository.save(room);
	}
	
	public List<Room> findAll() {
		return roomRepository.findAll();
	}
	
	public Room findOne(Long roomId) {
		return roomRepository.findById(roomId).orElseThrow(
				()-> new RoomNotFoundException(roomId));
	}
	
	@Transactional
	public void delete (Long roomId) {
		try {
			findOne(roomId);
			roomRepository.deleteById(roomId);
			roomRepository.flush();
		}
		catch(DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format("Hotel de codigo %s não pode ser deletado pois esta em uso", roomId));
		}			
	}
}
