package com.example.demo.model.dto;

import java.time.LocalDate;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
public class PostDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String titulo;
	private String contenido;
	private LocalDate fechaCreacion;
	// TODO: Hacer las relaciones con la bd
	@ToString.Exclude
	private ClienteDTO clienteDTO;
	@ToString.Exclude
	private CategoriaDTO categoriaDTO;

	public PostDTO() {

	}

}