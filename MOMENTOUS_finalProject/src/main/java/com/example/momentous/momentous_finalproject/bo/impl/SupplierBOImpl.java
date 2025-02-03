package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.SupplierBO;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dao.custom.impl.SupplierDAOImpl;
import com.example.momentous.momentous_finalproject.dto.SupplierDto;
import com.example.momentous.momentous_finalproject.entity.Supplier;

import java.sql.SQLException;
import java.util.ArrayList;

public class SupplierBOImpl implements SupplierBO {

    SupplierDAOImpl supplierDAO = (SupplierDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.SUPPLIER);
    public String getNextSupplierId() throws SQLException, ClassNotFoundException {
        return supplierDAO.getNextId();
    }

    public ArrayList<SupplierDto> getAllSuppliers() throws SQLException, ClassNotFoundException {
        ArrayList<SupplierDto> supplierDtos = new ArrayList<>();
        ArrayList<Supplier> suppliersList = supplierDAO.getAll();
        for (Supplier supplier : suppliersList) {
            supplierDtos.add(
                    new SupplierDto(supplier.getSupplierId(),supplier.getSupplierName(),supplier.getEmail()));

        }
        return supplierDtos;
    }

    public ArrayList<String> getAllSupplierIds() throws SQLException, ClassNotFoundException {
        return supplierDAO.getAllIds();
    }

    public SupplierDto findById(String selectedSupplierId) throws SQLException, ClassNotFoundException {
        Supplier supplier = supplierDAO.findById(selectedSupplierId);
        return new SupplierDto
                (supplier.getSupplierId(),supplier.getSupplierName(),supplier.getEmail()
                );

    }

    public boolean saveSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.save(
                new Supplier
                        (supplierDto.getSupplierId(),supplierDto.getSupplierName(),supplierDto.getEmail()
                        ));
    }

    public boolean updateSupplier(SupplierDto supplierDto) throws SQLException, ClassNotFoundException {
        return supplierDAO.update(
                new Supplier
                        (supplierDto.getSupplierId(),supplierDto.getSupplierName(),supplierDto.getEmail()
                        ));
    }

    public boolean deleteSupplier(String supplierId) throws SQLException, ClassNotFoundException {
        return supplierDAO.delete(supplierId);
    }
}
