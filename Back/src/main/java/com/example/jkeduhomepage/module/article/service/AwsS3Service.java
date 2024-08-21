package com.example.jkeduhomepage.module.article.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.jkeduhomepage.module.article.entity.UploadFile;
import com.example.jkeduhomepage.module.article.repository.UploadFileRepository;
import com.example.jkeduhomepage.module.common.enums.Category;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import marvin.image.MarvinImage;
import org.marvinproject.image.transform.scale.Scale;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AwsS3Service {

    private static final String FILE_EXTENSION_SEPARATOR = ".";
    private static final String CATEGORY_PREFIX = "/";
    private static final String TIME_SEPARATOR = "_";

    private static final int THUMBNAIL_WIDTH=235;

    private static final int THUMBNAIL_HEIGHT=300;

    private final AmazonS3Client amazonS3Client;

    private final UploadFileRepository uploadFileRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    public List<UploadFile> uploadFile(String filePath,List<MultipartFile> multipartFile) {
        List<UploadFile> uploadFileList = new ArrayList<>();
        // forEach 구문을 통해 multipartFile로 넘어온 파일들 하나씩 fileNameList에 추가
        multipartFile.forEach(file -> {
            String fileName = file.getOriginalFilename();
            String customFileName = buildFileName(filePath, Objects.requireNonNull(fileName));

            UploadFile uploadFile =new UploadFile();
            uploadFile.setFileName(fileName);
            uploadFile.setCustomFileName(customFileName);
            uploadFile.setUrl(amazonS3Client.getUrl(bucketName,customFileName).toString());

                    ObjectMetadata objectMetadata = new ObjectMetadata();
                    objectMetadata.setContentLength(file.getSize());
                    objectMetadata.setContentType(file.getContentType());

                    try (InputStream inputStream = file.getInputStream()) {
                        amazonS3Client.putObject(new PutObjectRequest(bucketName, customFileName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));
                        if(Objects.requireNonNull(file.getContentType()).contains("image")) {
                            String thumbnailFileName = buildFileName("thumbnail",Objects.requireNonNull(fileName));
                            MultipartFile resizedFile = thumbnailImage(fileName, thumbnailFileName, file);

                            uploadFile.setThumbnailImageUrl(amazonS3Client.getUrl(bucketName,thumbnailFileName).toString());

                            ObjectMetadata objectMetadata2 = new ObjectMetadata();
                            objectMetadata2.setContentLength(resizedFile.getSize());
                            objectMetadata2.setContentType(file.getContentType());

                            InputStream inputStream2=resizedFile.getInputStream();

                            amazonS3Client.putObject(new PutObjectRequest(bucketName, thumbnailFileName, inputStream2, objectMetadata2)
                                    .withCannedAcl(CannedAccessControlList.PublicRead));
                        }
                    } catch (IOException e) {
                        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 업로드에 실패했습니다.");
                    }
                    uploadFileList.add(uploadFile);
                }
        );
        return uploadFileList;
    }

    public String buildFileName(String category, String originalFileName) {
        int fileExtensionIndex = originalFileName.lastIndexOf(FILE_EXTENSION_SEPARATOR);
        String fileExtension = originalFileName.substring(fileExtensionIndex);
        String fileName = createFileName(originalFileName);
        String now = String.valueOf(System.currentTimeMillis());

        return category + CATEGORY_PREFIX + fileName + TIME_SEPARATOR + now + fileExtension;
    }

    private String createFileName(String fileName) { // 먼저 파일 업로드 시, 파일명을 난수화하기 위해 random으로 돌립니다.
        return UUID.randomUUID().toString().concat(getFileExtension(fileName));
    }

    private String getFileExtension(String fileName) { // file 형식이 잘못된 경우를 확인하기 위해 만들어진 로직이며, 파일 타입과 상관없이 업로드할 수 있게 하기 위해 .의 존재 유무만 판단하였습니다.
        try {
            return fileName.substring(fileName.lastIndexOf("."));
        } catch (StringIndexOutOfBoundsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 파일(" + fileName + ") 입니다.");
        }
    }

    MultipartFile thumbnailImage(String fileName, String fileFormatName, MultipartFile originalImage) {
        try {
            // MultipartFile -> BufferedImage Convert
            BufferedImage image = ImageIO.read(originalImage.getInputStream());
            // newWidth : newHeight = originWidth : originHeight
            int originWidth = image.getWidth();
            int originHeight = image.getHeight();

//            // origin 이미지가 resizing될 사이즈보다 작을 경우 resizing 작업 안 함
//            if(originWidth < THUMBNAIL_WIDTH)
//                return originalImage;


            MarvinImage imageMarvin = new MarvinImage(image);

            Scale scale = new Scale();
            scale.load();
            scale.setAttribute("newWidth", THUMBNAIL_WIDTH);
            scale.setAttribute("newHeight", THUMBNAIL_HEIGHT);
            scale.process(imageMarvin.clone(), imageMarvin, null, null, false);

            String fileExtension = fileFormatName.substring(fileFormatName.lastIndexOf(FILE_EXTENSION_SEPARATOR));

            BufferedImage imageNoAlpha = imageMarvin.getBufferedImageNoAlpha();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(imageNoAlpha, "png", baos);
            baos.flush();

            return new MockMultipartFile(fileName, baos.toByteArray());

        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "파일 리사이즈에 실패했습니다.");
        }
    }
//
//    public void deleteNoFile(Category category, String fileName) {
//        List<UploadFile> uploadFileList=uploadFileRepository.findByArticle_IdNull();
//
//        String deleteFileName = category + CATEGORY_PREFIX + fileName;
//        amazonS3Client.deleteObject(new DeleteObjectRequest(bucketName, deleteFileName));
//    }

    }