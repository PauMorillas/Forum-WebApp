package com.example.demo.repository.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.repository.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Long> {
	@Query("SELECT COUNT(c) FROM Comentario c WHERE c.post.id = :postId")
	long countByPostId(@Param("postId") Long postId);

}
