package com.example.demo.web.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.CategoriaDTO;
import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.PostDTO;
import com.example.demo.service.CategoriaService;
import com.example.demo.service.ClienteService;
import com.example.demo.service.LikeService;
import com.example.demo.service.PostService;
import com.example.demo.utils.UsuarioSesionUtils;

@Controller
public class IndexController {
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private PostService postService;

	@Autowired
	private CategoriaService categoriaService;

	@Autowired
	private LikeService likeService;

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/")
	public ModelAndView index() {
		log.info(this.getClass().getSimpleName() + " - index: Mostramos la pagina inicial");
		
		List<PostDTO> listaPostsDTO = postService.findAll();
		List<CategoriaDTO> listaCategoriasDTO = categoriaService.findAll();
		boolean hayUsuario = UsuarioSesionUtils.hayUsuarioEnSesion();
		String nomUsu = null;
		List<Long> likedPostIds = new ArrayList<>();
		ModelAndView mav = new ModelAndView("index");

		// Recupera el cliente desde el usuario en sesión
		if (hayUsuario) {
			nomUsu = UsuarioSesionUtils.obtenerNombreUsuario();
			ClienteDTO clienteDTO = clienteService.findByEmail(nomUsu);
			likedPostIds = likeService.findLikedPostIdsByClientId(clienteDTO.getId());
			mav.addObject("nombreUsuario", nomUsu);
		}

		mav.addObject("listaPosts", listaPostsDTO);
		mav.addObject("listaCategoriasDTO", listaCategoriasDTO);
		mav.addObject("hayUsuario", hayUsuario);
		mav.addObject("postDTO", new PostDTO());
		mav.addObject("likedPostIds", likedPostIds);

		return mav;
	}
}
