package com.aspectsense.gamechanger;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.aspectsense.gamechanger.model.State;
import org.json.JSONException;

/**
 * @author Nearchos
 *         Created: 23-Jan-17
 */

public class Preferences {

    public static final String TAG = "bullying.Preferences";

    private static final String GLOBAL_PREFERENCES = "global-preferences";

    static State getState(final Context context, final String storyId) {
        try {
            final SharedPreferences sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE);
            final String stateAsJSON = sharedPreferences.getString(storyId, State.EMPTY_STATE_AS_JSON);
            return State.fromJSON(stateAsJSON);
        } catch (JSONException jsone) {
            Log.e(TAG, "JSON error: " + jsone.getMessage());
            return State.EMPTY_STATE;
        }
    }

//    static State getState(final Context context, final String storyId, final String firstSceneId, final String firstStepId) {
//        final SharedPreferences sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE);
//        final String sceneId = sharedPreferences.getString(storyId + "-sceneId", firstSceneId);
//        final String stepId = sharedPreferences.getString(storyId + "-stepId", firstStepId);
//        final int points = sharedPreferences.getInt(storyId + "-points", 0);
//        return new State(sceneId, stepId, points);
//    }

    static void setState(final Context context, final String storyId, final State state) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(storyId, state.toJSON()).apply();
    }

//    static void setState(final Context context, final String storyId, final State state) {
//        setState(context, storyId, state.getSceneId(), state.getStepId(), state.getPoints());
//    }
//
//    static void setState(final Context context, final String storyId, final String sceneId, final String stepId, final int points) {
//        final SharedPreferences sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE);
//        sharedPreferences.edit().putString(storyId + "-sceneId", sceneId).putString(storyId + "-stepId", stepId).putInt(storyId + "points", points).apply();
//    }

    static void resetState(final Context context, final String storyId) {
        final SharedPreferences sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE);
        sharedPreferences.edit().remove(storyId).apply();
    }

//    static void resetState(final Context context, final String storyId) {
//        final SharedPreferences sharedPreferences = context.getSharedPreferences(GLOBAL_PREFERENCES, Context.MODE_PRIVATE);
//        sharedPreferences.edit().remove(storyId + "-sceneId").remove(storyId + "-stepId").remove(storyId + "-points").apply();
//    }
}