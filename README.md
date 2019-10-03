## Android Echo Chat Sample
Demo api URL :  https://tourn.darkmatter.in/

## Step 1
Create two users on the demo web application one to send message and another to receive messages.


## Step 2 : Api Setting Android
Edit  `ApiConstant.kt` accordingly
 

## Step 3 :  To generate token for android from demo server

**Use postman**
Login EndPoint : https://tourn.darkmatter.in/api/login
Register EndPoint  : https://tourn.dartkmatter.in/api/register

Update access token and user id  in `MyApplication.kt` code:

    private fun createDemoUser() {  
        val user = User()  
        user.id =  //YOUR_USER_ID  
	    user.accessToken = "Your access token"  
	    AccessTokenManager.save(user)  
    }

## Step 4

Run android

## Step 5
Open the web application https://tourn.darkmatter.in/ 
Select the user whose access token is used in android android
Then send the message.