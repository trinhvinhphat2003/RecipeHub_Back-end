package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_id")
	private Long ingredient_id;

	@OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
	private List<Recipe_HAVE_Ingredient> recipes = new ArrayList<>();

	@Column(name = "ingredient_name", nullable = false)
	private String ingredientName;

	public Ingredient(String ingredientName) {
		super();
		this.ingredientName = ingredientName;
	}
}
