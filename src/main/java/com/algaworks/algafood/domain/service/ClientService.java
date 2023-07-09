package com.algaworks.algafood.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.algaworks.algafood.domain.exception.EntityInUseException;
import com.algaworks.algafood.domain.exception.ClientNotFoundException;
import com.algaworks.algafood.domain.model.Client;
import com.algaworks.algafood.domain.repository.ClientRepository;

@Service
public class ClientService {

	@Autowired
	private ClientRepository clientRepository;
	
	public List<Client> findAll() {
		return clientRepository.findAll();
	}
	
	public Client findOne(Long clientId) {
		return clientRepository.findById(clientId).orElseThrow(
				()-> new ClientNotFoundException(clientId));
	}

	@Transactional
	public Client add(Client client) {
		return clientRepository.save(client);
	}

	@Transactional
	public void delete(Long clientId) {
		try {
			findOne(clientId);
			clientRepository.deleteById(clientId);
			clientRepository.flush();
		}
		catch(DataIntegrityViolationException e) {
			throw new EntityInUseException(String.format("Cliente de codigo %s não pode ser deletado pois esta em uso", clientId));
		}			
	}
}

