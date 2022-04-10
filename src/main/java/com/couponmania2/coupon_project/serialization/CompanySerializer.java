//package com.couponmania2.coupon_project.serialization;
//
//import com.couponmania2.coupon_project.beans.Company;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//
//import java.io.IOException;
//public class CompanySerializer extends JsonSerializer<Company> {
//    @Override
//    public void serialize(Company company, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//        jsonGenerator.writeStartObject();
//        jsonGenerator.writeNumberField("id" , company.getId());
//        jsonGenerator.writeStringField("name" , company.getName());
//        jsonGenerator.writeStringField("email" , company.getEmail());
//        jsonGenerator.writeStringField("password" , company.getPassword());
//
//        jsonGenerator.writeStringField("coupons" ,
//                company.getCoupons().toString()
//                /*company.getCoupons().stream()
//                        .map(c -> c.getId()).collect(Collectors.toList()).toString()*/);
//
//        jsonGenerator.writeEndObject();
//    }
//}
