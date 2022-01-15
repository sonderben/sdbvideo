package com.sonderben.sdbvideo.data.model;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class ErrorMessage {
    //ZonedDateTime timestamp;
    int status;
    private String error;
    private String path;

    public ErrorMessage(String error,String path,int status) {

        this.error=error;
        this.path=path;
        this.status=status;
    }



    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getPath() {
        return path;
    }
}
