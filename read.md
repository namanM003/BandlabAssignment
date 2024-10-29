# Bandlab Assignment

## How to run this project
1. This project is setup using a personal AWS account, if we need to run locally please let me know will share temp credential to run
2. Build the project using ```./gradlew build``` command
3. Run the project using ```./gradlew bootrun``` command. This will bring the server up on port 8080.

### Decision taken
1. For sake of time the project is using H2 database instead of mysql or any other hosted solution.
2. No users API are defined but 2 default users are added in DB.
3. Ideally poller will be running on different host then the rest application but currently is part of one.

### Productionizing the application
1. Poller should be taken out from rest api and moved to its own service/application.
2. S3 code should be modularized and added as dependency to poller and rest apis.
3. A hosted DB should be added.
4. Poller is running on single thread which will lead resource wastefulness.
5. Adding integration testing.
6. Logging setup to help debugging.
7. Metrics needs to be added and configured for application monitoring.
8. Retry mechanism is not configured which needs to be configured.

### Assumptions taken
1. When ever feed is requested return from all not we are not checking if the user are friends/followers.
2. User post can be returned even if uploaded image is not available for sometime.
3. Validation of uploaded images is currently not written. Neither a thought has been given on notifying user about invalid format upload.
4. Image size check of 100 mb is not written, neither a way to notify user of exceeding limit has been written.
5. Link generated for user comes with 30 min validity, which means a slow internet with 512kbps should be able to complete 100mb upload. This limit can be changes as per the requirement.


### User stories and comments
● As a user, I should be able to create posts with images (1 post - 1 image)
> 1. This is done, the design is to get users sends a request to post
> 2. The post details are saved in DB while generating a presigned url for user to upload.
> 3. User uploads

● As a user, I should be able to set a text caption when I create a post
> User is able to set ```text``` caption when creating a post,
> the length of caption is limited to the limits supported by underlying db

● As a user, I should be able to comment on a post
> User is able to comment on post. Currently comment type is limited to text only. 
> As per the choice of DB there can be underlying limits on lengths.

● As a user, I should be able to delete a comment (created by me) from a post

> Comment writer can delete comment, currently I havent given post owner ability to delete comment, 
> but this is not hard to add, just need to add validation whether the delete request is coming from post owner or comment owner and then we permit to delete.

● As a user, I should be able to get the list of all posts along with the last 2 comments
on each post
> 1. This is implemented, but for now the API returns all the comments on post in descending order of creation time.
> 2. Pending to implement lazy loading.


### Functional Requirement

- [X] RESTful Web API (JSON) - 
- [] Maximum image size - 100MB -> This is currently not checked will need to think more on what might be the best way to do so. There are multiple ways to do with tradeoffs on each.
- [] Allowed image formats: .png, .jpg, .bmp.
- [X] Save uploaded images in the original format
- [X] Convert uploaded images to .jpg format and resize to 600x600
- [X] Serve images only in .jpg format
- [X] Posts should be sorted by the number of comments (desc)
- [X] Retrieve posts via a cursor-based pagination


### Additional Notes
1. Currently application is not developed using serverless model or k8s or docker can be be easily dockerized and launched on hosted solutions like AWS EC2.
2. The choice of language choosen is Java.
3. Storage system choice is S3.
4. Event model choice is SQS.
5. A few tests were written based on time constraints but an prod ready app should have unit and integration test suite.