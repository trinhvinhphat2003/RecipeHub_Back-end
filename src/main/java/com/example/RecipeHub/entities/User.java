package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.LoginType;
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
	@Column(name = "user_id")
	private Long userId;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = true)
	private String password;

	@Column(name = "full_name")
	private String fullName;

	@Column(name = "profile_image")
	private String profileImage;

	@Column(name = "birthday")
	private Date birthday;

	@Enumerated(EnumType.STRING)
	private Role role;

	@Enumerated(EnumType.STRING)
	private Gender gender;
	
	@Column(name = "login_type")
	@Enumerated(EnumType.STRING)
	private LoginType loginType;

	@Column(name = "enable")
	private boolean enable;
	
	@Column(name = "blocked")
	private boolean blocked;

	public boolean isEnable() {
		return enable;
	}

	public void setEnable(boolean enable) {
		this.enable = enable;
	}

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "friends", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "friend_id"))
	private List<User> friends = new ArrayList<>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL)
	private List<Recipe> recipes = new ArrayList<>();

	@OneToMany(mappedBy = "receiver", cascade = CascadeType.ALL)
	private List<FriendshipRequest> friendshipRequests = new ArrayList<>();
	
	public User(Long userId, String email, String password, String fullName, String profileImage, Date birthday,
			Role role, Gender gender, LoginType loginType, boolean enable, boolean blocked, List<User> friends,
			List<Recipe> recipes, List<FriendshipRequest> friendshipRequests) {
		super();
		this.userId = userId;
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.profileImage = profileImage;
		this.birthday = birthday;
		this.role = role;
		this.gender = gender;
		this.loginType = loginType;
		this.enable = enable;
		this.blocked = blocked;
		this.friends = friends;
		this.recipes = recipes;
		this.friendshipRequests = friendshipRequests;
	}

	public User(String email, String password, Role role, String fullName, Gender gender, boolean enable, Date birthday, String profileImage, LoginType loginType, boolean blocked) {
		super();
		this.email = email;
		this.password = password;
		this.role = role;
		this.fullName = fullName;
		this.gender = gender;
		this.enable = enable;
		this.birthday = birthday;
		this.profileImage = profileImage;
		this.loginType = loginType;
		this.blocked = blocked;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public User() {
		super();
	}

	public LoginType getLoginType() {
		return loginType;
	}

	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}

	public boolean isBlocked() {
		return blocked;
	}

	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}	

	//

}
