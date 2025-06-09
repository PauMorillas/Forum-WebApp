package com.example.demo.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.model.dto.ComentarioDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.ComentarioService;
import com.example.demo.service.PostService;
import com.example.demo.utils.UsuarioSesionUtils;

@Controller
public class ComentarioController {

	@Autowired
	private ComentarioService comentarioService;

	@Autowired
	private PostService postService;

	@Autowired
	private ClienteService clienteService;

	@PostMapping("/comments/new")
	public String save(@ModelAttribute("comentarioDTO") ComentarioDTO comentarioDTO) {
		if (UsuarioSesionUtils.obtenerUsuario() == null) {
			return "redirect:/login";
		}

		comentarioService.save(comentarioDTO);

		return "redirect:/posts/" + comentarioDTO.getPostDTO().getId();
	}
}