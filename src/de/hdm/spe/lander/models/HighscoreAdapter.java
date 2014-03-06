
package de.hdm.spe.lander.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.hdm.spe.lander.R;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;


public class HighscoreAdapter extends ArrayAdapter<Highscore> {

    private final NumberFormat format;

    public HighscoreAdapter(Context context, int resource, List<Highscore> objects) {
        super(context, resource, objects);
        this.format = NumberFormat.getIntegerInstance(Locale.getDefault());
        this.format.setMaximumFractionDigits(0);
        this.format.setMinimumFractionDigits(0);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext()).inflate(R.layout.list_entry, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.score = (TextView) convertView.findViewById(R.id.score);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }

        Highscore score = this.getItem(position);

        holder.name.setText(position + 1 + ". " + score.getName());
        holder.score.setText(this.format.format(score.getScore()));

        return convertView;

    }

    class ViewHolder {

        TextView name;
        TextView score;
    }

}
