package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealList;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealController extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_USER = "/meals.jsp";
    private MealDaoImpl mealDaoImpl;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public MealController() {
        super();
        mealDaoImpl = new MealDaoImpl();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String forward="";
        String action = request.getParameter("action");

        if (action == null){
            log.debug("redirect to meals");
            forward = LIST_USER;
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(MealList.getInstance().getMeals(), LocalTime.MIN,LocalTime.MAX,2000);
            request.setAttribute("meals", mealsTo);
        } else if (action.equalsIgnoreCase("delete")){
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealDaoImpl.deleteMeal(mealId);
            log.debug("meal with id " + mealId + " deleted");
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDaoImpl.getMeals(), LocalTime.MIN,LocalTime.MAX,2000);
            request.setAttribute("meals", mealsTo);
            response.sendRedirect(request.getContextPath() + "/meals");
            return;
        } else if (action.equalsIgnoreCase("edit")){
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealDaoImpl.getMealById(mealId);
            log.debug("redirect to update meal");
            request.setAttribute("meal", meal);
        } else {
            forward = INSERT_OR_EDIT;
            log.debug("redirect to add meal");
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("redirect to meals");
        Meal meal = new Meal();

        meal.setDateTime(LocalDateTime.parse(request.getParameter("date")));
        meal.setDescription(request.getParameter("desc"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            meal.setId(MealList.getCounter().incrementAndGet());
            mealDaoImpl.addMeal(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealDaoImpl.updateMeal(meal);
        }
        RequestDispatcher view = request.getRequestDispatcher(LIST_USER);
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDaoImpl.getMeals(), LocalTime.MIN,LocalTime.MAX,2000);
        request.setAttribute("meals", mealsTo);
        view.forward(request, response);
    }
}
