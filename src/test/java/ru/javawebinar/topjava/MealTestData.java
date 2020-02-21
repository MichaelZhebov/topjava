package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.Month;

import static java.time.LocalDateTime.*;
import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Meal MEAL1 = new Meal(1, of(2020, Month.FEBRUARY, 20, 7, 30), "breakfast", 500);
    public static final Meal MEAL2 = new Meal(2, of(2020, Month.FEBRUARY, 20, 14, 30), "lunch", 1000);
    public static final Meal MEAL3 = new Meal(3, of(2020, Month.FEBRUARY, 20, 19, 0), "dinner", 1500);
    public static final Meal MEAL4 = new Meal(4, of(2020, Month.FEBRUARY, 22, 10, 0), "breakfast", 350);
    public static final Meal MEAL5 = new Meal(5, of(2020, Month.FEBRUARY, 22, 13, 25), "lunch", 800);
    public static final Meal MEAL6 = new Meal(6, of(2020, Month.FEBRUARY, 22, 20, 30), "dinner", 500);
    public static final Meal MEAL7 = new Meal(7, of(2020, Month.FEBRUARY, 24, 7, 30), "breakfast", 500);
    public static final Meal MEAL8 = new Meal(8, of(2020, Month.FEBRUARY, 24, 21, 30), "diner", 1000);

    public static Meal getNew() {
        return new Meal(null, of(2020, Month.MARCH, 12, 10, 0), "New Meal, Desc", 1000);
    }

    public static Meal getUpdated() {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("Updated Desc");
        updated.setCalories(330);
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "user_id");
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparatorIgnoringFields("user_id").isEqualTo(expected);
    }
}
