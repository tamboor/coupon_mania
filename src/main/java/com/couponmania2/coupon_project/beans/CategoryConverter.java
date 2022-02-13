package com.couponmania2.coupon_project.beans;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category,String> {
    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category==null){
            return null;
        }
       return category.getName();
    }
//TODO: change to custom exeption
    @Override
    public Category convertToEntityAttribute(String name) {
        if (name==null){
            return null;
        }
        return Stream.of(Category.values())
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
