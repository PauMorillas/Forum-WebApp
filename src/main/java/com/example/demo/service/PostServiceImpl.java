package com.example.demo.service;

import java.util.ArrayList;
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
		postRepository.delete(PostDTO.convertToEntity(postDTO));
	}

	public List<PostDTO> searchByTitle(String title) {
		List<Post> posts = postRepository.findByTitleContainingIgnoreCase(title);
		return posts.stream()
				.map(p -> PostDTO.convertToDTO(p, likeRepository.countByPostId(p.getId()),
						comentarioRepository.countByPostId(p.getId())))
				.collect(Collectors.toList());
	}

	public List<PostDTO> searchByContent(String content) {
		List<Post> posts = postRepository.findByContentContainingIgnoreCase(content);
		return posts.stream().map(p -> PostDTO.convertToDTO(p, likeRepository.countByPostId(p.getId()),
				comentarioRepository.countByPostId(p.getId()))).collect(Collectors.toList());
	}

	public List<PostDTO> searchByCategory(String categoryName) {
		List<Post> posts = postRepository.findByCategoryNameContainingIgnoreCase(categoryName);
		return posts.stream().map(p -> PostDTO.convertToDTO(p, likeRepository.countByPostId(p.getId()),
				comentarioRepository.countByPostId(p.getId()))).collect(Collectors.toList());
	}

	/**
	 * Busca posts priorizando el título y luego el contenido. Si un post coincide
	 * por título, no se buscará en contenido. Si no hay resultados por título, se
	 * buscan en contenido.
	 */
	public List<PostDTO> searchByTitleThenContent(String query) {
		List<Post> postsResult = new ArrayList<>();

		// 1. Buscar por título
		List<Post> postsByTitle = postRepository.findByTitleContainingIgnoreCase(query);
		postsResult.addAll(postsByTitle);

		// 2. Si encontramos posts por título, los devolvemos directamente y no buscamos
		// por contenido
		if (!postsByTitle.isEmpty()) {
			return searchByTitle(query);
		} else {
			// Si NO hay posts por título, entonces buscamos por contenido
			return searchByContent(query);
		}
	}
}
