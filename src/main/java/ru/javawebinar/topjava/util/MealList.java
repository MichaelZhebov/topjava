package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class MealList {

    private static AtomicInteger counter = new AtomicInteger(0);

    private static MealList instance = null;

    private List<Meal> meals;

    private MealList() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        meals = Collections.synchronizedList(new ArrayList<>(Arrays.asList(
                new Meal(counter.incrementAndGet(),LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(counter.incrementAndGet(),LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(counter.incrementAndGet(),LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(counter.incrementAndGet(),LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(counter.incrementAndGet(),LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(counter.incrementAndGet(),LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(counter.incrementAndGet(),LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)))
        );
    }

    public static MealList getInstance() {
        if (instance == null) {
            synchronized (MealList.class) {
                if (instance == null) {
                    instance = new MealList();
                }
            }
        }
        return instance;
    }

    public List<Meal> getMeals() {
        return meals;
    }

    public static AtomicInteger getCounter() {
        return counter;
    }
}
