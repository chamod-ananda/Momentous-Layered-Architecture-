package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.ItemBO;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dao.custom.impl.ItemDAOImpl;
import com.example.momentous.momentous_finalproject.dto.ItemDto;
import com.example.momentous.momentous_finalproject.entity.Item;

import java.sql.SQLException;
import java.util.ArrayList;

public class ItemBOImpl implements ItemBO {

    ItemDAOImpl itemDAO = (ItemDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.ITEM);
    public String getNextItemId() throws SQLException, ClassNotFoundException {
        return itemDAO.getNextId();
    }

    public ArrayList<ItemDto> getAllItems() throws SQLException, ClassNotFoundException {
        ArrayList<ItemDto> itemDtos = new ArrayList<>();
        ArrayList<Item> itemsList = itemDAO.getAll();
        for (Item item : itemsList) {
            itemDtos.add(
                    new ItemDto(
                            item.getItemId(),
                            item.getItemName(),
                            item.getItemDescription(),
                            item.getCost(),
                            item.getQuantity(),
                            item.getSupplierId()

                    ));
        }
        return itemDtos;
    }

    public ItemDto findById(String selectedItemId) throws SQLException, ClassNotFoundException {
        Item item = itemDAO.findById(selectedItemId);
        return new ItemDto(
                item.getItemId(),
                item.getItemName(),
                item.getItemDescription(),
                item.getCost(),
                item.getQuantity(),
                item.getSupplierId()

        );
    }

    public ArrayList<String> getAllItemIds(String supplierId) throws SQLException, ClassNotFoundException {
        return itemDAO.getAllItemIds(supplierId);
    }

    public boolean deleteItem(String itemId) throws SQLException, ClassNotFoundException {
        return itemDAO.delete(itemId);
    }

    public boolean saveItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.save(
                new Item(
                        itemDto.getItemId(),
                        itemDto.getItemName(),
                        itemDto.getItemDescription(),
                        itemDto.getCost(),
                        itemDto.getQuantity(),
                        itemDto.getSupplierId()
                ));
    }

    public boolean updateItem(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return itemDAO.update(
                new Item(
                        itemDto.getItemId(),
                        itemDto.getItemName(),
                        itemDto.getItemDescription(),
                        itemDto.getCost(),
                        itemDto.getQuantity(),
                        itemDto.getSupplierId()
                ));
    }
}
