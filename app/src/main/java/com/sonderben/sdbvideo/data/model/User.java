package com.sonderben.sdbvideo.data.model;

/**
 * Data class that captures user information for logged in users retrieved from UserRepository
 */
public class User {

    private String email;
    private String password;
    private String device;
    private String location;

    public User(){}
    public User(String email, String password, String device, String location) {
        this.email = email;
        this.password = password;
        this.device = device;
        this.location = location;
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public class Response{
        String token;

        public String getToken() {
            return token;
        }

        public Response(String token) {
            this.token = token;
        }
    }
}