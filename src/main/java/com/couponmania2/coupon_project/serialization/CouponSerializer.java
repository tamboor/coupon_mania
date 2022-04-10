//package com.couponmania2.coupon_project.serialization;
//
//import com.couponmania2.coupon_project.beans.Coupon;
//import com.fasterxml.jackson.core.JsonGenerator;
//import com.fasterxml.jackson.databind.JsonSerializer;
//import com.fasterxml.jackson.databind.SerializerProvider;
//import com.fasterxml.jackson.databind.annotation.JsonSerialize;
//import com.fasterxml.jackson.databind.ser.std.StdSerializer;
//import org.springframework.boot.jackson.JsonComponent;
//
//import java.io.IOException;
//@JsonSerialize
//public class CouponSerializer extends JsonSerializer<Coupon> {
//
//    @Override
//    public void serialize(Coupon coupon, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
//
//        jsonGenerator.writeStartObject();
//        jsonGenerator.writeNumberField("id" , coupon.getId());
//        jsonGenerator.writeNumberField("companyID" , coupon.getCompany().getId());
//        jsonGenerator.writeStringField("category" , coupon.getCategory().getName());
//        jsonGenerator.writeStringField("title" , coupon.getTitle());
//        jsonGenerator.writeStringField("description" , coupon.getDescription());
//        jsonGenerator.writeStringField("startDate" , coupon.getStartDate().toString());
//        jsonGenerator.writeStringField("endDate" , coupon.getEndDate().toString());
//        jsonGenerator.writeNumberField("amount" , coupon.getAmount());
//        jsonGenerator.writeNumberField("price" , coupon.getPrice());
//        jsonGenerator.writeStringField("image" , coupon.getImage());
//        jsonGenerator.writeEndObject();
//    }
//
//}
