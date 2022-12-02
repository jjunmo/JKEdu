package com.example.jkedudemo.module.jwt.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Map;
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshApiResponseMessage {
    Map<String,String> refreshResponseMap;

}
