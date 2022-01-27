# Project2
## Project Description
This is a deployable full stack, social media application. Users are allowed to signup, post their interests along with pictures and view all
other uses media. Users can also update their information and change their password. This repository consists of the backend of the 
project, which will persist data to the database. The repositories for the frontend and e2e testing can be found, respectivly, at:

https://github.com/garcia-alfredm/p2-FrontEndFinal

https://github.com/garcia-alfredm/p2-selenium-tests


## Technologies Used
* HTML/CSS with Typscript
* Angular 
* Java with Spring Data
* AWS EC2, S3, and RDS
* GitHub
* Postgres SQL

## Features
* Users can log in to their account, uses session checks to prevent subdomain navigation
* Users can create text and image posts that persist regardless of session
* Users can view theirs and others posts

To-do List;
* Users will be able to like other user's posts
* Users will be able to comment on other user's posts
* Users will be able to update username, and personal information

## Getting Started
To download backend repository:

```git clone https://github.com/garcia-alfredm/backend.git```

To download frontend repository:
```git clone https://github.com/garcia-alfredm/p2-FrontEndFinal.git```

To download selenium e2e testing repository:
```git clone https://github.com/garcia-alfredm/p2-selenium-tests.git```

To set up enviornment variables:
* update .bashrc file to include aws credentials, 
```
export AWS_RDS_ENDPOINT='[location of aws ec2 domain]'
export RDS_USERNAME='[ec2 username]'
export RDS_PASSWORD='[ec2 password]'

export P2_MAIL_PORT='[email port number]'
export P2_MAIL_USERNAME='[email username]'
export P2_MAIL_PASSWORD='[email password]'

export P2_S3_ID='[s3 bucket id]'
export P2_S3_Key='[s3 bucker key]'
export P2_S3_REGION='[s3 region]'
export P2_S3_BUCKETNAME='[s3 bucket name]'
export P2_S3_ENDPOINT='[s3 bucket enpoint]'
```


* Download frontend and backend to EC2 server
* User maven to build backend as a package
```mvn package```
* From deploy backend application as a background process
```nohup java -jar [name of build with dependencies].jar & ```
* use web browser to navigate to client side

## Usage
Users will be able to register and login to their social media account

Once logged in, a user will be able to set their own profile picture, create a text post 
including pictures

Users will be able to see other users posts in a feed


## Contributors
Alfred Garcia
Angel DePeña
Jakarai Forsythe
Michael Mikhael

## License
This project uses the license: 
