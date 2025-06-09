package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.CategoriaDTO;

public interface CategoriaService {

	List<CategoriaDTO> findAll();

	CategoriaDTO findById(CategoriaDTO categoryDTO);

}
