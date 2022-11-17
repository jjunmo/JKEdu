package com.example.jkedudemo.module.common.util;

import com.example.jkedudemo.module.common.enums.member.Phoneauth;
import org.springframework.core.convert.converter.Converter;

public class PhoneauthConverter implements Converter<String, Phoneauth> {
    @Override
    public Phoneauth convert(String source) {
        return Phoneauth.valueOf(source.toUpperCase());
    }
}