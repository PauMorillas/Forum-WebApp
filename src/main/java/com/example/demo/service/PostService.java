package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.PostDTO;

public interface PostService {
	List<PostDTO> findAll();

	PostDTO findById(PostDTO postDTO);

	void save(PostDTO postDTO);

	void delete(PostDTO postDTO);
}
