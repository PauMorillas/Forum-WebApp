package com.example.demo.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.ComentarioDTO;
import com.example.demo.model.dto.PostDTO;
import com.example.demo.repository.dao.ComentarioRepository;
import com.example.demo.repository.entity.Comentario;
import com.example.demo.utils.UsuarioSesionUtils;

@Service
public class ComentarioServiceImpl implements ComentarioService {

	@Autowired
	private ComentarioRepository comentarioRepository;

	@Autowired
	private ClienteService clienteService;

	@Override
	public List<ComentarioDTO> findByPostId(Long id) {

		List<ComentarioDTO> listaComentariosDTO = comentarioRepository.findByPostId(id).stream()
				.map(c -> ComentarioDTO.convertToDTO(c)).collect(Collectors.toList());

		return listaComentariosDTO;
	}

	@Override
	public void save(ComentarioDTO comentarioDTO) {

		String email = UsuarioSesionUtils.obtenerNombreUsuario();
		ClienteDTO clienteDTO = clienteService.findByEmail(email);

		// Creamos el comentario con la lógica centralizada
		Comentario comentario = ComentarioDTO.convertToEntity(comentarioDTO, ClienteDTO.convertToEntity(clienteDTO),
				PostDTO.convertToEntity(comentarioDTO.getPostDTO()));

		comentarioRepository.save(comentario);

	}

}
