package com.couponmania2.coupon_project.serialization;

import com.couponmania2.coupon_project.beans.Customer;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomerSerializer extends JsonSerializer<Customer> {
    @Override
    public void serialize(Customer customer, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
        jsonGenerator.writeStartObject();
        jsonGenerator.writeNumberField("id" , customer.getId());
        jsonGenerator.writeStringField("firstName" , customer.getFirstName());
        jsonGenerator.writeStringField("lastName" , customer.getLastName());
        jsonGenerator.writeStringField("email" , customer.getEmail());
        jsonGenerator.writeStringField("password" , customer.getPassword());
        jsonGenerator.writeEndObject();
    }
}
