package com.example.jkedudemo.module.exam.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.jkedudemo.module.exam.entity.ExamCategory;
import com.example.jkedudemo.module.exam.entity.ExamQuest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;
    private final EntityManager em;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    /**
     * S3 bucket 파일 읽어 DB에 저장
     */
    @Transactional
    public void readObject(String storedFileName) throws IOException {
        S3Object o = amazonS3.getObject(new GetObjectRequest(bucket, storedFileName));
        S3ObjectInputStream ois = null;
        BufferedReader br = null;

        // Read the CSV one line at a time and process it.
        try {
            ois = o.getObjectContent();
            System.out.println ("ois = " + ois);
            br = new BufferedReader (new InputStreamReader(ois, StandardCharsets.UTF_8));

            String line;
            while ((line = br.readLine()) != null) {
                // Store 1 record in an array separated by commas
                String[] data = line.split(",", 0);

                ExamCategory examCategory = new ExamCategory(data[0], data[1]);
                ExamQuest examQuest=new ExamQuest(data[])
                em.persist(examCategory);
            }
        } finally {
            if(ois != null){
                ois.close();
            }
            if(br != null){
                br.close();
            }
        }
    }
}
