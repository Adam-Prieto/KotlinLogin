package edu.msudenver.kotlinlogin

class UserModel(var email: String, var username: String, var password: String)
{
   
   override fun toString(): String
   {
      return "UserModel(email='$email', username='$username', password='$password')"
   }
}