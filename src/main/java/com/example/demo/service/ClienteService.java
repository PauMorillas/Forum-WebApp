package com.example.demo.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.example.demo.model.dto.ClienteDTO;

public interface ClienteService {
	List<ClienteDTO> findAll();

	void save(ClienteDTO clienteDTO);

	void delete(ClienteDTO clienteDTO);

	UserDetails processLogin(ClienteDTO clienteDTO) throws UsernameNotFoundException;

	ClienteDTO findByEmail(String obtenerNombreUsuario);

	ClienteDTO findById(ClienteDTO clienteDTO);

	ClienteDTO findByUsername(String name);

}
