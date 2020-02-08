package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealList;

import java.util.List;

public class MealDaoImpl implements MealDao {

    private MealList meals;

    public MealDaoImpl() {
        this.meals = MealList.getInstance();
    }

    @Override
    public void addMeal(Meal meal) {
        meals.getMeals().add(meal);
    }

    @Override
    public void deleteMeal(int id) {
        meals.getMeals().removeIf(m -> m.getId()==id);
    }

    @Override
    public void updateMeal(Meal meal) {
        meals.getMeals().removeIf(m -> m.getId() == meal.getId());
        meals.getMeals().add(meal);
    }

    @Override
    public List<Meal> getMeals() {
        return meals.getMeals();
    }

    @Override
    public Meal getMealById(int id) {
        return getMeals().stream()
                .filter(meal -> meal.getId() == id)
                .findAny()
                .get();
    }
}
