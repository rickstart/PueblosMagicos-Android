package com.mobintum.pueblosmagicos.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.models.News;
import com.mobintum.pueblosmagicos.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Rick on 23/02/16.
 */
public class NewsAdapter extends ArrayAdapter<News> {
    private List<News> mNews;
    private int resource;
    private LayoutInflater inflater;
    private Context context;

    public NewsAdapter(Context context, int resource, List<News> mNews) {
        super(context, resource, mNews);
        this.context = context;
        this.resource = resource;
        this.mNews = mNews;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        News news = mNews.get(position);
        if(convertView == null){
            convertView = inflater.inflate(resource,parent,false);
            holder = new ViewHolder();
            holder.txtDate = (TextView) convertView.findViewById(R.id.txtDate);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.txtTitle);
            holder.txtContent = (TextView) convertView.findViewById(R.id.txtContent);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        holder.txtDate.setText(DateUtil.getDateFormat(news.getDate()));
        holder.txtTitle.setText(news.getTitle());
        holder.txtContent.setText(news.getDescription());

        return convertView;
    }

    class ViewHolder{
        TextView txtDate;
        TextView txtTitle;
        TextView txtContent;
    }
}
