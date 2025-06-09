package com.example.demo.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.LikeDTO;
import com.example.demo.model.dto.PostDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.dao.LikeRepository;
import com.example.demo.repository.dao.PostRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Like;
import com.example.demo.repository.entity.Post;

@Service
public class LikeServiceImpl implements LikeService {

	@Autowired
	private LikeRepository likeRepository;

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private PostRepository postRepository;

	@Override
	public long countByPostId(PostDTO postDTO) {
		return likeRepository.countByPostId(postDTO.getId());
	}

	@Override
	public boolean existsByClientIdAndPostId(ClienteDTO clienteDTO, PostDTO postDTO) {
		return likeRepository.existsByClientIdAndPostId(clienteDTO.getId(), postDTO.getId());
	}

	@Override
	public List<Long> findLikedPostIdsByClientId(Long clientId) {
		return likeRepository.findLikedPostIdsByClient(clientId);
	}

	@Override
	public void likePost(Long postId, Long clientId) {
		// Validar que no se haya dado like ya
		if (!likeRepository.existsByClientIdAndPostId(clientId, postId)) {
			Like like = new Like();
			like.setPost(postRepository.findById(postId).orElseThrow());
			like.setClient(clienteRepository.findById(clientId).orElseThrow());
			like.setLikedAt(LocalDateTime.now());

			likeRepository.save(like);
		}
	}

	@Override
	public Optional<LikeDTO> findByClientIdAndPostId(Long clientId, Long postId) {
		return likeRepository.findByClientIdAndPostId(clientId, postId).map(LikeDTO::convertToDTO);
	}

	@Override
	public void save(LikeDTO likeDTO) {
		Cliente client = clienteRepository.findById(likeDTO.getClientDTO().getId()).orElseThrow();
		Post post = postRepository.findById(likeDTO.getPostDTO().getId()).orElseThrow();
		Like like = LikeDTO.convertToEntity(likeDTO, client, post);

		likeRepository.save(like);
	}

	@Override
	public void delete(LikeDTO likeDTO) {
		likeRepository.deleteById(likeDTO.getId());
	}

}
