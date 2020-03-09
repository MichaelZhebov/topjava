package ru.javawebinar.topjava.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.Profiles.DATAJPA;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class MealServiceDatajpaTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser() {
        Meal meal = service.getWithUser(MEAL1_ID, USER_ID);
        USER_MATCHER.assertMatch(meal.getUser(), USER);
    }

    @Test
    public void getWithUserNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithUser(1, USER_ID));
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithUser(MEAL1_ID,1));
    }

}
