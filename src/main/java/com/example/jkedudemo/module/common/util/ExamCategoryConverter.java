package com.example.jkedudemo.module.common.util;

import com.example.jkedudemo.module.common.enums.exam.Exam;
import org.springframework.core.convert.converter.Converter;

public class ExamCategoryConverter implements Converter<String, Exam> {
    @Override
    public Exam convert(String source) {
        return Exam.valueOf(source.toUpperCase());
    }
}