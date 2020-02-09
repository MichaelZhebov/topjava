package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImplMemory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final long serialVersionUID = 1L;
    private static String INSERT_OR_EDIT = "/meal.jsp";
    private static String LIST_USER = "/meals.jsp";
    private MealDao mealDao;

    @Override
    public void init() throws ServletException {
        super.init();
        mealDao = new MealDaoImplMemory();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        if (action == null) {
            log.debug("redirect to meals");
            forward = LIST_USER;
            List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
            request.setAttribute("meals", mealsTo);
        } else {
            switch (action) {
                case "delete": {
                    int mealId = Integer.parseInt(request.getParameter("mealId"));
                    mealDao.delete(mealId);
                    log.debug("meal with id " + mealId + " deleted");
                    List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                    request.setAttribute("meals", mealsTo);
                    response.sendRedirect(request.getContextPath() + "/meals");
                    return;
                }
                case "edit": {
                    forward = INSERT_OR_EDIT;
                    int mealId = Integer.parseInt(request.getParameter("mealId"));
                    Meal meal = mealDao.getById(mealId);
                    log.debug("redirect to update meal");
                    request.setAttribute("meal", meal);
                    break;
                }
                case "insert": {
                    forward = INSERT_OR_EDIT;
                    log.debug("redirect to add meal");
                    break;
                }
                default: {
                    List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
                    request.setAttribute("meals", mealsTo);
                    log.debug("bad action parameter, redirect to meals");
                    response.sendRedirect(request.getContextPath() + "/meals");
                    return;
                }
            }
        }
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        request.setCharacterEncoding("UTF-8");
        log.debug("redirect to meals");
        Meal meal = new Meal();
        meal.setDateTime(LocalDateTime.parse(request.getParameter("date")));
        meal.setDescription(request.getParameter("desc"));
        meal.setCalories(Integer.parseInt(request.getParameter("calories")));
        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            mealDao.add(meal);
        } else {
            meal.setId(Integer.parseInt(mealId));
            mealDao.update(meal);
        }
        List<MealTo> mealsTo = MealsUtil.filteredByStreams(mealDao.getAll(), LocalTime.MIN, LocalTime.MAX, 2000);
        request.setAttribute("meals", mealsTo);
        response.sendRedirect(request.getContextPath() + "/meals");
    }
}
