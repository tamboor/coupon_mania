package com.couponmania2.coupon_project.serialization;

import com.couponmania2.coupon_project.beans.Category;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class CategoryConverter implements AttributeConverter<Category, String> {
    /**
     * converts the category attribute to varchar in the database.
     *
     * @param category the category of the bean.
     * @return the string to be put in the database.
     */
    @Override
    public String convertToDatabaseColumn(Category category) {
        if (category == null) {
            return null;
        }
        return category.getName();
    }

    /**
     * Allows to take category from the database (represented in varchar) and converts to the enum value.
     *
     * @param name the varchar taken from the database.
     * @return the category matching the vategory value.
     */
    @Override
    public Category convertToEntityAttribute(String name) {
        if (name == null) {
            return null;
        }
        return Stream.of(Category.values())
                .filter(category -> category.getName().equals(name))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }
}
