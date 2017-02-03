package com.softserve.if072.restservice.service.impl;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.restservice.Exception.DataSourceException;
import com.softserve.if072.restservice.dao.mybatisdao.ShoppingListDAO;
import com.softserve.if072.restservice.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by dyndyn on 21.01.2017.
 */
public class ShoppingListServiceImpl implements ShoppingListService {
    private ShoppingListDAO shoppingListDAO;

    @Autowired
    public ShoppingListServiceImpl(ShoppingListDAO shoppingListDAO) {
        this.shoppingListDAO = shoppingListDAO;
    }

    @Override
    public List<ShoppingList> getByUserId(int user_id) throws DataSourceException {
        List<ShoppingList> list = shoppingListDAO.getByUserID(user_id);
        if (list != null && !list.isEmpty()) {
            return list;
        } else {
            throw new DataSourceException("ShoppingList not found");
        }
    }

    @Override
    public void insert(ShoppingList shoppingList) {
        shoppingListDAO.insert(shoppingList);
    }

    @Override
    public void update(ShoppingList shoppingList) throws DataSourceException {
        shoppingListDAO.update(shoppingList);
    }

    @Override
    public void delete(ShoppingList shoppingList) throws DataSourceException {
        if (shoppingList != null) {
            shoppingListDAO.delete(shoppingList);
        } else {
            throw new DataSourceException("ShoppingList was not found");
        }
    }
}
