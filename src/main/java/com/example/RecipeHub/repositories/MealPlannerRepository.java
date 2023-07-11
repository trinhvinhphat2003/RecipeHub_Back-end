package com.example.RecipeHub.repositories;

//<<<<<<< HEAD
import com.example.RecipeHub.entities.MealPlanner;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface MealPlannerRepository extends JpaRepository<MealPlanner,Long> {
    MealPlanner findMealPlannerByDateAndUser_UserId(Date date, Long userId);
    List<MealPlanner> findMealPlannerByUser_UserIdAndDateBeforeAndDateAfter(long userId, Date dayBefore, Date dayAfter);
    @Transactional
    void deleteByDateBefore(Date date);
//=======
//import java.util.Date;
//import java.util.List;
//import java.util.Optional;
//
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Repository;
//
//import com.example.RecipeHub.entities.Meal_planner;
//
//@Repository
//public interface MealPlannerRepository extends JpaRepository<Meal_planner, Long>{
	
	@Query(value = "SELECT * FROM meal_planner m where date(m.date) = :date", nativeQuery = true)
	List<MealPlanner> findByDate(@Param("date") String date);
	@Query(value = "SELECT * FROM meal_planner m where date(m.date) between :from and :to", nativeQuery = true)
	List<MealPlanner> findByDateFromTo(@Param("from") String from,@Param("to") String to);
//>>>>>>> 2f578391f0a7bdb7c5c45d3e6c9720112a4db132
}
