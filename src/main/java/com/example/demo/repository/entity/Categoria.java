package com.example.demo.repository.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "categories")
public class Categoria {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "cat_name")
	private String name;
	@Column(name = "cat_description")
	private String description;

	// Representa la relacion
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Post> postsList;

	public Categoria() {
		this.postsList = new ArrayList<>();
	}

	public Categoria(Long id, String nombre, String descripcion) {
		super();
		this.id = id;
		this.name = nombre;
		this.description = descripcion;
		this.postsList = new ArrayList<>();
	}

}
