package com.example.demo.model.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Like;
import com.example.demo.repository.entity.Post;

import lombok.Data;

@Data
public class LikeDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private ClienteDTO clientDTO;
	private PostDTO postDTO;
	private LocalDateTime likedAt;

	public static LikeDTO convertToDTO(Like like) {
		LikeDTO dto = new LikeDTO();
		dto.setId(like.getId());
		dto.setClientDTO(ClienteDTO.convertToDTO(like.getClient()));
		dto.setPostDTO(PostDTO.convertToDTO(like.getPost(), 0l, 0l));
		dto.setLikedAt(like.getLikedAt());

		return dto;
	}

	public static Like convertToEntity(LikeDTO dto, Cliente client, Post post) {
		Like like = new Like();
		like.setClient(client);
		like.setPost(post);
		like.setLikedAt(dto.getLikedAt() != null ? dto.getLikedAt() : LocalDateTime.now());
		like.setClient(client);
		like.setPost(post);

		return like;
	}
}
