package com.example.demo.model.dto;

import java.time.LocalDateTime;

import com.example.demo.repository.entity.Post;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.ToString;

@Data
public class PostDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	private LocalDateTime createdAt;
	private long likes;
	private long comments;
	// TODO: Hacer las relaciones con la bd
	@ToString.Exclude
	private ClienteDTO clientDTO;
	@ToString.Exclude
	private CategoriaDTO categoryDTO;
//	@ToString.Exclude
//	private List<Comentario> commentsDTOList;

	public PostDTO() {
		clientDTO = new ClienteDTO();
		categoryDTO = new CategoriaDTO();
//		commentsDTOList = new ArrayList<>();
	}

	// TODO: HACER los CONVERT
	public static PostDTO convertToDTO(Post p, long likes, long comments) {
		PostDTO postDTO = new PostDTO();
		postDTO.setId(p.getId());
		postDTO.setTitle(p.getTitle());
		postDTO.setContent(p.getContent());
		postDTO.setCreatedAt(p.getCreatedAt());
		postDTO.setLikes(likes);
		postDTO.setComments(comments);
		postDTO.setClientDTO(ClienteDTO.convertToDTO(p.getClient()));

		postDTO.setCategoryDTO(CategoriaDTO.convertToDTO(p.getCategory()));

		return postDTO;
	}

}