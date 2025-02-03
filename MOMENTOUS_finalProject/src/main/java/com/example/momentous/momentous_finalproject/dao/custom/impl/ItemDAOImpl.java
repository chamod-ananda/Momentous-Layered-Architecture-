package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.ItemDAO;
import com.example.momentous.momentous_finalproject.dto.EventSupplierDto;
import com.example.momentous.momentous_finalproject.dto.ItemDto;
import com.example.momentous.momentous_finalproject.entity.Item;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ItemDAOImpl implements ItemDAO {
    public String getNextId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select item_id from item order by item_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("I%03d", newIdIndex);
        }
        return "I001";
    }

    public ArrayList<Item> getAll() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from item");

        ArrayList<Item> itemList = new ArrayList<>();

        while (rst.next()) {
            Item entity = new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6)

            );
            itemList.add(entity);
        }
        return itemList;
    }

    public Item findById(String selectedItemId) throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from item where item_id=?", selectedItemId);

        if (rst.next()) {
            return new Item(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3),
                    rst.getDouble(4),
                    rst.getInt(5),
                    rst.getString(6)
            );
        }
        return null;
    }

    public ArrayList<String> getAllIds() throws SQLException, ClassNotFoundException {
        return null;
    }

    public ArrayList<String> getAllItemIds(String supplierId) throws SQLException, ClassNotFoundException {
        String query = "SELECT item_id FROM item WHERE supplier_id = ?";

        ResultSet rst = CrudUtil.execute(query, supplierId);

        ArrayList<String> itemIds = new ArrayList<>();

        while (rst.next()) {
            itemIds.add(rst.getString(1));
        }
        return itemIds;
    }

    public boolean reduceQty(EventSupplierDto evenSupplierDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update item set quantity = quantity - ? where item_id = ?",
                evenSupplierDto.getItemQuantity(),
                evenSupplierDto.getItemId()
        );
    }

    public boolean delete(String selectedItemId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from item where item_id=?", selectedItemId);
    }

    public boolean save(ItemDto itemDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into item values(?,?,?,?,?,?)",
                itemDto.getItemId(),
                itemDto.getItemName(),
                itemDto.getItemDescription(),
                itemDto.getCost(),
                itemDto.getQuantity(),
                itemDto.getSupplierId()
        );
    }

    public boolean update(ItemDto itemDto) throws SQLException {
        return CrudUtil.execute("update item set item_name = ?, description = ?, cost = ?, quantity = ?, supplier_id = ? where item_id = ?",
                itemDto.getItemName(),
                itemDto.getItemDescription(),
                itemDto.getCost(),
                itemDto.getQuantity(),
                itemDto.getSupplierId(),
                itemDto.getItemId()
        );
    }
}
