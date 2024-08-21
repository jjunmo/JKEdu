package com.example.jkeduhomepage.module.common.utility;

import java.util.Random;

public class Cer {

    public static StringBuilder getCerNum(String phone) {
        Random random  = new Random();
        StringBuilder cerNum = new StringBuilder();
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(random.nextInt(10));
            cerNum.append(ran);
        }

        return cerNum;
    }
}
