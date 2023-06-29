package com.example.RecipeHub.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByEmail(String email);
	
	@Query(value = "select * from User u where u.full_name like %:query% or u.email like %:query% order by :sortBy :direction", nativeQuery=true)
	public Page<User> filterUserAndPagination(@Param("query") String query,@Param("sortBy") String sortBy,@Param("direction") String direction, Pageable pageable);
	
	@Query(value="select u from User u where u.fullName like %:query% or u.email like %:query%")
	public List<User> filterUser(@Param("query") String query);
}
