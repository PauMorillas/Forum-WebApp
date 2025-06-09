package com.example.demo.repository.dao;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.repository.entity.Like;

public interface LikeRepository extends JpaRepository<Like, Long> {
	// Contar likes de un post
	long countByPostId(Long postId);

	// Ver si un cliente ya dio like a un post
	boolean existsByClientIdAndPostId(Long clientId, Long postId);

	// Obtener IDs de posts que le gustaron a un cliente
	@Query("SELECT l.post.id FROM Like l WHERE l.client.id = :clientId")
	List<Long> findLikedPostIdsByClient(@Param("clientId") Long clientId);

	Optional<Like> findByClientIdAndPostId(Long clientId, Long postId);
}
