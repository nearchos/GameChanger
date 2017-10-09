package com.aspectsense.gamechanger.model;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class State implements Serializable {

    private final int index;
    private final int points;

    public State(final int index, final int points) {
        this.index = index;
        this.points = points;
    }

    public int getIndex() {
        return index;
    }

    public int getPoints() {
        return points;
    }

    public static final State EMPTY_STATE = new State(0, 0);

    public static final String EMPTY_STATE_AS_JSON = EMPTY_STATE.toJSON();

    public static State fromJSON(final String json) throws JSONException {
        final JSONObject jsonObject = new JSONObject(json);
        return new State(jsonObject.getInt("index"), jsonObject.getInt("points"));
    }

    public String toJSON() {
        return "{ \"index\": " + index + ", \"points\": " + points + "}";
    }

    @Override
    public String toString() {
        return "index: " + index+ ", points: " + points;
    }
}