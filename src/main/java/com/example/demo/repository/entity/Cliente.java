package com.example.demo.repository.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;

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
@Table(name = "users")
public class Cliente implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private static final long serialVersionUID = 1L;
	private String username;
	private String pass;
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private ArrayList<Post> postsList;
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private ArrayList<Mensaje> msgsList;
	@OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
	private ArrayList<Comentario> commentsList;

	public Cliente(Long id, String nomUsu, String passUsu, LocalDateTime createdAt) {
		this.id = id;
		this.username = nomUsu;
		this.pass = passUsu;
		this.createdAt = createdAt;
		this.postsList = new ArrayList<>();
		this.msgsList = new ArrayList<>();
		this.commentsList = new ArrayList<>();
	}

	public Cliente() {
		this.postsList = new ArrayList<>();
		this.msgsList = new ArrayList<>();
		this.commentsList = new ArrayList<>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		return Objects.equals(id, other.id)
				&& Objects.equals(username, other.username) && Objects.equals(pass, other.pass);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, username, pass);
	}

}
