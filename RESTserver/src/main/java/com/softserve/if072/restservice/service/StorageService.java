package com.softserve.if072.restservice.service;

import com.softserve.if072.common.model.ShoppingList;
import com.softserve.if072.common.model.Storage;
import com.softserve.if072.common.model.dto.HistoryDTO;
import com.softserve.if072.common.model.dto.StorageDTO;
import com.softserve.if072.restservice.dao.mybatisdao.StorageDAO;
import com.softserve.if072.restservice.exception.DataNotFoundException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * The StorageService class is used to hold business logic for working with the storage DAO
 *
 * @author Roman Dyndyn
 */
@Service
public class StorageService {
    private static final Logger LOGGER = LogManager.getLogger(StorageService.class);
    private StorageDAO storageDAO;
    private ShoppingListService shoppingListService;
    private HistoryService historyService;

    @Autowired
    public StorageService(StorageDAO storageDAO, ShoppingListService shoppingListService,
                          HistoryService historyService) {
        this.storageDAO = storageDAO;
        this.shoppingListService = shoppingListService;
        this.historyService = historyService;
    }

    public List<Storage> getByUserId(int user_id) throws DataNotFoundException {
        List<Storage> list = storageDAO.getByUserID(user_id);
        if (CollectionUtils.isNotEmpty(list)) {
            return list;
        } else {
            throw new DataNotFoundException(String.format("Storages of user with id %d not found", user_id));
        }
    }

    public Storage getByProductId(int product_id) {
        return storageDAO.getByProductID(product_id);
    }

    public void insert(Storage storage) {
        if (storage != null)
            storageDAO.insert(storage);
    }

    public void insert(int userId, int productId, int amount) {
        storageDAO.insertInParts(userId, productId, amount);
    }

    public void update(Storage storage) {
        if (storage.getAmount() < 0) {
            LOGGER.error("Illegal argument: amount < 0");
            return;
        }

        Storage storageDB = storageDAO.getByProductID(storage.getProduct().getId());
        int diff;
        if((diff = storageDB.getAmount() - storage.getAmount()) > 0) {
            addToHistory(storage, diff);
        }
        if (storage.getEndDate() != null) {
            storageDAO.update(storage);
        } else {
            storageDAO.updateAmount(storage);
        }
        if (storage.getAmount() <= 1) {
            shoppingListService.insert(new ShoppingList(storage.getUser(), storage.getProduct(), 1));
        }
    }

    public void update(StorageDTO storageDTO) {
        if (storageDTO.getAmount() < 0) {
            LOGGER.error("Illegal argument: amount < 0");
            return;
        }

        Storage storage = storageDAO.getByProductID(storageDTO.getProductId());
        if(storage == null){
            LOGGER.error(String.format("Storage with product id %d doesn't exist", storageDTO.getProductId()));
            return;
        }

        int diff;
        if((diff = storage.getAmount() - storageDTO.getAmount()) > 0) {
            addToHistory(storage, diff);
        }
        storage.setAmount(storageDTO.getAmount());
        storageDAO.updateAmount(storage);

        if (storage.getAmount() <= 1) {
            shoppingListService.insert(new ShoppingList(storage.getUser(), storage.getProduct(), 1));
        }
    }

    public void delete(Storage storage) {
        if (storage != null) {
            storageDAO.delete(storage);
        } else {
            LOGGER.error("Illegal argument: storage == null");
            return;
        }
    }

    private void addToHistory(Storage storage, int diff){
            HistoryDTO historyDTO = new HistoryDTO.Builder()
                    .userId(storage.getUser().getId())
                    .productId(storage.getProduct().getId())
                    .amount(diff)
                    .usedDate(new Timestamp(System.currentTimeMillis()))
                    .build();
            historyService.insert(historyDTO);

    }
}
