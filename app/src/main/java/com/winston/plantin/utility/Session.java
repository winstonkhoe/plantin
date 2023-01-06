package com.winston.plantin.utility;

import com.winston.plantin.model.User;

public class Session {
    private User user;
    private static Session instance;

    public static Session getInstance(){
        if(instance == null){
            instance = new Session();
        }
        return instance;
    }

    public void setUser(User user){
        this.user = user;
    }

    public User getUser(){
        return this.user;
    }

    public void logout(){
        user = null;
    }

}
