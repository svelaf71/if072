package com.softserve.if072.mvcapp.controller;

import com.softserve.if072.common.model.Product;
import com.softserve.if072.common.model.Store;
import com.softserve.if072.common.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The class contains methods that handle the http requests from the stores`s page
 *
 * @author Nazar Vynnyk
 */

@Controller
@PropertySource(value = {"classpath:application.properties"})
public class StorePageController extends BaseController {

    public static final Logger LOGGER = LogManager.getLogger(StorePageController.class);

    private int idStore;
    @Value("${application.restStoreURL}")
    private String storeUrl;

    @Value("${application.restProductURL}")
    private String productUrl;

    @Value("${service.user.current}")
    private String getCurrentUser;

    @GetMapping("/stores/")
    public String getAllStoresByUserId(Model model) {
        final String uri = storeUrl + "/user/{userId}";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("userId", userId);
            List stores = restTemplate.getForObject(uri, List.class, param);
            model.addAttribute("stores", stores);
            LOGGER.info(String.format("Stores of user with id %d were found", userId));

            return "allStores";

        } catch (Exception e) {
            LOGGER.error(Arrays.toString(e.getStackTrace()));
            return "redirect:/home";
        }
    }

    @GetMapping("/addStore")
    public String addStore(Model model) {
        model.addAttribute("store", new Store());
        LOGGER.info("Adding Store");
        return "addStore";
    }

    @PostMapping(value = "/addStore")
    public String addStore(@ModelAttribute("store") Store store) {
        final String uri = storeUrl + "/";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        try {
            store.setUser(user);
            store.setEnabled(true);
            restTemplate.postForObject(uri, store, Store.class);
            LOGGER.info(String.format("Store of user %d was added", user.getId()));

            return "redirect:/stores/";

        } catch (Exception e) {
            LOGGER.error("Stores not added");
            return "addStore";
        }
    }

    @GetMapping("/stores/storeProducts")
    public String getAllProductsByStoreId(@RequestParam("storeId") String storeId, ModelMap model) {
        final String uri = storeUrl + "/{storeId}/storeProducts/{userId}";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("storeId", Integer.parseInt(storeId));
            param.put("userId", userId);
            List products = restTemplate.getForObject(uri, List.class, param);
            model.addAttribute("products", products);
            LOGGER.info(String.format("Products from store %s were found", storeId));

            return "product";

        } catch (Exception e) {
            LOGGER.error(String.format("Products user %d from store %s were not found", userId, storeId));
            return "redirect:/stores/";
        }
    }

    @GetMapping("/addProductsToStore")
    public String addProductsToStore(@RequestParam("storeId") String storeId, @RequestParam("name") String name,
                                     @RequestParam("adr") String adr, ModelMap model, ModelMap model2 ) {
        final String storeUri = storeUrl + "/{storeId}/notMappedProducts/{userId}";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        int userId = user.getId();
        int returnedStoreId = Integer.parseInt(storeId);
   System.out.println(adr + name +" ++++++++++++++++++++++++++++=+++");
        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("storeId", returnedStoreId);
            param.put("userId", userId);
            Product[] productResult = restTemplate.getForObject(storeUri, Product[].class, param);
            List<Product> products = Arrays.asList(productResult);

            Store myStore = new Store();
            myStore.setId(returnedStoreId);
            myStore.setName(name);
            myStore.setAddress(adr);
//            myStore.setProducts(products);
            model.addAttribute("myStore", myStore);
            model2.addAttribute("products", products);

            LOGGER.info(String.format("Products of user %d in store %s found", userId, storeId));

            return "addProductsToStore";

        } catch (Exception e) {
            LOGGER.error("Stores and products not found");
            return "redirect:/stores/";
        }
    }

    @PostMapping(value = "/addProductsToStore")
    public String addProductsToStore(@ModelAttribute("myStore") Store myStore, @ModelAttribute("products")
                                     List<Product> products ) {

            final String uri = storeUrl + "/{storeId}";
            try {
                RestTemplate restTemplate = getRestTemplate();
         

            return "redirect:/stores/";
        } catch (Exception e) {
            LOGGER.error("Stores and products not found");
            return "redirect:/stores/";
        }

    }

    @PostMapping(value = "/stores/delStore")
    public String deleteStore(@RequestParam("storeId") int storeId) {
        final String uri = storeUrl + "/{storeId}";
        RestTemplate restTemplate = getRestTemplate();

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("storeId", storeId);
            restTemplate.put(uri, Store.class, param);
            LOGGER.info(String.format("Store with id %d was deleted", storeId));

            return "redirect:/stores/";

        } catch (Exception e) {
            LOGGER.error(String.format("Store with id %d was not deleted", storeId));
            return "redirect:/stores/";
        }
    }

    @GetMapping("/editStore")
    public String editStore(@RequestParam("storeId") String storeId, ModelMap model) {
        final String uri = storeUrl + "/{storeId}";
        RestTemplate restTemplate = getRestTemplate();
        idStore = Integer.parseInt(storeId);

        try {
            Map<String, Integer> param = new HashMap<>();
            param.put("storeId", Integer.parseInt(storeId));
            Store store = restTemplate.getForObject(uri, Store.class, param);
            model.addAttribute("store", store);
            LOGGER.info("Editing Store         " + idStore);
            return "editStore";

        } catch (Exception e) {
            LOGGER.error(String.format("Store with id %d is not possible to edit", storeId));
            return "redirect:/stores/";
        }
    }

    @PostMapping("/editStore")
    public String editStore(@ModelAttribute("store") Store store) {
        store.setId(idStore);
        int storeId = store.getId();
        final String uri = storeUrl + "/update";
        RestTemplate restTemplate = getRestTemplate();
        User user = restTemplate.getForObject(getCurrentUser, User.class);
        store.setUser(user);
        LOGGER.info(store.toString());
        try {
            restTemplate.put(uri, store, Store.class);
            LOGGER.info(String.format("Store with id %d was updated", store.getId()));
            return "redirect:/stores/";

        } catch (Exception e) {
            LOGGER.error(String.format("Store with id %d was not updated", store.getId()));
            return "redirect:/stores/";
        }
    }

}
