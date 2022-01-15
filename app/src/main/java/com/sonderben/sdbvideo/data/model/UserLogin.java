package com.sonderben.sdbvideo.data.model;

/**
 * Data class that captures user information for logged in users retrieved from UserRepository
 */
public class UserLogin {

    private String email;
    private String password;
    private String device;
    private String location;


    /*
     "email":"q@gmail.com",
    "password":"1234",
    "device":"windows 18",
    "location":"san marco"
     */

    public UserLogin(){}
    public UserLogin(String email, String password, String device, String location) {
        this.email = email;
        this.password = password;
        this.device = device;
        this.location = location;
    }
    public UserLogin(String email, String password) {
        this.email = email;
        this.password = password;
    }
    public class Response{
        String token;

        @Override
        public String toString() {
            return "Response{" +
                    "token='" + token + '\'' +
                    '}';
        }

        public String getToken() {
            return token;
        }

        public Response(String token) {
            this.token = token;
        }
    }
}