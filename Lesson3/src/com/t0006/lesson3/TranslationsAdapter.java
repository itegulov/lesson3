package com.t0006.lesson3;

import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by dimatomp on 26.09.14.
 */
public class TranslationsAdapter extends StdListAdapter<WordTranslation> {

    public TranslationsAdapter(AsyncTaskFragment fragment) {
        super(fragment);
    }

    @Override
    protected View createLastItem(ViewGroup parent) {
        return getLayoutInflater().inflate(R.layout.ind_progress_bar, parent, false);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        WordTranslation translation = (WordTranslation) getItem(position);
        if (convertView == null) {
            if (translation == null)
                return createLastItem(parent);
            convertView = getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false);
        }
        if (translation != null) {
            TextView title = (TextView) convertView.findViewById(android.R.id.text1);
            title.setText(translation.translation);
            title = (TextView) convertView.findViewById(android.R.id.text2);
            title.setText(translation.lang);
        }
        return convertView;
    }
}
