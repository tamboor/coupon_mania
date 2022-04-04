package com.couponmania2.coupon_project.serialization;

import com.couponmania2.coupon_project.beans.Customer;
import com.couponmania2.coupon_project.beans.Purchase;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.stream.Collectors;

public class CustomerSerializer extends JsonSerializer<Customer> {
    @Override
    public void serialize(Customer customer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id" , customer.getId());
        jsonGenerator.writeStringField("firstName" , customer.getFirstName());
        jsonGenerator.writeStringField("lastName" , customer.getLastName());
        jsonGenerator.writeStringField("email" , customer.getEmail());
        jsonGenerator.writeStringField("password" , customer.getPassword());

        jsonGenerator.writeStringField("coupons" ,
                customer.getPurchases().stream().map(Purchase::getCoupon).collect(Collectors.toSet()).toString()
                /*customer.getPurchases().stream().map(p -> p.getCoupon().getId()).collect(Collectors.toList())
                        .toString()*/);

        jsonGenerator.writeEndObject();
    }
}
