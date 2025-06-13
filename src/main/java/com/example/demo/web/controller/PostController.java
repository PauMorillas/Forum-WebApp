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
import org.springframework.web.bind.annotation.RequestParam;
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
	public ModelAndView createPost(@ModelAttribute PostDTO postDTO) {
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
	public ModelAndView showPostDetail(@PathVariable Long id) {

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

	// --- NUEVOS MÉTODOS DE BÚSQUEDA ---

	/**
	 * Endpoint para buscar posts por categoría. La URL será
	 * /posts/search/category?categoryName=NombreDeLaCategoria
	 */
	@GetMapping("/posts/search/category")
	public ModelAndView searchPostsByCategory(
			@RequestParam(value = "categoryName", required = false) String categoryName) {
		List<PostDTO> listaPostsDTO;
		if (categoryName != null && !categoryName.trim().isEmpty()) {
			listaPostsDTO = postService.searchByCategory(categoryName.trim());
		} else {
			// Si no se selecciona ninguna categoría, quizás mostrar todos los posts
			listaPostsDTO = postService.findAll();
		}
		ModelAndView mav = prepareModelAndViewForSearchResults(listaPostsDTO);
		return mav;
	}

	/**
	 * Endpoint para buscar posts por texto libre (priorizando título, luego
	 * contenido). La URL será /posts/search/text?query=TextoDeBusqueda
	 */
	@GetMapping("/posts/search/text")
	public ModelAndView searchPostsByText(@RequestParam(value = "query", required = false) String query) {
		List<PostDTO> listaPostsDTO;
		if (query != null && !query.trim().isEmpty()) {
			listaPostsDTO = postService.searchByTitleThenContent(query.trim());
		} else {
			// Si el campo de búsqueda está vacío, quizás mostrar todos los posts
			listaPostsDTO = postService.findAll();
		}
		// Necesitas volver a cargar las categorías para que el dropdown se muestre
		// correctamente
		List<CategoriaDTO> listaCategoriasDTO = categoriaService.findAll();
		ModelAndView mav = prepareModelAndViewForSearchResults(listaPostsDTO);
		mav.addObject("listaCategoriasDTO", listaCategoriasDTO);
		return mav;
	}

	// MÉTODO DE UTILIDAD PARA PREPARAR EL ModelAndView DE RESULTADOS
	// Evita repetir código para añadir los atributos comunes al ModelAndView de
	// resultados.
	private ModelAndView prepareModelAndViewForSearchResults(List<PostDTO> posts) {
		ModelAndView mav = new ModelAndView("index");

		// --- Recargar todos los atributos que el index.html espera ---
		List<CategoriaDTO> listaCategoriasDTO = categoriaService.findAll();
		boolean hayUsuario = UsuarioSesionUtils.hayUsuarioEnSesion();
		String nomUsu = null;
		List<Long> likedPostIds = new ArrayList<>();

		if (hayUsuario) {
			nomUsu = UsuarioSesionUtils.obtenerNombreUsuario();
			ClienteDTO clienteDTO = clienteService.findByEmail(nomUsu);
			likedPostIds = likeService.findLikedPostIdsByClientId(clienteDTO.getId());
			mav.addObject("nombreUsuario", nomUsu);
		}

		// --- Atributos específicos de la búsqueda o del feed ---
		mav.addObject("listaPosts", posts); // Esta será la lista de posts filtrada o el feed completo
		mav.addObject("listaCategoriasDTO", listaCategoriasDTO); // Siempre se carga para el dropdown
		mav.addObject("hayUsuario", hayUsuario);
		mav.addObject("postDTO", new PostDTO()); // Para el formulario de creación de posts (siempre se necesita)
		mav.addObject("likedPostIds", likedPostIds);

		return mav;
	}

}
