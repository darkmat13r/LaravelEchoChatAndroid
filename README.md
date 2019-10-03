## Android Echo Chat Sample
Demo api URL :  https://tourn.darkmatter.in/

## Api Setting Android
Edit  `ApiConstant.kt` accordingly
 

## To generate token for android from demo server

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



