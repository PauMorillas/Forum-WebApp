package com.example.demo.model.dto;

import java.time.LocalDate;

import com.example.demo.repository.entity.Post;

import lombok.Data;
import lombok.ToString;

@Data
public class ComentarioDTO {

	private Long id;
	private String contenido;
	private LocalDate fecha;
	@ToString.Exclude
	private ClienteDTO clienteDTO;
	@ToString.Exclude
	private Post postDTO;

	public ComentarioDTO() {

	}

}