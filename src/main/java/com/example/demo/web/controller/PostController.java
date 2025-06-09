package com.example.demo.web.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.CategoriaDTO;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.ComentarioDTO;
import com.example.demo.model.dto.PostDTO;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ComentarioService;
import com.example.demo.service.LikeService;
import com.example.demo.service.PostService;
import com.example.demo.utils.UsuarioSesionUtils;

@Controller
public class PostController {
	@Autowired
	private PostService postService;
	@Autowired
	private ClienteService clienteService;
	@Autowired
	private CategoriaService categoriaService;
	@Autowired
	private ComentarioService comentarioService;
	@Autowired
	private LikeService likeService;

	@PostMapping("/posts/create")
	public ModelAndView postMethodName(@ModelAttribute PostDTO postDTO) {
		// Obtenemos el cliente actual (basado en el usuario logueado)
		ClienteDTO clienteDTO = clienteService.findByEmail(UsuarioSesionUtils.obtenerNombreUsuario());
		CategoriaDTO categoriaDTO = categoriaService.findById(postDTO.getCategoryDTO());

		postDTO.setClientDTO(clienteDTO);
		postDTO.setCategoryDTO(categoriaDTO);
		postDTO.setCreatedAt(LocalDateTime.now());
		System.out.println("Post DTO: " + postDTO.toString());
		postService.save(postDTO);

		ModelAndView mav = new ModelAndView("redirect:/");
		return mav;
	}

	@GetMapping("/posts/{id}")
	public ModelAndView verDetallePost(@PathVariable Long id) {

		PostDTO postDTO = new PostDTO();
		postDTO.setId(id);
		postDTO = postService.findById(postDTO);
		List<ComentarioDTO> comentarios = comentarioService.findByPostId(id);
		boolean hayUsuario = UsuarioSesionUtils.hayUsuarioEnSesion();
		String nombreUsu = null;
		List<Long> likedPostIds = new ArrayList<>();

		ModelAndView mav = new ModelAndView("post-info");

		if (hayUsuario) {
			nombreUsu = UsuarioSesionUtils.obtenerNombreUsuario();
			ClienteDTO cliente = clienteService.findByEmail(nombreUsu);
			likedPostIds = likeService.findLikedPostIdsByClientId(cliente.getId());
			ComentarioDTO comment = new ComentarioDTO();
			comment.setPostDTO(postDTO);
			mav.addObject("comentarioDTO", comment);

		}

		mav.addObject("postDTO", postDTO);
		mav.addObject("comentarios", comentarios);
		mav.addObject("hayUsuario", hayUsuario);
		mav.addObject("likedPostIds", likedPostIds);
		mav.addObject("nombreUsuario", nombreUsu);

		return mav;
	}

}
