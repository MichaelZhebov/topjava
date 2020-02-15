package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            //Meal Controller tests:
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            System.out.println(mealRestController.getAll());
            System.out.println(mealRestController.get(1));
            //System.out.println(mealRestController.get(3)); // NotFoundException
            mealRestController.create(new Meal(authUserId(), LocalDateTime.of(2020, Month.FEBRUARY, 13, 10, 0), "Test", 5000));
            //mealRestController.create(new Meal(2, LocalDateTime.of(2020, Month.FEBRUARY, 13, 10, 0), "Test", 5000)); //NotFoundException
            System.out.println(mealRestController.getAll());
            mealRestController.delete(2);
            //mealRestController.delete(7); //NotFoundException
            System.out.println(mealRestController.getAll());
            mealRestController.update(mealRestController.get(8), 8);
            //mealRestController.update(mealRestController.get(8), 5); //IllegalArgumentException
            System.out.println(mealRestController.getAll());
        }
    }
}
