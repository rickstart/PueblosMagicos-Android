package com.mobintum.pueblosmagicos.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.mobintum.pueblosmagicos.R;
import com.mobintum.pueblosmagicos.adapters.NewsAdapter;
import com.mobintum.pueblosmagicos.models.News;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListNewsFragment extends Fragment {

    private ListView listNews;
    private NewsAdapter adapter;


    public ListNewsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_news, container, false);
        listNews = (ListView) view.findViewById(R.id.listNews);
        adapter = new NewsAdapter(getContext(),R.layout.item_news, News.getData());
        listNews.setAdapter(adapter);

        return view;
    }

}
