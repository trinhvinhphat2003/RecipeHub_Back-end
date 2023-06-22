package com.example.RecipeHub.repositories;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Recipe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

@Repository
public class RecipeCustomRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public List<Recipe> filterByCondition(ArrayList<String> tags, ArrayList<String> ingredients, Integer page,
			Integer size, String sortBy, String direction, String title, String privacyStatus, Boolean isFavorite,
			Long user_id) {
		// initial
		String sql = "select r.* from recipe r \r\n";
		if (tags != null && tags.size() > 0) {
			sql += "left join recipe_have_tag rt on rt.recipe_id = r.recipe_id\r\n"
					+ "left join tag t on rt.tag_id = t.tag_id\r\n";
		}
		if (ingredients != null && ingredients.size() > 0) {
			sql += "left join ingredient i on i.recipe_id = r.recipe_id\n";
		}

		// append title
		sql += "where\n";
		sql += "r.title like :title\n";

		// append privacy status
		if (privacyStatus != null)
			sql += "and r.privacy_status like :privacyStatus\n";

		// append is favorite
		if (isFavorite != null)
			sql += "and r.is_favourite = " + (isFavorite ? 1 : 0) + "\n";

		// append user id
		if (user_id != null) {
			sql += "and r.user_id = " + user_id + "\n";
		}

		// append tags
		if (tags != null && tags.size() > 0) {
			sql += "and ";
			sql += "t.tag_name = :tag" + 0 + " ";
			for (int i = 1; i < tags.size(); i++) {
				sql += "or t.tag_name = :tag" + i + " ";
			}
		}

		// append ingredients
		if (ingredients != null && ingredients.size() > 0) {
			sql += "\nand\n";
			sql += "i.ingredient_name LIKE :ingredient" + 0 + " ";
			for (int i = 1; i < ingredients.size(); i++) {
				sql += "or i.ingredient_name LIKE :ingredient" + i + " ";
			}
		}

		// append group by
		sql += "\ngroup by r.recipe_id ";
		if ((tags != null && tags.size() > 0) || (ingredients != null && ingredients.size() > 0)) {

			// append having
			sql += "\nhaving ";
			if (tags.size() > 0) {
				sql += "count(r.recipe_id) >= " + tags.size() + " ";
			}
			if (ingredients.size() > 0) {
				if (tags.size() > 0) {
					sql += "and ";
				}
				sql += "count(r.recipe_id) >= " + ingredients.size() + " ";
			}
		}

		// append order by
		if (sortBy != null && !sortBy.isEmpty()) {
			sql += "\norder by " + sortBy + " ";
			if (direction != null && !direction.isEmpty()) {
				sql += direction;
			}
		}

		// create query to add data
		Query query = entityManager.createNativeQuery(sql, Recipe.class);

		// add title
		query.setParameter("title", "%" + title + "%");

		// add privacy status
		if (privacyStatus != null)
			query.setParameter("privacyStatus", "%" + privacyStatus + "%");

		// add tags
		if (ingredients != null && ingredients.size() > 0) {
			for (int i = 0; i < tags.size(); i++) {
				query.setParameter("tag" + i, tags.get(i));
			}
		}

		// add ingredients
		if (ingredients != null && ingredients.size() > 0) {
			for (int i = 0; i < ingredients.size(); i++) {
				query.setParameter("ingredient" + i, "%" + ingredients.get(i) + "%");
			}
		}

		// pagination
		query.setFirstResult(page * size);
		query.setMaxResults(size);

		// result
		List<Recipe> recipes = query.getResultList();

		return recipes;
	}
}
