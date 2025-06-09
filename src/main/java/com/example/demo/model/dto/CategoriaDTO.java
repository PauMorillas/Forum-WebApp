package com.example.demo.model.dto;

import com.example.demo.repository.entity.Categoria;

import lombok.Data;

@Data
public class CategoriaDTO {
	private Long id;
	private String name;
	private String description;

	public CategoriaDTO() {

	}

	public static CategoriaDTO convertToDTO(Categoria c) {
		CategoriaDTO categoriaDTO = new CategoriaDTO();
		categoriaDTO.setId(c.getId());
		categoriaDTO.setName(c.getName());
		categoriaDTO.setDescription(c.getDescription());

		return categoriaDTO;
	}

	public static Categoria convertToEntity(CategoriaDTO cDTO) {
		Categoria categoria = new Categoria();
		categoria.setId(cDTO.getId());
		categoria.setName(cDTO.getName());
		categoria.setDescription(cDTO.getDescription());

		return categoria;
	}
}
