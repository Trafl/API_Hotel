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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.algaworks.algafood.api.DTO.input.RoomInput;
import com.algaworks.algafood.api.DTO.jsonview.OutPutView;
import com.algaworks.algafood.api.DTO.output.RoomOutput;
import com.algaworks.algafood.api.assembler.RoomMapper;
import com.algaworks.algafood.domain.model.Room;
import com.algaworks.algafood.domain.service.HotelRoomService;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.validation.Valid;

@RestController
@RequestMapping(value = "hotel/{hotelId}/quarto")
public class HotelRoomController {
		
	@Autowired
	private HotelRoomService hotelRoomService;
	
	@Autowired 
	private RoomMapper roomMapper;
	
	@GetMapping()
	@JsonView(OutPutView.RoomView.class)
	public List<RoomOutput> findRoomsOfHotel(@PathVariable Long hotelId, @RequestParam(required = false) boolean alugados ) {
		List<Room> list = null;
		
		if(alugados) {
			list = hotelRoomService.findRooms(hotelId);
		}else
			list = hotelRoomService.findAvailableRoom(hotelId);

		return roomMapper.toCollectionOuputModel(list);
	}

	@GetMapping(value = "/{roomId}")
	@JsonView(OutPutView.RoomView.class)
	public RoomOutput findOneRoomOfHotel(@PathVariable Long hotelId ,@PathVariable Long roomId) {
		return roomMapper.toOutputModel(hotelRoomService.findOneRoomFromHotel(hotelId, roomId));
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public RoomOutput addRoom(@PathVariable Long hotelId,@Valid @RequestBody RoomInput roomInput){
		Room room = roomMapper.toDomainModel(roomInput);
		hotelRoomService.addRoomHotel(hotelId, room);
		return roomMapper.toOutputModel(room);
	}
	@PutMapping(value = "/{roomId}")
	public RoomOutput updateRoom(@PathVariable Long hotelId, @PathVariable Long roomId, @Valid @RequestBody RoomInput roomInput) {
		Room room = hotelRoomService.findOneRoomFromHotel(hotelId, roomId);
		
		roomMapper.copyToDomain(roomInput, room);
		hotelRoomService.upDateRoom(room);
		return roomMapper.toOutputModel(room);
	}
	
	@DeleteMapping(value = "/{roomId}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteRoom(@PathVariable Long roomId) {
		hotelRoomService.deleteRoom(roomId);
	}
}  
