package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.PostDTO;

public interface PostService {
	List<PostDTO> findAll();

	PostDTO findById(PostDTO postDTO);

	void save(PostDTO postDTO);

	void delete(PostDTO postDTO);

	List<PostDTO> searchByTitle(String searchText);

	List<PostDTO> searchByContent(String searchText);

	List<PostDTO> searchByCategory(String categoryName);

	List<PostDTO> searchByTitleThenContent(String trim);
}
