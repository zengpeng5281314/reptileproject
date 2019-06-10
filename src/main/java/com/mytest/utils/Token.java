package com.mytest.utils;

import com.google.common.base.Objects;


/**
 *
 */
public class Token {
    /**
     * 版本号。代表身份认证机制的版本
     */
    private final String version;

    /**
     * 时间戳。用户登录时的时间戳。
     */
    private final long timeStamp;

    /**
     * 干扰码。是用户登录时产生的一个随机数。
     */
    private final int randomNumber;

    /**
     * 用户的ID。
     */
    private final long userId;

    /**
     * 修改密码的时间
     */
    private final long passwordUpdateTime;
    
    private String username;
    
    private String password;
    
    public Token(String version, long timeStamp, int randomNumber, long userId, long passwordUpdateTime) {
        this.version = version;
        this.timeStamp = timeStamp;
        this.randomNumber = randomNumber;
        this.userId = userId;
        this.passwordUpdateTime = passwordUpdateTime;
    }


    public Token(String version, long timeStamp, int randomNumber, long userId, long passwordUpdateTime,String username,String password) {
        this.version = version;
        this.timeStamp = timeStamp;
        this.randomNumber = randomNumber;
        this.userId = userId;
        this.passwordUpdateTime = passwordUpdateTime;
        this.username = username;
        this.password = password;
    }

    public String getVersion() {
        return version;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public int getRandomNumber() {
        return randomNumber;
    }

    public long getUserId() {
        return userId;
    }

    public long getPasswordUpdateTime() {
        return passwordUpdateTime;
    }
    
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("version'", version)
                .add("timeStamp", timeStamp)
                .add("randomNumber", randomNumber)
                .add("userId", userId)
                .add("passwordUpdateTime", passwordUpdateTime)
                .toString();
    }
}
