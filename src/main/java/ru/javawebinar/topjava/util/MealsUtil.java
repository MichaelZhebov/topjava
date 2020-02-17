package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class MealsUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 510),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(1, LocalDateTime.of(2020, Month.FEBRUARY, 20, 10, 0), "Завтрак", 500),
            new Meal(1, LocalDateTime.of(2020, Month.FEBRUARY, 20, 20, 0), "Ужин", 700),
            new Meal(1, LocalDateTime.of(2020, Month.FEBRUARY, 20, 13, 0), "Обед", 450),
            new Meal(1, LocalDateTime.of(2020, Month.MARCH, 15, 8, 0), "Завтрак", 1000),
            new Meal(1, LocalDateTime.of(2020, Month.MARCH, 15, 14, 0), "Обед", 200),
            new Meal(1, LocalDateTime.of(2020, Month.MARCH, 15, 16, 0), "Полдник", 800),
            new Meal(1, LocalDateTime.of(2020, Month.MARCH, 15, 22, 0), "Ужин", 350),
            new Meal(1, LocalDateTime.of(2020, Month.APRIL, 5, 7, 0), "Завтрак", 200),
            new Meal(1, LocalDateTime.of(2020, Month.APRIL, 5, 12, 0), "Обед", 350),
            new Meal(1, LocalDateTime.of(2020, Month.APRIL, 5, 17, 0), "Полдник", 500),
            new Meal(1, LocalDateTime.of(2020, Month.APRIL, 5, 21, 0), "Ужин", 600)
    );

    public static List<MealTo> getTos(Collection<Meal> meals, int caloriesPerDay) {
        return filteredByStreams(meals, caloriesPerDay, meal -> true);
    }

    public static List<MealTo> getFilteredTos(Collection<Meal> meals, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return filteredByStreams(meals, caloriesPerDay, meal -> DateTimeUtil.isBetweenInclusive(meal.getTime(), startTime, endTime));
    }

    public static List<MealTo> filteredByStreams(Collection<Meal> meals, int caloriesPerDay, Predicate<Meal> filter) {
        Map<LocalDate, Integer> caloriesSumByDate = meals.stream()
                .collect(
                        Collectors.groupingBy(Meal::getDate, Collectors.summingInt(Meal::getCalories))
//                      Collectors.toMap(Meal::getDate, Meal::getCalories, Integer::sum)
                );

        return meals.stream()
                .filter(filter)
                .map(meal -> createTo(meal, caloriesSumByDate.get(meal.getDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private static MealTo createTo(Meal meal, boolean excess) {
        return new MealTo(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories(), excess);
    }
}
