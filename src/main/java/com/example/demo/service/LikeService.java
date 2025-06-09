package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.LikeDTO;
import com.example.demo.model.dto.PostDTO;

public interface LikeService {
	long countByPostId(PostDTO postDTO);

	boolean existsByClientIdAndPostId(ClienteDTO clienteDTO, PostDTO postDTO);

	List<Long> findLikedPostIdsByClientId(@Param("clientId") Long clientId);

	Optional<LikeDTO> findByClientIdAndPostId(Long clientId, Long postId);

	void likePost(Long postId, Long clientId);

	void save(LikeDTO likeDTO);

	void delete(LikeDTO likeDTO);
}
