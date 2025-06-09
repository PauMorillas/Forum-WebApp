package com.example.demo.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Comentario;
import com.example.demo.repository.entity.Mensaje;
import com.example.demo.repository.entity.Post;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String username;
	@ToString.Exclude
	private String pass;
	private String email;
	private LocalDateTime createdAt;
	@ToString.Exclude
	private List<Post> postsList;
	@ToString.Exclude
	private List<Mensaje> msgsList;
	@ToString.Exclude
	private List<Comentario> commentsList;

	public ClienteDTO(Long id, String username, String pass, String email, LocalDateTime createdAt) {
		this.id = id;
		this.username = username;
		this.pass = pass;
		this.email = email;
		this.createdAt = createdAt;
		this.postsList = new ArrayList<>();
		this.msgsList = new ArrayList<>();
		this.commentsList = new ArrayList<>();
	}

	public ClienteDTO() {
		this.postsList = new ArrayList<>();
		this.msgsList = new ArrayList<>();
		this.commentsList = new ArrayList<>();
	}

	public static ClienteDTO convertToDTO(Cliente c) {
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(c.getId());
		clienteDTO.setUsername(c.getUsername());
		clienteDTO.setPass(null); // No mostramos el pass pero si recogemos el pass de la vista register
		clienteDTO.setEmail(c.getEmail());
		clienteDTO.setCreatedAt(c.getCreatedAt());

		return clienteDTO;
	}

	public static Cliente convertToEntity(ClienteDTO cDTO) {
		Cliente cliente = new Cliente();
		cliente.setId(cDTO.getId());
		cliente.setUsername(cDTO.getUsername());
		cliente.setPass(cDTO.getPass());
		cliente.setEmail(cDTO.getEmail());
		cliente.setCreatedAt(cDTO.getCreatedAt());

		return cliente;
	}
}
