
package com.aspectsense.gamechanger.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Interaction implements Serializable {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("prompt")
    @Expose
    private String prompt;

    @SerializedName("audio-prompt")
    @Expose
    private String audioPrompt;

    @SerializedName("choices")
    @Expose
    private List<Choice> choices = new ArrayList<Choice>();

    /**
     * @return The id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return The prompt
     */
    public String getPrompt() {
        return prompt;
    }

    /**
     * @param prompt The prompt
     */
    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    /**
     * @return The audioPrompt
     */
    public String getAudioPrompt() {
        return audioPrompt;
    }

    /**
     * @param audioPrompt The audio-prompt
     */
    public void setAudioPrompt(String audioPrompt) {
        this.audioPrompt = audioPrompt;
    }

    /**
     * @return The choices
     */
    public List<Choice> getChoices() {
        return choices;
    }

    /**
     * @param choices The choices
     */
    public void setChoices(List<Choice> choices) {
        this.choices = choices;
    }

}
