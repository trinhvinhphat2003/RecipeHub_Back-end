package com.example.RecipeHub.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
	Optional<Tag> findByTagName(String tagName);
	
    @Query(value = "SELECT t.* FROM tag t " +
            "INNER JOIN recipe_have_tag re ON re.tag_id = t.tag_id " +
            "INNER JOIN recipe r ON re.recipe_id = r.recipe_id " +
            "WHERE t.tag_name = :tagName AND r.user_id = :userId " +
            "GROUP BY t.tag_id", nativeQuery = true)
	Optional<Tag> findOneByUserIdAndName(@Param("tagName") String tagName, @Param("userId") Long userId);
    
    @Query(value = "SELECT t.* FROM tag t\r\n"
    		+ "inner join recipe_have_tag re on re.tag_id = t.tag_id\r\n"
    		+ "inner join recipe r on re.recipe_id = r.recipe_id\r\n"
    		+ "where r.user_id = :userId \r\n"
    		+ "group by t.tag_id", nativeQuery = true)
    List<Tag> findByUserId(@Param("userId") Long userId);
}
