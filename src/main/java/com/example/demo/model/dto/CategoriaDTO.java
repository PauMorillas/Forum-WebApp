package com.example.demo.model.dto;

import lombok.Data;

@Data
public class CategoriaDTO {
	private Long id;
	private String nombre;
	private String descripcion;

	public CategoriaDTO() {

	}
}
