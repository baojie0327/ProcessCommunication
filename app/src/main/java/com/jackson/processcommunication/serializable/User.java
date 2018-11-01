package com.jackson.processcommunication.serializable;

import java.io.Serializable;
import java.net.PortUnreachableException;

/*
 * User  2018-10-17
 * Copyright (c) 2018 HYB Co.Ltd. All right reserved.
 *
 */
/*
 * class description here
 * @author Jackson
 * @version 1.0.0
 * since 2018 10 17
 */
public class User implements Serializable {

    public static final long serialVersionUID = 519067123721295773L;

    private int userId;
    private String userName;
    private boolean isMale;

    public User(int userId, String userName, boolean isMale){
        this.userId = userId;
        this.userName = userName;
        this.isMale = isMale;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isMale() {
        return isMale;
    }

    public void setMale(boolean male) {
        isMale = male;
    }
}