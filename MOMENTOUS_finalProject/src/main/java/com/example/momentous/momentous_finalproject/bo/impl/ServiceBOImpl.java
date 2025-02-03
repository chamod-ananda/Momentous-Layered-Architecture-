package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.ServiceBO;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dao.custom.impl.ServiceDAOImpl;
import com.example.momentous.momentous_finalproject.dto.ServiceDto;
import com.example.momentous.momentous_finalproject.entity.Service;

import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceBOImpl implements ServiceBO {

    ServiceDAOImpl serviceDAO = (ServiceDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.SERVICE);
    public ArrayList<String> getAllServiceIds() throws SQLException, ClassNotFoundException {
        return serviceDAO.getAllIds();
    }

    public ServiceDto findById(String selectedServiceId) throws SQLException, ClassNotFoundException {
        Service service = serviceDAO.findById(selectedServiceId);
        return new ServiceDto(
                service.getServiceId(),
                service.getPrice(),
                service.getServiceType()
        );
    }

    public String getNextServiceId() throws SQLException, ClassNotFoundException {
        return serviceDAO.getNextId();
    }

    public ArrayList<ServiceDto> getAllServices() throws SQLException, ClassNotFoundException {
        ArrayList<ServiceDto> serviceDtos = new ArrayList<>();
        ArrayList<Service> services = serviceDAO.getAll();
        for (Service service : services) {
            serviceDtos.add(
                    new ServiceDto(
                            service.getServiceId(),
                            service.getPrice(),
                            service.getServiceType()
                    ));



        }
        return serviceDtos;
    }

    public boolean saveService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return serviceDAO.save(
                new Service(
                        serviceDto.getServiceId(),
                        serviceDto.getPrice(),
                        serviceDto.getServiceType()
                ));
    }

    public boolean updateService(ServiceDto serviceDto) throws SQLException, ClassNotFoundException {
        return serviceDAO.update(
                new Service(
                        serviceDto.getServiceId(),
                        serviceDto.getPrice(),
                        serviceDto.getServiceType()
                ));
    }

    public boolean deleteService(String serviceId) throws SQLException, ClassNotFoundException {
        return serviceDAO.delete(serviceId);
    }
}
