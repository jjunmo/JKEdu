package com.example.jkedudemo.module.exam.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.example.jkedudemo.module.exam.entity.ExamCategory;
import com.example.jkedudemo.module.exam.entity.ExamMultipleChoice;
import com.example.jkedudemo.module.exam.entity.ExamQuest;

import com.example.jkedudemo.module.exam.repository.ExamCategoryRepository;
import com.example.jkedudemo.module.exam.repository.ExamQuestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class S3Service {
    private final AmazonS3 amazonS3;
    private final EntityManager em;
    private final ExamCategoryRepository examCategoryRepository;
    private final ExamQuestRepository examQuestRepository;

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

        // Read the CSV
        try {
            ois = o.getObjectContent();
            System.out.println ("ois = " + ois);
            br = new BufferedReader (new InputStreamReader(ois, StandardCharsets.UTF_8));

            String line;

            while ((line = br.readLine()) != null) {
                
                String[] data = line.split(",", 0);

                if(storedFileName.contains("Category")) {
                    ExamCategory examCategory = new ExamCategory(data[0], data[1]);
                    em.persist(examCategory);
                }

                if(storedFileName.contains("Quest")) {
                    Optional<ExamCategory> examCategory = examCategoryRepository.findById(Long.parseLong(data[1]));
                    ExamCategory examCategory1 = null;

                    if (examCategory.isPresent()) examCategory1 = examCategory.get();

                    data[0]=data[0].replace("\uFEFF", "");

                    ExamQuest examQuest = new ExamQuest(data[0],
                            examCategory1, data[2],
                            data[3].replace("*",","),
                            data[4].replace("*",","),
                            data[5].replace("*",","),
                            data[6], data[7], data[8], data[9]);
                    em.persist(examQuest);
                }

                if(storedFileName.contains("Multiple")) {
                    Optional<ExamQuest> examQuest = examQuestRepository.findById(Long.parseLong(data[1]));
                    ExamQuest examQuest1 = null;

                    if (examQuest.isPresent()) examQuest1 = examQuest.get();

                    ExamMultipleChoice examMultipleChoice= new ExamMultipleChoice(data[0],examQuest1,data[2],data[3]);
                    em.persist(examMultipleChoice);
                }

            }
        } finally {

            if(ois != null) ois.close();

            if(br != null) br.close();

        }
    }
}