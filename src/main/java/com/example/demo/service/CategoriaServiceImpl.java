package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.CategoriaDTO;
import com.example.demo.repository.dao.CategoriaRepository;
import com.example.demo.repository.entity.Categoria;

@Service
public class CategoriaServiceImpl implements CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Override
	public List<CategoriaDTO> findAll() {
		List<CategoriaDTO> listaCategoriasDTO = categoriaRepository.findAll().stream()
				.map(cat -> CategoriaDTO.convertToDTO(cat)).collect(Collectors.toList());

		return listaCategoriasDTO;
	}

	@Override
	public CategoriaDTO findById(CategoriaDTO categoryDTO) {
		Optional<Categoria> categoriaOpt = categoriaRepository.findById(categoryDTO.getId());
		CategoriaDTO categoriaDTO = null;

		if (categoriaOpt.isPresent()) {
			categoriaDTO = CategoriaDTO.convertToDTO(categoriaOpt.get());
		}

		return categoriaDTO;
	}

}
