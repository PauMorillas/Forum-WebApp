package com.example.demo.repository.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "comments")
public class Comentario {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@ManyToOne
	@JoinColumn(name = "client_id")
	@ToString.Exclude
	private Cliente client;
	@ManyToOne
	@JoinColumn(name = "post_id")
	@ToString.Exclude
	private Post post;

	public Comentario() {
	}
}
