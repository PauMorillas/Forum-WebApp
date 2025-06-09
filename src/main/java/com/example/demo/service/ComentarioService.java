package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ComentarioDTO;

public interface ComentarioService {

	List<ComentarioDTO> findByPostId(Long id);

	void save(ComentarioDTO comentarioDTO);

}
