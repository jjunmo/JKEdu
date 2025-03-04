package com.example.jkedudemo.module.upload;

import org.springframework.util.StringUtils;

import java.util.UUID;

public final class MultipartUtil {
    private static final String BASE_DIR = "images";

    /**
     * 새로운 파일 고유 ID를 생성합니다.
     * @return 36자리의 UUID
     */
    public static String createFileId() {
        return UUID.randomUUID().toString();
    }

    /**
     * Multipart 의 ContentType 값에서 / 이후 확장자만 잘라냅니다.
     * @param contentType ex) image/png
     * @return ex) png
     */
    public static String getFormat(String contentType) {

        if (StringUtils.hasText(contentType)) return contentType.substring(contentType.lastIndexOf('/') + 1);

        return null;
    }

    /**
     * 파일의 전체 경로를 생성합니다.
     * @param fileId 생성된 파일 고유 ID
     * @param format 확장자
     */
    public static String createPath(String fileId, String format) {
        return String.format("%s/%s.%s", BASE_DIR, fileId, format);
    }


}