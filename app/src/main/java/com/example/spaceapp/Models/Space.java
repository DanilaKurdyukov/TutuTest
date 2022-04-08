package com.example.spaceapp.Models;

import org.json.JSONObject;

import java.io.Serializable;

public class Space implements Serializable {
    private String message;
    private JSONObject iss_positions;
    private String timestamp;

    public Space(String message, JSONObject iss_positions, String timestamp) {
        this.message = message;
        this.iss_positions = iss_positions;
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getIss_positions() {
        return iss_positions;
    }

    public void setIss_positions(JSONObject iss_positions) {
        this.iss_positions = iss_positions;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }
}
