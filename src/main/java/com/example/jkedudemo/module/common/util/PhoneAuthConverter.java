package com.example.jkedudemo.module.common.util;

import com.example.jkedudemo.module.common.enums.member.PhoneAuth;
import org.springframework.core.convert.converter.Converter;

public class PhoneAuthConverter implements Converter<String, PhoneAuth> {
    @Override
    public PhoneAuth convert(String source) {
        return PhoneAuth.valueOf(source.toUpperCase());
    }
}