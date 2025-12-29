package com.loose.coupling;

import com.tight.coupling.UserDatabase;

public class UserDatabaseProvider implements UserDataProvider {
    @Override
    public String getUserDetails(){
        // Directly access the database here
        return "User details from Database";
    }
}
