
package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @PostMapping("")
    public String create(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            service.update(meal, userId);
        } else {
            service.create(meal, userId);
        }
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model) {
        Meal meal = service.get(id, userId);
        assureIdConsistent(meal, id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String createNew(Model model) {
        model.addAttribute("meal",
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        return "mealForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        service.delete(id, userId);
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        log.info("getBetween dates({} - {}) time({} - {}) for user {}", startDate, endDate, startTime, endTime, userId);
        List<Meal> mealsDateFiltered = service.getBetweenInclusive(startDate, endDate, userId);
        model.addAttribute("meals",
                MealsUtil.getFilteredTos(mealsDateFiltered, SecurityUtil.authUserCaloriesPerDay(), startTime, endTime));
        return "meals";
    }

    @GetMapping()
    public String getAll(Model model) {
        log.info("getAll for user {}", userId);
        model.addAttribute("meals",
                MealsUtil.getTos(super.service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
        return "meals";
    }
}
