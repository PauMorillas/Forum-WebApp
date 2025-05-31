package com.example.demo.model.dto;

import java.time.LocalDateTime;

import com.example.demo.repository.entity.Comentario;
import com.example.demo.repository.entity.Post;

import lombok.Data;
import lombok.ToString;

@Data
public class ComentarioDTO {

	private Long id;
	private String content;
	private LocalDateTime createdAt;
	@ToString.Exclude
	private ClienteDTO clientDTO;
	@ToString.Exclude
	private Post postDTO;

	public ComentarioDTO() {

	}

	public static ComentarioDTO convertToDTO(Comentario c) {
		ComentarioDTO comentarioDTO = new ComentarioDTO();
		comentarioDTO.setId(c.getId());
		comentarioDTO.setContent(c.getContent());
		comentarioDTO.setCreatedAt(c.getCreatedAt());
		comentarioDTO.setClientDTO(ClienteDTO.convertToDTO(c.getClient()));
//		comentarioDTO.setPostDTO(); No se necesita en el momento del index

		return comentarioDTO;
	}

}