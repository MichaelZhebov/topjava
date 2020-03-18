package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;

@Controller
@RequestMapping(value = "/meals")
public class JspMealController {

    @Autowired
    private MealRestController mealController;

    @GetMapping()
    public String getAll(Model model,
                           @RequestParam(name = "startDate") @Nullable String sDate,
                           @RequestParam(name = "endDate") @Nullable String eDate,
                           @RequestParam(name = "startTime") @Nullable String sTime,
                           @RequestParam(name = "endTime") @Nullable String eTime) {
        LocalDate startDate = parseLocalDate(sDate);
        LocalDate endDate = parseLocalDate(eDate);
        LocalTime startTime = parseLocalTime(sTime);
        LocalTime endTime = parseLocalTime(eTime);
        model.addAttribute("meals", mealController.getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping("create")
    public String сreate(Model model) {
        Meal meal = new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("update")
    public String update(Model model, @RequestParam(name = "id") Integer id) {
        model.addAttribute("meal", mealController.get(id));
        return "mealForm";
    }

    @PostMapping("update")
    public String updateOrCreate(HttpServletRequest request) {
        if (request.getParameter("id") != "") {
            mealController.update(get(request), getId(request));
        } else {
            mealController.create(get(request));
        }
        return "redirect:/meals";
    }

    @GetMapping("delete")
    public String delete(@RequestParam(name = "id") int id) {
        mealController.delete(id);
        return "redirect:/meals";
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }

    private Meal get(HttpServletRequest request) {
        return new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
    }
}
