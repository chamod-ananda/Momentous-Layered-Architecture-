package com.example.momentous.momentous_finalproject.bo.impl;

import com.example.momentous.momentous_finalproject.bo.PaymentBO;
import com.example.momentous.momentous_finalproject.dao.DAOFactory;
import com.example.momentous.momentous_finalproject.dao.custom.impl.PaymentDAOImpl;
import com.example.momentous.momentous_finalproject.dto.PaymentDto;
import com.example.momentous.momentous_finalproject.entity.Payment;

import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentBOImpl implements PaymentBO {
    PaymentDAOImpl paymentDAO = (PaymentDAOImpl) DAOFactory.getInstance().getDao(DAOFactory.DAOType.PAYMENT);

    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        return paymentDAO.getNextId();
    }

    public ArrayList<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException {
        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();
        ArrayList<Payment> paymentsList = paymentDAO.getAll();
        for (Payment payment : paymentsList) {
            paymentDtos.add(
                    new PaymentDto(
                            payment.getPaymentId(),
                            payment.getPaymentDate(),
                            payment.getPaymentAmount(),
                            payment.getBookingId()
                    ));
        }
        return paymentDtos;

    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(
                new Payment(
                        paymentDto.getPaymentId(),
                        paymentDto.getPaymentDate(),
                        paymentDto.getPaymentAmount(),
                        paymentDto.getBookingId()
                ));
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return paymentDAO.save(
                new Payment(
                        paymentDto.getPaymentId(),
                        paymentDto.getPaymentDate(),
                        paymentDto.getPaymentAmount(),
                        paymentDto.getBookingId()
                ));
    }

    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return paymentDAO.delete(paymentId);
    }
}
