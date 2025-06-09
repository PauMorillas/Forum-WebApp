package com.example.demo.web.controller;


import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.service.ClienteService;

@Controller
public class ClienteController {
	private static final Logger log = LoggerFactory.getLogger(IndexController.class);

	@Autowired
	private ClienteService clienteService;

	@GetMapping("/register")
	public ModelAndView register() {
		log.info(this.getClass().getSimpleName() + " - register.html: Mostramos la página de registro");
		ClienteDTO clienteDTO = new ClienteDTO();
		ModelAndView mav = new ModelAndView("form-client");
		mav.addObject("cliente", clienteDTO);

		return mav;
	}

	@PostMapping("/register")
	public ModelAndView processRegister(@ModelAttribute ClienteDTO clienteDTO) {
		log.info(this.getClass().getSimpleName() + " - processRegister(): Lógica para el registro");
		ModelAndView mav = new ModelAndView("form-client");
		try {
			clienteDTO.setCreatedAt(LocalDateTime.now());
			clienteService.save(clienteDTO);
			mav.setViewName("redirect:/login");
		} catch (IllegalArgumentException e) {
			mav.setViewName("form-client");
			mav.addObject("cliente", clienteDTO);
			mav.addObject("error", e.getMessage());
		}

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

//	@PostMapping("/login")
//	public ModelAndView processLogin(@ModelAttribute ClienteDTO clienteDTO) {
//		log.info(this.getClass().getSimpleName() + " - processLogin(): Lógica para el login");
//
//		clienteService.processLogin(clienteDTO);
//
//		ModelAndView mav = new ModelAndView("redirect:/");
//		mav.addObject("clienteDTO", clienteDTO);
//		return mav;
//	}

}
