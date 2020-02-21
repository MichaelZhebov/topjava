package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static ru.javawebinar.topjava.MealTestData.*;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService mealService;

    @Test
    public void create() {
        Meal newMeal = getNew();
        Meal created = mealService.create(newMeal, USER_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, USER_ID), newMeal);
    }

    @Test(expected = DataAccessException.class)
    public void duplicateDateCreateBySameUser() {
        mealService.create(new Meal(null, of(2020, Month.FEBRUARY, 20, 7, 30), "duplicate", 1000), USER_ID);
    }

    @Test
    public void duplicateDateCreateByAnotherUser() {
        Meal newMeal = getNew();
        newMeal.setDateTime(MEAL_USER_1.getDateTime());
        Meal created = mealService.create(newMeal, ADMIN_ID);
        Integer newId = created.getId();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(mealService.get(newId, ADMIN_ID), newMeal);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        mealService.update(updated, USER_ID);
        assertMatch(mealService.get(updated.getId(), USER_ID), updated);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal updated = getUpdated();
        updated.setId(100);
        mealService.update(updated, USER_ID);
    }

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL_USER_1.getId(), USER_ID);
        assertMatch(meal, MEAL_USER_1);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        mealService.get(100, USER_ID);
    }

    @Test
    public void delete() {
        mealService.delete(MEAL_USER_1.getId(), USER_ID);
        List<Meal> meals = mealService.getAll(USER_ID);
        assertMatch(meals, MEAL_USER_5, MEAL_USER_4, MEAL_USER_3, MEAL_USER_2);
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotFound() {
        mealService.delete(100, USER_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> mealList = mealService.getBetweenHalfOpen(
                LocalDate.of(2020, Month.FEBRUARY, 21),
                LocalDate.of(2020, Month.FEBRUARY, 25),
                USER_ID);
        assertMatch(mealList, MEAL_USER_5, MEAL_USER_4);
    }

    @Test
    public void getAll() {
        List<Meal> mealList = mealService.getAll(USER_ID);
        assertMatch(mealList, MEAL_USER_5, MEAL_USER_4, MEAL_USER_3, MEAL_USER_2, MEAL_USER_1);
    }


}