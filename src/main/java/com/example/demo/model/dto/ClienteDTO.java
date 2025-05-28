package com.example.demo.model.dto;

import java.time.LocalDate;

import lombok.Data;

@Data
public class ClienteDTO {

	private Long id;
	private String nomUsu;
	private LocalDate fechaReg;

	public ClienteDTO(Long id, String nomUsu, LocalDate fechaReg) {
		this.id = id;
		this.nomUsu = nomUsu;
		this.fechaReg = fechaReg;
	}

	public ClienteDTO() {

	}
}
