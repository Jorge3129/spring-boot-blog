package com.sopromadze.blogapi.model;

import com.sopromadze.blogapi.model.audit.UserDateAudit;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotBlank;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "todos", uniqueConstraints = { @UniqueConstraint(columnNames = { "title" }) })
public class Todo extends UserDateAudit {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(name = "title")
	private String title;

	@Column(name = "completed")
	private Boolean completed;

	@Column(name = "user_id")
	private Long userId;
}
