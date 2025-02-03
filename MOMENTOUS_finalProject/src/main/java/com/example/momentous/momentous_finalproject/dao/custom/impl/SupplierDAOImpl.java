package com.example.momentous.momentous_finalproject.dao.custom.impl;

import com.example.momentous.momentous_finalproject.dao.custom.SupplierDAO;
import com.example.momentous.momentous_finalproject.dto.SupplierDto;
import com.example.momentous.momentous_finalproject.entity.Supplier;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierDAOImpl implements SupplierDAO {
    public String getNextId() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier order by supplier_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("D%03d", newIdIndex);
        }
        return "D001";
    }

    public ArrayList<Supplier> getAll() throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier");

        ArrayList<Supplier> suppliers = new ArrayList<>();

        while (rst.next()) {
            Supplier entity = new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
            suppliers.add(entity);
        }
        return suppliers;
    }

    @Override
    public boolean save(Supplier entity) throws SQLException, ClassNotFoundException {
        return false;
    }

    public ArrayList<String> getAllIds() throws SQLException {
        ResultSet rst = CrudUtil.execute("select supplier_id from supplier");

        ArrayList<String> supplierIds = new ArrayList<>();

        while (rst.next()) {
            supplierIds.add(rst.getString(1));
        }

        return supplierIds;
    }

    public Supplier findById(String selectedSupplierId) throws SQLException {
        ResultSet rst = CrudUtil.execute("select * from supplier where supplier_id=?", selectedSupplierId);

        if (rst.next()) {
            return new Supplier(
                    rst.getString(1),
                    rst.getString(2),
                    rst.getString(3)
            );
        }
        return null;

    }

    public boolean save(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute("insert into supplier values(?,?,?)",
                supplierDto.getSupplierId(),
                supplierDto.getSupplierName(),
                supplierDto.getEmail()

        );
    }

    public boolean update(SupplierDto supplierDto) throws SQLException {
        return CrudUtil.execute(
                "update supplier set  supplier_name = ?, email = ? where supplier_id = ?",
                supplierDto.getSupplierName(),
                supplierDto.getEmail(),
                supplierDto.getSupplierId()
        );
    }

    public boolean delete(String supplierId) throws SQLException {
        return CrudUtil.execute("delete from supplier where supplier_id=?", supplierId);
    }

    @Override
    public boolean update(Supplier entity) throws SQLException, ClassNotFoundException {
        return false;
    }
}
