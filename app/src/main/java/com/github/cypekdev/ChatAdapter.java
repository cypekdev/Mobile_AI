package com.github.cypekdev;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import java.util.List;

public class ChatAdapter extends ArrayAdapter<ConversationCard> {
    public ChatAdapter(Context context, List<ConversationCard> conversation) {
        super(context, 0, conversation);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        ConversationCard item = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.conversation_card, parent, false);
        }

        TextView userPromptTV = convertView.findViewById(R.id.userPrompt);
        TextView assistantAnswerTV = convertView.findViewById(R.id.assistantAnswer);

        assert item != null;
        userPromptTV.setText(item.getUserPrompt());
        assistantAnswerTV.setText(item.getAssistantAnswer().getValue());

        return convertView;
    }
}
