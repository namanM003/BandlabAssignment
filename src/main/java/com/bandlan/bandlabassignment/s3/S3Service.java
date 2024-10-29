package com.bandlan.bandlabassignment.s3;

import com.bandlan.bandlabassignment.sqs.SqsPoller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseBytes;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.GetObjectRequest;
import software.amazon.awssdk.services.s3.model.GetObjectResponse;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.PresignedPutObjectRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Map;

@Service
public class S3Service {

    @Autowired
    S3Client s3Client;

    @Autowired
    S3Presigner s3Presigner;

    @Value("${aws.s3.bucketName}")
    private String s3UserImageBucket;

    @Value("${aws.s3.processImagesBucketName}")
    private String processedImageBucket;

//    private static final Logger logger = LoggerFactory.getLogger(S3Service.class);

    public String generatePresignedUploadUrl(Map<String, String> metadata, String imageFileName) {
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(s3UserImageBucket)
                .key(imageFileName)
                .metadata(metadata)
                .build();

        //Assuming 30 min should be enough to upload 100MB of data in poor internet conditions.
        PutObjectPresignRequest putObjectPresignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .putObjectRequest(putObjectRequest)
                .build();

        PresignedPutObjectRequest presignedPutObjectRequest = s3Presigner.presignPutObject(putObjectPresignRequest);

        return presignedPutObjectRequest.url().toExternalForm();
    }

    public BufferedImage readImageFromS3(final String imageFileName) throws IOException {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                                                            .bucket(s3UserImageBucket)
                                                            .key(imageFileName)
                                                            .build();

            ResponseBytes<GetObjectResponse> objectBytes = s3Client.getObjectAsBytes(getObjectRequest);

            //TODO Add checks for checking image file type and accordingly handle if not a supported image type.
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(objectBytes.asByteArray());
//            logger.info("Read " + imageFileName);
            return ImageIO.read(byteArrayInputStream);
    }

    public void putProcessedImage(ByteArrayInputStream image, String imageFileName) {
//        logger.info("Putting image " + imageFileName + " in bucket " + processedImageBucket);
        PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                .bucket(processedImageBucket)
                .key(imageFileName)
                .build();

        s3Client.putObject(putObjectRequest, RequestBody.fromBytes(image.readAllBytes()));
    }
}
