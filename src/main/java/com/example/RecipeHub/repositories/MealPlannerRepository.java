package com.example.RecipeHub.repositories;

import com.example.RecipeHub.entities.MealPlanner;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;

@Repository
public interface MealPlannerRepository extends JpaRepository<MealPlanner,Long> {
    MealPlanner findMealPlannerByDateAndUser_UserId(Date date, Long userId);
    MealPlanner findMealPlannerByUser_UserIdAndDateBeforeAndDateAfter(long userId, Date dayBefore, Date dayAfter);

}
