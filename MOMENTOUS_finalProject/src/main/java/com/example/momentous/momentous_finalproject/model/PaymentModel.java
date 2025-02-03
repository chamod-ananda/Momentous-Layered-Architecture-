package com.example.momentous.momentous_finalproject.model;

import com.example.momentous.momentous_finalproject.dto.PaymentDto;
import com.example.momentous.momentous_finalproject.util.CrudUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class PaymentModel {
    public String getNextPaymentId() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select payment_id from payment order by payment_id desc limit 1");

        if (rst.next()) {
            String lastId = rst.getString(1);
            String substring = lastId.substring(1);
            int i = Integer.parseInt(substring);
            int newIdIndex = i + 1;
            return String.format("P%03d", newIdIndex);
        }
        return "P001";
    }

    public ArrayList<PaymentDto> getAllItems() throws SQLException, ClassNotFoundException {
        ResultSet rst = CrudUtil.execute("select * from payment");

        ArrayList<PaymentDto> paymentDtos = new ArrayList<>();

        while (rst.next()) {
            PaymentDto paymentDto = new PaymentDto(
                    rst.getString(1),
                    rst.getDate(2),
                    rst.getDouble(3),
                    rst.getString(4)

            );
            paymentDtos.add(paymentDto);
        }
        return paymentDtos;
    }

    public boolean savePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("insert into payment values(?,?,?,?)",
                paymentDto.getPaymentId(),
                paymentDto.getPaymentDate(),
                paymentDto.getPaymentAmount(),
                paymentDto.getBookingId()

        );
    }

    public boolean updatePayment(PaymentDto paymentDto) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute(
                "update payment set  date = ?, amount = ?, booking_id = ? where payment_id = ?",
                paymentDto.getPaymentDate(),
                paymentDto.getPaymentAmount(),
                paymentDto.getBookingId(),
                paymentDto.getPaymentId()
        );
    }

    public boolean deletePayment(String paymentId) throws SQLException, ClassNotFoundException {
        return CrudUtil.execute("delete from payment where payment_id=?", paymentId);
    }
}
