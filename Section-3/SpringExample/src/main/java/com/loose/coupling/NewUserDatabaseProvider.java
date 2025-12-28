package com.loose.coupling;

public class NewUserDatabaseProvider implements UserDataProvider{
    @Override
    public String getUserDetails() {
        return "User details from new Database";
    }
}
