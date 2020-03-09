package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collections;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class UserServiceDatajpaTest extends AbstractUserServiceTest {

    @Test
    public void getUserMeals() throws Exception {
        User userDB = service.getWithMeal(USER_ID);
        MEAL_MATCHER.assertMatch(userDB.getMeals(), List.of(MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test(expected = NotFoundException.class)
    public void getUserMealsNotFound() throws Exception {
        service.getWithMeal(1);
    }

    @Test
    public void getUserMealsEmptyList() throws Exception {
        User userDB = service.getWithMeal(USER_ID_WO_MEALS);
        MEAL_MATCHER.assertMatch(userDB.getMeals(), Collections.emptyList());
    }
}
