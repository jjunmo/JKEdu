package com.example.jkeduhomepage.module.common.utility;

import com.example.jkeduhomepage.module.common.enums.Category;
import org.springframework.core.convert.converter.Converter;

import java.util.Locale;

public class CategoryConverter implements Converter<String, Category> {
    @Override
    public Category convert(String path) {
        try {
            return Category.valueOf(path.toUpperCase(Locale.ROOT));   // 소문자로 들어온 source를 대문자로
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
