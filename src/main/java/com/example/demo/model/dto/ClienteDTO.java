package com.example.demo.model.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Comentario;
import com.example.demo.repository.entity.Mensaje;
import com.example.demo.repository.entity.Post;

import lombok.Data;

@Data
public class ClienteDTO {

	private Long id;
	private String username;
	private LocalDateTime createdAt;
	private List<Post> postsList;
	private List<Mensaje> msgsList;
	private List<Comentario> commentsList;

	public ClienteDTO(Long id, String username, LocalDateTime createdAt) {
		this.id = id;
		this.username = username;
		this.createdAt = createdAt;
		// TODO: Las listas
	}

	public ClienteDTO() {

	}

	public static ClienteDTO convertToDTO(Cliente c) {
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(c.getId());
		clienteDTO.setUsername(c.getUsername());
		clienteDTO.setCreatedAt(c.getCreatedAt());

		return clienteDTO;
	}
}
