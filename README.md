# myTwitter
## 1. list all the users
curl -X GET \
  http://localhost:8088/api/v0/Users \
  -H 'Authorization: Basic dXNlcjo=' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' 
  
## 2. create new user
curl -X POST \
  http://localhost:8088/api/v0/Users \
  -H 'Authorization: Basic dXNlcjo=' \
  -H 'Content-Type: application/json' \
  -d '{
    "id": 10003,
    "firstName": "feng3",
    "lastName": "wang3",
    "email": "feng.wang3@minitwitter.com",
    "password": "password3",
    "roles": [
        {
            "id": 10001,
            "name": "USER"
        }
    ]
	
}'

## 3. follow a user
curl -X POST \
  http://localhost:8088/api/v0/following \
  -H 'Authorization: Basic dXNlcjo=' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 755d3e73-72ca-b72b-91cf-36d53f6ac633' \
  -d '{
    "user_id": 10001,
    "follower_id": 10002
}'

## 4. unfollow a user
curl -X DELETE \
  http://localhost:8088/api/v0/following \
  -H 'Authorization: Basic dXNlcjo=' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 57a350f4-9755-172b-dec5-dae203b540a3' \
  -d '{
    "user_id": 10002,
    "follower_id": 10001
}'

## 5. get the list of people the user is following as well as the followers of the user.
curl -X GET \
  http://localhost:8088/api/v0/followings/10001 \
  -H 'Authorization: Basic Og==' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 68440d73-b39d-9096-650a-2ed8bfa110bd' 

## 6. create a new message
curl -X POST \
  http://localhost:8088/api/v0/messages \
  -H 'Authorization: Basic Og==' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: f10cc237-0dbf-2ab1-32d1-705487412ecc' \
  -d '{
    "user_id": 10003,
    "message": "feng3 published the first message"
}'

## 7. messages that were sent by user and users he/she follow
curl -X GET \
  http://localhost:8088/api/v0/messages/10001 \
  -H 'Authorization: Basic Og==' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 70629f7b-fc94-9a35-dc9f-9d288f77cb57'
  
 ## 8. list of user and his/her most popular follower
 curl -X GET \
  http://localhost:8088/api/v0/users_popular_followers \
  -H 'Authorization: Basic Og==' \
  -H 'Cache-Control: no-cache' \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 3fd1ab4b-65fc-fd97-f764-414937332dcb'
 
