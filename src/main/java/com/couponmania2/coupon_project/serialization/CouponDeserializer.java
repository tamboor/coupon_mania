package com.couponmania2.coupon_project.serialization;

import com.couponmania2.coupon_project.beans.Category;
import com.couponmania2.coupon_project.beans.Coupon;
import com.couponmania2.coupon_project.repositories.CompanyRepo;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

//@JsonComponent
public class CouponDeserializer extends JsonDeserializer<Coupon> {


    private static SimpleDateFormat formatter
            = new SimpleDateFormat("dd-MM-yyyy");


    @Override
    public Coupon deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JsonProcessingException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);

//        long id = (long) (node.get("id")).numberValue();
        String description = node.get("description").asText();
        String title = node.get("title").asText();
        Category category = Category.valueOf(node.get("category").asText());
        java.sql.Date startDate= null;
        Date endDate= null;
        try {
            //TODO: move all to dateutils
             java.util.Date javaStartDate = formatter.parse(node.get("startDate").asText());
             java.util.Date javaEndDate = formatter.parse(node.get("endDate").asText());

             startDate = new Date(javaStartDate.getTime());
             endDate = new Date(javaStartDate.getTime());
        } catch (ParseException e) {
            //TODO: throw custom exception
            e.printStackTrace();
        }


        int amount = (int) (node.get("amount")).numberValue();
        double price = (node.get("price")).numberValue().doubleValue();

        String image = node.get("image").asText();


        return new Coupon(category,title,description, startDate,endDate,amount, price, image);

    }
}
