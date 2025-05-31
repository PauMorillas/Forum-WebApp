package com.example.demo.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.PostDTO;
import com.example.demo.service.PostService;

@Controller
public class IndexController {
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private PostService postService;

	@GetMapping("/")
	public ModelAndView index() {
		log.info(this.getClass().getSimpleName() + " - index: Mostramos la pagina inicial");

		List<PostDTO> listaPostsDTO = postService.findAll();

		ModelAndView mav = new ModelAndView("index");
		mav.addObject("listaPosts", listaPostsDTO);

		return mav;
	}

	@GetMapping("/login")
	public ModelAndView login() {
		log.info(this.getClass().getSimpleName() + " - login.html: Mostramos la página de login");
		ClienteDTO clienteDTO = new ClienteDTO();
		ModelAndView mav = new ModelAndView("login");
		mav.addObject("clienteDTO", clienteDTO);

		return mav;
	}
}
