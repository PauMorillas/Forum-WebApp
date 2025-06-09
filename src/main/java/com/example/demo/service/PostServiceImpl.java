package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.PostDTO;
import com.example.demo.repository.dao.ComentarioRepository;
import com.example.demo.repository.dao.LikeRepository;
import com.example.demo.repository.dao.PostRepository;
import com.example.demo.repository.entity.Post;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepository;
	@Autowired
	private LikeRepository likeRepository;
	@Autowired
	private ComentarioRepository comentarioRepository;

	// Monta los DTO con los likes ya contados
	@Override
	public List<PostDTO> findAll() {
		// TODO Auto-generated method stub
		List<PostDTO> listaPostsDTO = postRepository.findAll().stream()
				.map(p -> PostDTO.convertToDTO(p, likeRepository.countByPostId(p.getId()),
						comentarioRepository.countByPostId(p.getId())))
				.collect(Collectors.toList());

		return listaPostsDTO;
	}

	@Override
	public PostDTO findById(PostDTO postDTO) {
		Optional<Post> postOptional = postRepository.findById(postDTO.getId());

		if (postOptional.isPresent()) {
			postDTO = PostDTO.convertToDTO(postOptional.get(), likeRepository.countByPostId(postDTO.getId()),
					comentarioRepository.countByPostId(postDTO.getId()));
		}

		return postDTO;
	}

	@Override
	public void save(PostDTO postDTO) {
		postRepository.save(PostDTO.convertToEntity(postDTO));
	}

	@Override
	public void delete(PostDTO postDTO) {
		// TODO Auto-generated method stub

	}

}
