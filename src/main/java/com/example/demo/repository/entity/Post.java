package com.example.demo.repository.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "posts")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String title;
	private String content;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@ManyToOne
	@JoinColumn(name = "client_id")
	@ToString.Exclude
	private Cliente client;
	@ManyToOne()
	@JoinColumn(name = "category_id")
	@ToString.Exclude
	private Categoria category;
	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Comentario> commentsList;

	public Post() {
		this.commentsList = new ArrayList<>();
	}
}
