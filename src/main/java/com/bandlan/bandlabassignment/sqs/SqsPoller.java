package com.bandlan.bandlabassignment.sqs;

import com.bandlan.bandlabassignment.s3.S3Service;
import com.bandlan.bandlabassignment.utility.ImageTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import software.amazon.awssdk.core.exception.RetryableException;
import software.amazon.awssdk.eventnotifications.s3.model.S3EventNotification;
import software.amazon.awssdk.eventnotifications.s3.model.S3EventNotificationRecord;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.DeleteMessageRequest;
import software.amazon.awssdk.services.sqs.model.Message;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageRequest;
import software.amazon.awssdk.services.sqs.model.ReceiveMessageResponse;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class SqsPoller {

    SqsClient sqsClient;

    private String queueUrl;

    private S3Service s3Service;

    private static final Logger logger = LoggerFactory.getLogger(SqsPoller.class);

    @Autowired
    public SqsPoller(SqsClient sqsClient, S3Service s3Service, @Value("${aws.sqs.queueUrl}")String queueUrl) {
        this.sqsClient = sqsClient;
        this.queueUrl = queueUrl;
        this.s3Service = s3Service;
        fetchMessages();
    }

    ExecutorService executors = Executors.newSingleThreadExecutor();

    public void fetchMessages() {
        executors.submit(() -> {
            while (true) {
                try {
                    ReceiveMessageRequest receive_request = ReceiveMessageRequest.builder()
                            .maxNumberOfMessages(5)
                            .queueUrl(queueUrl)
                            .waitTimeSeconds(20)
                            .build();
                    ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(receive_request);

                    List<Message> messageList = receiveMessageResponse.messages();
                    if (CollectionUtils.isEmpty(messageList)) {
                        continue;
                    }
                    for (Message message : messageList) {
                        S3EventNotification notification = S3EventNotification.fromJson(message.body());

                        for(S3EventNotificationRecord record : notification.getRecords()) {
                            String fileName = record.getS3().getObject().getKey();
                            logger.info(String.format("Fetching %s image from s3", fileName));
                            BufferedImage imageInputStream = s3Service.readImageFromS3(fileName);
                            ByteArrayInputStream convertedInputStream = ImageTransformer.convertToJPG(imageInputStream);
                            s3Service.putProcessedImage(convertedInputStream, fileName);

                        }
                        DeleteMessageRequest deleteMessageRequest = DeleteMessageRequest.builder()
                                .queueUrl(queueUrl)
                                .receiptHandle(message.receiptHandle())
                                .build();
                        sqsClient.deleteMessage(deleteMessageRequest);
                    }
                } catch (IOException ioException) {
                    logger.error("Something went wrong while reading, writing a file, should be retried");
                } catch (RetryableException e) {
                    logger.warn("Retryable exception occured while reading message", e);
                    //TODO configure retry mechanism
                }
            }
        });
    }
}
