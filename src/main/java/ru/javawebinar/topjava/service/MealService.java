package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.DateTimeUtil.isBetweenDateTime;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    public Meal create(Meal meal, Integer userId) {
        return repository.save(meal, userId);
    }

    public void delete(int id, Integer userId) {
        checkNotFoundWithId(repository.delete(id, userId), id);
    }

    public Meal get(int id, Integer userId) {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public List<MealTo> getAll(Integer userId) {
        return MealsUtil.getTos(repository.getAll(userId), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public void update(Meal meal, Integer userId) {
        checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }
}