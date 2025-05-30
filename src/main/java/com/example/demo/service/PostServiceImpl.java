package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.PostDTO;
import com.example.demo.repository.dao.PostRepository;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;

	@Override
	public List<PostDTO> findAll() {
		// TODO Auto-generated method stub
		List<PostDTO> listaPostsDTO = postRepository.findAll().stream().map(p -> PostDTO.convertToDTO(p))
				.collect(Collectors.toList());

		return listaPostsDTO;
	}

	@Override
	public PostDTO findById(PostDTO postDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void save(PostDTO postDTO) {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete(PostDTO postDTO) {
		// TODO Auto-generated method stub

	}

}
