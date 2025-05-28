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

@Data
@Entity
@Table(name = "messages")
public class Mensaje {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String content;
	@Column(name = "m_channel")
	private String channel;
	@Column(name = "send_date")
	private LocalDateTime sendDate;
	@ManyToOne
	@JoinColumn(name = "client_id")
	private Cliente client;
}
