package com.example.jkedudemo.module.common.util;

import com.example.jkedudemo.module.common.enums.PhoneAuthType;
import org.springframework.core.convert.converter.Converter;

public class PhoneAuthTypeConverter implements Converter<String, PhoneAuthType> {
    @Override
    public PhoneAuthType convert(String source) {
        return PhoneAuthType.valueOf(source.toUpperCase());
    }
}