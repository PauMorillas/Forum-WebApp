package com.example.demo.web.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.LikeDTO;
import com.example.demo.model.dto.PostDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.LikeService;
import com.example.demo.utils.UsuarioSesionUtils;

@Controller
public class LikeController {
	@Autowired
	ClienteService clienteService;

	@Autowired
	private LikeService likeService;


	@PostMapping("/likes/toggle")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> toggleLike(@RequestBody Map<String, Long> payload) {
		Long postId = payload.get("postId");

		// Recuperar cliente desde el usuario en sesión
		ClienteDTO clienteDTO = clienteService.findByEmail(UsuarioSesionUtils.obtenerNombreUsuario());

		Optional<LikeDTO> existing = likeService.findByClientIdAndPostId(clienteDTO.getId(), postId);
		PostDTO postDTO = new PostDTO();
		postDTO.setId(postId);
		LikeDTO newLike = new LikeDTO();

		boolean liked;

		if (existing.isPresent()) {
			likeService.delete(existing.get());
			liked = false;
		} else {
			newLike.setClientDTO(clienteDTO);
			newLike.setPostDTO(postDTO);
			newLike.setLikedAt(LocalDateTime.now());

			likeService.save(newLike);
			liked = true;
		}

		long totalLikes = likeService.countByPostId(postDTO);

		Map<String, Object> response = new HashMap<>();
		response.put("liked", liked);
		response.put("totalLikes", totalLikes);

		return ResponseEntity.ok(response);
	}
}
