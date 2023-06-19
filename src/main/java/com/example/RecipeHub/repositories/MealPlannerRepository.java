package com.example.RecipeHub.repositories;

import com.example.RecipeHub.entities.MealPlanner;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MealPlannerRepository extends JpaRepository<MealPlanner,Long> {
    MealPlanner findMealPlannerByDateAndUser_UserId(Date date, Long userId);
    List<MealPlanner> findMealPlannerByUser_UserIdAndDateBeforeAndDateAfter(long userId, Date dayBefore, Date dayAfter);
    @Transactional
    void deleteByDateBefore(Date date);
}
