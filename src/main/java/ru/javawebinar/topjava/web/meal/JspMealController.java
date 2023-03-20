
package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
@RequestMapping("/meals")
public class JspMealController extends AbstractMealController {

    @PostMapping
    public String createOrUpdate(HttpServletRequest request) {
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.hasLength(request.getParameter("id"))) {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            update(meal);
        } else {
            create(meal);
        }
        return "redirect:/meals";
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") int id, Model model, HttpServletRequest request) {
        log.info("update meal:");
        Meal meal = get(id);
        assureIdConsistent(meal, id);
        model.addAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping("/create")
    public String createNew(Model model) {
        log.info("create new meal:");
        model.addAttribute("meal",
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000));
        model.addAttribute("action", "new");
        return "mealForm";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") int id, HttpServletRequest request) {
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping("/filter")
    public String getBetween(HttpServletRequest request, Model model) {
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));

        model.addAttribute("meals", getBetween(startDate, startTime, endDate, endTime));
        return "meals";
    }

    @GetMapping
    public String getAll(Model model) {
        model.addAttribute("meals", getAll());
        return "meals";
    }
}
