package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long user_id;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = true)
	private String password;

	@Column(name = "full_name")
	private String full_name;

	@Column(name = "profile_image")
	private String profile_image;

	@Column(name = "birthday")
	private Date birthDay;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private List<User> friends = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
	private List<Recipe> recipes = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private List<FriendshipRequest> friendshipRequests = new ArrayList<>();

	public User(String email, String password, Role role, String fullname, Gender gender) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.full_name = fullname;
		this.gender = gender;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return List.of(new SimpleGrantedAuthority(role.name()));
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getProfile_image() {
		return profile_image;
	}

	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}

	public Date getBirthday() {
		return birthDay;
	}

	public void setBirthday(Date birthday) {
		this.birthDay = birthday;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public List<FriendshipRequest> getFriendshipRequests() {
		return friendshipRequests;
	}

	public void setFriendshipRequests(List<FriendshipRequest> friendshipRequests) {
		this.friendshipRequests = friendshipRequests;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public User(Long user_id, String email, String password, String full_name, String profile_image, Date birthday,
			Role role, Gender gender, List<User> friends, List<Recipe> recipes,
			List<FriendshipRequest> friendshipRequests) {
		super();
		this.user_id = user_id;
		this.email = email;
		this.password = password;
		this.full_name = full_name;
		this.profile_image = profile_image;
		this.birthDay = birthday;
		this.role = role;
		this.gender = gender;
		this.friends = friends;
		this.recipes = recipes;
		this.friendshipRequests = friendshipRequests;
	}

	public User() {
		super();
	}
	
	//
	
	

}
