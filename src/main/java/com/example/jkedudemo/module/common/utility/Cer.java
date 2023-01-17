package com.example.jkedudemo.module.common.utility;

import java.util.Random;

public class Cer {

    // A~Z 임의의 문자 1개 생성
    public static StringBuilder getCerStrA(){
        StringBuilder cerStr = new StringBuilder();
        char ch = (char) ((Math.random() * 26) + 65);
        cerStr.append(ch);
        return cerStr;
    }

    // a~z 임의의 문자 1개 생성
    public static StringBuilder getCerStra(){
        StringBuilder cerStr = new StringBuilder();
        char ch = (char) ((Math.random() * 26) + 97);
        cerStr.append(ch);
        return cerStr;
    }

    /**
     *
     * @param phone 수신자 번호
     * @return 인증번호 생성
     */
    public static StringBuilder getCerNum(String phone) {
        Random random  = new Random();
        StringBuilder cerNum = new StringBuilder();
        for(int i=0; i<4; i++) {
            String ran = Integer.toString(random.nextInt(10));
            cerNum.append(ran);
        }

        return cerNum;
    }


    //AcademyId
    public static String getStrCerNum(String phone){
       return String.valueOf(Cer.getCerStrA().append(Cer.getCerNum(phone)));
    }

    //임시 비밀번호
    public static StringBuilder getStrStrCerNum(String phone){
        return new StringBuilder(Cer.getCerStra().append(Cer.getCerStra()).append(Cer.getCerNum(phone)));
    }


}
