package com.example.jkeduhomepage.module.common.utility;

import com.example.jkeduhomepage.module.article.service.AwsS3Service;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Scheduler {
    private final AwsS3Service awsS3Service;
//
//    @Scheduled(cron = "2 * * * * *")
//    public void deleteFile() {
//        awsS3Service
//    }
}
