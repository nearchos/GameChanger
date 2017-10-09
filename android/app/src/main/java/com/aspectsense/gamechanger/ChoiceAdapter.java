package com.aspectsense.gamechanger;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.aspectsense.gamechanger.model.Choice;

import java.util.List;

/**
 * @author Nearchos
 *         Created: 21-Nov-16
 */

public class ChoiceAdapter extends ArrayAdapter<Choice> {

    private final LayoutInflater layoutInflater;

    ChoiceAdapter(final Context context) {
        super(context, R.layout.choice_list_item);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    ChoiceAdapter(final Context context, final List<Choice> choices) {
        super(context, R.layout.choice_list_item, choices);

        this.layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        if(convertView == null) {
            convertView = layoutInflater.inflate(R.layout.choice_list_item, parent, false);
        }

        final Choice selectedChoice = getItem(position);
        assert selectedChoice != null;

        final ImageButton audioImageButton = (ImageButton) convertView.findViewById(R.id.choice_list_item_audio);
        if(selectedChoice.getPromptSound() == null) {
            audioImageButton.setEnabled(false);
            audioImageButton.setVisibility(View.INVISIBLE);
        } else {
            audioImageButton.setEnabled(true);
            audioImageButton.setVisibility(View.INVISIBLE);
            audioImageButton.setVisibility(View.VISIBLE);
            audioImageButton.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View view) {
                    // todo
                    Toast.makeText(getContext(), "TODO -> " + selectedChoice.getPrompt(), Toast.LENGTH_SHORT).show();
                }
            });
            audioImageButton.setFocusable(false);
        }

        final TextView optionMessage = (TextView) convertView.findViewById(R.id.choice_list_item_option_message);
        optionMessage.setText(selectedChoice.getPrompt());

        return convertView;
    }
}
