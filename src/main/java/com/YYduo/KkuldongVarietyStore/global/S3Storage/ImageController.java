package com.YYduo.KkuldongVarietyStore.global.S3Storage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/images")
@RequiredArgsConstructor
public class ImageController {

    private final S3StorageService s3StorageService;

    @PostMapping
    public ResponseEntity<List<String>> uploadImages(@RequestParam("files") List<MultipartFile> files) {
        List<String> uploadedImageUrls = s3StorageService.uploadImage(files);
        return new ResponseEntity<>(uploadedImageUrls, HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteImages(@RequestBody List<String> imageUrls) {
        s3StorageService.deleteImages(imageUrls);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
