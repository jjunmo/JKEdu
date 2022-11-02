package com.example.jkedudemo.module.common.util;

import java.util.Random;

public class Cer {

    // A~Z 임의의 문자 1개 생성
    public static StringBuilder getCerStr(){
        StringBuilder cerStr = new StringBuilder();
        char ch = (char) ((Math.random() * 26) + 65);
        cerStr.append(ch);
        return cerStr;
    }

    /**
     *
     * @param phoneNumber 수신자 번호
     * @return 인증번호 생성
     */
    public static StringBuilder getCerNum(String phoneNumber) {
        Random random  = new Random();
        StringBuilder cerNum = new StringBuilder();
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(random.nextInt(10));
            cerNum.append(ran);
        }

        return cerNum;
    }

    public static String getCerStrNum(String phoneNumber){
       return String.valueOf(Cer.getCerStr().append(Cer.getCerNum(phoneNumber)));
    }


}
