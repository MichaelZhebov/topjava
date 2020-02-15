package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class UsersUtil {
    public static final int DEFAULT_CALORIES_PER_DAY = 2000;

    public static final List<User> USERS = Arrays.asList(
            new User(null, "Michael", "Michael@mail.com", "1234", Role.ROLE_USER),
            new User(null, "Irina", "Irina@mail.com", "1234", Role.ROLE_USER),
            new User(null, "Yura", "Yura@mail.com", "1234", Role.ROLE_USER),
            new User(null, "Andrei", "Andrei@mail.com", "1234", Role.ROLE_USER)
    );

    public static List<User> getSorted() {
        return USERS.stream()
                    .sorted(Comparator.comparing(User::getName))
                    .collect(Collectors.toList());
    }
 }
