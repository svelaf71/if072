package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Category;
import com.softserve.if072.common.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * The controller contains methods that handle http-requests from the category's page
 *
 * @author Pavlo Bendus
 */

@Controller
@RequestMapping("/category")
@PropertySource({"classpath:application.properties", "classpath:message.properties"})
public class CategoryPageController extends BaseController{

    @Value("${application.restCategoryURL}")
    private String restCategoryURL;

    @Value("${category.added}")
    private String categoryAdded;

    private static final Logger LOGGER = LogManager.getLogger(CategoryPageController.class);

    /**
     * Method for mapping on default categories url
     * shows the list of available categories and allows to add a new one
     *
     * @param model with data for view
     * @return .jsp page
     */
    @GetMapping
    public String getPage(ModelMap model) {

        RestTemplate restTemplate = getRestTemplate();
        List<Category> categories = restTemplate.getForObject(restCategoryURL + getCurrentUser().getId(), List.class);
        LOGGER.info(categories);
        model.addAttribute("categories", categories);

        return "category";
    }

    /**
     * Method for mapping on page for adding the category
     *
     * @param model allows to create a new default category object
     * @return .jsp page
     */

    @GetMapping("/add")
    public String addCategory(ModelMap model) {

        model.addAttribute("category", new Category());

        return "addCategory";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute Category category, ModelMap model) {

        RestTemplate restTemplate = getRestTemplate();
        category.setUser(getCurrentUser());
        category.setEnabled(true);

        restTemplate.postForObject(restCategoryURL, category, Category.class);
        model.addAttribute("successMessage", categoryAdded);

        return "redirect:/category";
    }

    /**
     * Method for mapping on page for editing
     *
     * @param id for getting the category from rest-service
     * @param model for forming model that will be edited
     * @return .jsp page
     */

    @GetMapping(value="/edit")
    public String editCategory(@RequestParam int id, ModelMap model) {

        RestTemplate restTemplate = getRestTemplate();
        Category category = restTemplate.getForObject(restCategoryURL + "id/" + id, Category.class);
        LOGGER.info("Category were opened for updating: " + category);
        model.addAttribute("category", category);

        return "editCategory";
    }

    /**
     * Method gets edited category and sends a request to the rest-service to update database
     *
     * @param category edited category
     * @return redirect to the categories list page
     */

    @PostMapping(value = "/edit")
    public String editCategory(@ModelAttribute Category category) {

        RestTemplate restTemplate = getRestTemplate();
        category.setUser(getCurrentUser());
        restTemplate.put(restCategoryURL, category, Category.class);
        LOGGER.info("Category " + category.getName() + " was updated");
        return "redirect:/categories/";
    }

    /**
     * Method for deleting category from the list
     *
     * @param id id of category that will be deleted
     * @return redirect to the categories list
     */

    @PostMapping(value = "/delete")
    public void deleteCategory(@RequestParam int id) {
        RestTemplate restTemplate = getRestTemplate();
        restTemplate.delete(restCategoryURL + id);
    }
}
