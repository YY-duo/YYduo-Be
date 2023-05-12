package com.YYduo.KkuldongVarietyStore.global.S3Storage;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
@Service
@RequiredArgsConstructor
public class S3StorageService {
    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final AmazonS3 amazonS3;

    public List<String> uploadImage(List<MultipartFile> multipartFiles) {
        List<String> imageUrls = new ArrayList<>();

        multipartFiles.forEach(multipartFile -> {
            String fileName = createFileName(multipartFile.getOriginalFilename());
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                amazonS3.putObject(new PutObjectRequest(bucket, fileName, inputStream, objectMetadata));
                String imageUrl = amazonS3.getUrl(bucket, fileName).toString();
                imageUrls.add(imageUrl);
            } catch (IOException e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 업로드에 실패했습니다.");
            }
        });

        return imageUrls;
    }

    public void deleteImages(List<String> imageUrls) {
        for (String imageUrl : imageUrls) {
            String fileName = extractFileNameFromUrl(imageUrl);
            amazonS3.deleteObject(new DeleteObjectRequest(bucket, fileName));
        }
    }

    private String createFileName(String fileName) {
        return UUID.randomUUID().toString().concat(extractFileNameFromUrl(fileName));
    }

    /*파일 resize나 확장자 validation 추가 안했음 추후 논의*/

    private String extractFileNameFromUrl(String imageUrl) {
        int lastSlashIndex = imageUrl.lastIndexOf("/");
        if (lastSlashIndex != -1) {
            return imageUrl.substring(lastSlashIndex + 1);
        }
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 형식의 이미지 URL 입니다.");
    }
}

