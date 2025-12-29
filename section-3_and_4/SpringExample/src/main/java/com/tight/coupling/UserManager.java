package com.tight.coupling;

public class UserManager {
    private final UserDatabase UserDB = new UserDatabase();

    public String getUserInfo(){
        return UserDB.getUserDetails();
    }
}
