package com.example.demo.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class MensajeDTO {
	private Long id;
	private String content;
	private String channel;
	private LocalDate shipDate;
	private ClienteDTO clienteDTO;

	public MensajeDTO() {

	}
}
