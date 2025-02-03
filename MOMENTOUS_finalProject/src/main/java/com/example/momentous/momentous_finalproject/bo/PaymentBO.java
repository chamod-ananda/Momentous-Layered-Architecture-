package com.example.momentous.momentous_finalproject.bo;

import com.example.momentous.momentous_finalproject.dto.PaymentDto;

import java.sql.SQLException;
import java.util.ArrayList;

public interface PaymentBO extends SuperBO{
    String getNextPaymentId() throws SQLException, ClassNotFoundException ;
    ArrayList<PaymentDto> getAllPayments() throws SQLException, ClassNotFoundException ;
    boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException ;
    boolean updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException ;
    boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException ;
}
