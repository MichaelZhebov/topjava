package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.AbstractUserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.Profiles.POSTGRES_DB;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles({POSTGRES_DB, DATAJPA})
public class UserServiceDatajpaTest extends AbstractUserServiceTest {

    @Test
    public void getUserMeals() throws Exception {
        User userDB = service.getWithMeal(USER_ID);
        MEAL_MATCHER.assertMatch(userDB.getMeals(), List.of(MEAL1, MEAL2, MEAL3, MEAL4, MEAL5, MEAL6, MEAL7));
    }
}
