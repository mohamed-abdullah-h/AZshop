package com.main.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class AppUser {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "{name.violation}")
	private String name;
	@Column(columnDefinition = "not null unique")
	@Email(message = "{user.email.invalid.violation}")
	@NotBlank(message = "{user.email.blank.violation}")
	private String email;
	@NotBlank(message = "{user.password.blank.violation}")
	private String password;
	@OneToOne()
	@JsonIgnoreProperties(value = {"user"})
	@Valid
	private Address address;
	@OneToOne
	@JoinColumn(name = "cart_id")
	private ShoppingCart cart;
	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(joinColumns = {
			@JoinColumn(name = "user_id",referencedColumnName = "id")},
			inverseJoinColumns = @JoinColumn(name="role_id",referencedColumnName = "id"))
	private Set<Role> roles = new HashSet<>();
	
	public AppUser(String name,String email,String pass) {
		this.name = name;
		this.email = email;
		this.password = pass;
	}
	
	public void addRole(Role role) {
		roles.add(role);
	}
	public void removeRole(Role role) {
		roles.remove(role);
	}

	@Override
	public String toString() {
		return "AppUser [id=" + id + ", name=" + name + ", email=" + email + ", password=" + password + ", address="
				+ address + ", cart=" + cart+ ", roles=" + roles + "]";
	}
	
}
