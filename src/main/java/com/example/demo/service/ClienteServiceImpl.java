package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService, UserDetailsService {

	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	public List<ClienteDTO> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ClienteDTO findById(ClienteDTO clienteDTO) {
		log.info("ClienteServiceImpl - findById(): Buscamos el cliente con id: " + clienteDTO.toString());
		Optional<Cliente> clienteOpt = clienteRepository.findById(clienteDTO.getId());
		ClienteDTO cliDTO = null;

		if (clienteOpt.isPresent()) {
			cliDTO = ClienteDTO.convertToDTO(clienteOpt.get());
		}

		return cliDTO;
	}

	public void save(ClienteDTO clienteDTO) throws IllegalArgumentException {
		log.info("ClienteServiceImpl - save(): Guardamos el cliente: " + clienteDTO.toString());
		
		validarCliente(clienteDTO); // Método interno para validar

		Cliente cliente = ClienteDTO.convertToEntity(clienteDTO);
		cliente.setPass(encoder.encode(clienteDTO.getPass())); // Aquí podrías encriptar

		clienteRepository.save(cliente);
	}

	@Override
	public void delete(ClienteDTO clienteDTO) {
		// TODO Auto-generated method stub
	}

	private void validarCliente(ClienteDTO clienteDTO) throws IllegalArgumentException {
		if (clienteDTO.getPass() == null || clienteDTO.getPass().length() < 6) {
			throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres");
		}

		if (clienteDTO.getEmail() == null || !clienteDTO.getEmail().matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
			throw new IllegalArgumentException("El email no es válido");
		}

		if (clienteRepository.existsByUsername(clienteDTO.getUsername())) {
			throw new IllegalArgumentException("El nombre de usuario ya está en uso");
		}

		if (clienteRepository.existsByEmail(clienteDTO.getEmail())) {
			throw new IllegalArgumentException("El correo ya está registrado");
		}
	}

	@Override
	public UserDetails processLogin(ClienteDTO clienteDTO) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByUsername(clienteDTO.getUsername())
				.orElseGet(() -> clienteRepository.findByEmail(clienteDTO.getEmail())
						.orElseThrow(() -> new UsernameNotFoundException("Usuario o email no encontrado")));

		return User.builder().username(cliente.getEmail()).password(cliente.getPass())
				.roles("USER").build();
	}

	@Override
	public UserDetails loadUserByUsername(String input) throws UsernameNotFoundException {
		Cliente cliente = clienteRepository.findByUsername(input).orElseGet(() -> clienteRepository.findByEmail(input)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado")));

		return User.builder().username(cliente.getEmail()).password(cliente.getPass())
				.roles("USER").build();
	}

//	@Override
//	public void processLogin(ClienteDTO clienteDTO) throws IllegalArgumentException {
//		// Buscar por username
//		Optional<Cliente> clienteOpt = clienteRepository.findByUsername(clienteDTO.getUsername());
//
//		Cliente cliente = null;
//
//		if (clienteOpt.isEmpty()) {
//			// Si no lo encuentra por username, prueba con el email
//			clienteOpt = clienteRepository.findByEmail(clienteDTO.getEmail());
//		}
//
//		if (clienteOpt.isEmpty()) {
//			throw new IllegalArgumentException("Usuario o email no encontrado");
//		} else {
//			cliente = clienteOpt.get();
//
//			// Comparar contraseña usando PasswordEncoder
//			if (!encoder.matches(clienteDTO.getPass(), cliente.getPass())) {
//				throw new IllegalArgumentException("Contraseña incorrecta");
//			}
//		}
//
//	}

	@Override
	public ClienteDTO findByEmail(String nomUsu) {
		log.info("ClienteServiceImpl - findByEmail(): Lista de todos los clientes");
		Optional<Cliente> clienteOpt = clienteRepository.findByEmail(nomUsu);
		ClienteDTO clienteDTO = null;

		if (clienteOpt.isPresent()) {
			clienteDTO = ClienteDTO.convertToDTO(clienteOpt.get());
		}

		return clienteDTO;
	}

	@Override
	public ClienteDTO findByUsername(String name) {
		Optional<Cliente> clienteOpt = clienteRepository.findByUsername(name);
		ClienteDTO clienteDTO = null;

		if (clienteOpt.isPresent()) {
			clienteDTO = ClienteDTO.convertToDTO(clienteOpt.get());
		}

		return clienteDTO;
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
