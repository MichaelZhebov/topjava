package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.DateTimeUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;
import static ru.javawebinar.topjava.web.SecurityUtil.authUserId;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealTo> getAll() {
        log.info("getAll");
        return service.getAll(authUserId());
    }

    public List<MealTo> getFiltered(String startDate, String endDate, String startTime, String endTime) {
        log.info("get filtered");
        LocalDate sDate = startDate == null ? LocalDate.MIN : startDate.equals("") ? LocalDate.MIN : LocalDate.parse(startDate);
        LocalDate eDate = endDate == null ? LocalDate.MAX : endDate.equals("") ? LocalDate.MAX : LocalDate.parse(endDate);
        LocalTime sTime = startTime == null ? LocalTime.MIN : startTime.equals("") ? LocalTime.MIN : LocalTime.parse(startTime);
        LocalTime eTime = endTime == null ? LocalTime.MAX : endTime.equals("") ? LocalTime.MAX : LocalTime.parse(endTime);
        return service.getBetweenDate(sDate, eDate, authUserId()).stream()
                .filter(mealTo -> DateTimeUtil.isBetweenTime(mealTo.getDateTime(), sTime, eTime))
                .collect(Collectors.toList());
    }

    public Meal get(int id) {
        log.info("get {}", id);
        return service.get(id,authUserId());
    }

    public Meal create(Meal meal) {
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal, authUserId());
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id, authUserId());
    }

    public void update(Meal meal, int id) {
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal, authUserId());
    }

}