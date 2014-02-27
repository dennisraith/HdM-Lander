
package de.hdm.spe.lander.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import de.hdm.spe.lander.R;

import java.util.List;


public class HighscoreAdapter extends ArrayAdapter<Highscore> {

    public HighscoreAdapter(Context context, int resource, List<Highscore> objects) {
        super(context, resource, objects);
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

        holder.name.setText(score.getName());
        holder.score.setText("" + score.getScore());

        return convertView;

    }

    class ViewHolder {

        TextView name;
        TextView score;
    }

}
