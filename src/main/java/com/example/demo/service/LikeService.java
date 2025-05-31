package com.example.demo.service;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.PostDTO;

public interface LikeService {
	long countByPostId(PostDTO postDTO);

	// Ver si un cliente ya dio like a un post
	boolean existsByClientIdAndPostId(ClienteDTO clienteDTO, PostDTO postDTO);

	// Obtener IDs de posts que le gustaron a un cliente
	@Query("SELECT l.post.id FROM Like l WHERE l.client.id = :clientId")
	List<Long> findLikedPostIdsByClient(@Param("clientId") Long clientId);
}
