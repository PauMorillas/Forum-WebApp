package com.example.demo.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;

@Service
public class ClienteServiceImpl implements ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Override
	public List<ClienteDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClienteDTO findById(ClienteDTO clienteDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(ClienteDTO clienteDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(ClienteDTO clienteDTO) {
		// TODO Auto-generated method stub

	}

//	@Override
//	public List<ClienteDTO> findAll() {
//
//		log.info("ClienteServiceImpl - findAll(): Lista de todos los clientes");
//
//		List<ClienteDTO> listaClientesDTO = clienteRepository.findAll().stream().map(p -> ClienteDTO.convertToDTO(p))
//				.collect(Collectors.toList());
//
//		return listaClientesDTO;
//	}
//
//	@Override
//	public ClienteDTO findById(ClienteDTO clienteDTO) {
//		log.info("ClienteServiceImpl - findById: Buscar cliente por id: " + clienteDTO.getId());
//
//		Optional<Cliente> cliente = clienteRepository.findById(clienteDTO.getId());
//		if (cliente.isPresent()) {
//			clienteDTO = ClienteDTO.convertToDTO(cliente.get());
//			return clienteDTO;
//		} else {
//			return null;
//		}
//	}
//
//	@Override
//	public void save(ClienteDTO clienteDTO) {
//
//		log.info("ClienteServiceImpl - save(): Guardamos el cliente: " + clienteDTO.toString());
//
//		System.out.println("Cliente: " + clienteDTO.getId());
//		System.out.println("Recomendación: " + clienteDTO.getRecomendacionDTO().getId());
//
//		Cliente cliente = ClienteDTO.convertToEntity(clienteDTO);
//		clienteRepository.save(cliente);
//	}
//
//	@Override
//	public void delete(ClienteDTO clienteDTO) {
//		log.info("ClienteServiceImpl - delete(): Borramos el cliente: " + clienteDTO.getId());
//
//		Cliente cliente = new Cliente();
//		cliente.setId(clienteDTO.getId());
//		clienteRepository.deleteById(cliente.getId());
//	}

}
