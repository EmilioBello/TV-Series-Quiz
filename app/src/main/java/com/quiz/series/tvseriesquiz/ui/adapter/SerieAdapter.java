package com.quiz.series.tvseriesquiz.ui.adapter;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.quiz.series.tvseriesquiz.R;
import com.quiz.series.tvseriesquiz.catalog.LanguageCatalog;
import com.quiz.series.tvseriesquiz.ui.viewmodel.SeriesViewModel;
import com.squareup.picasso.Picasso;

import java.util.Collection;
import java.util.List;

/**
 * Created by Emilio on 10/10/2016.
 */

public class SerieAdapter extends RecyclerView.Adapter<SerieAdapter.ViewHolder>{

    private List<SeriesViewModel> items;
    private Context context;
    private ClickListener clickListener;

    public SerieAdapter(List<SeriesViewModel> items, Context context, ClickListener clickListener) {
        this.items = items;
        this.clickListener = clickListener;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = null;
        int layout = 0;

        layout = R.layout.row_serie;
        View v = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        holder = new ViewHolder(v, clickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        SeriesViewModel item = items.get(position);
        initViewHolder(viewHolder, item);
        viewHolder.itemView.setTag(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    private void initViewHolder(final ViewHolder viewHolder, SeriesViewModel item) {

        TextView tvName = viewHolder.tvName;
        tvName.setText(LanguageCatalog.getValue(item.getName()));

        ImageView ivImageBackground = viewHolder.ivImageBackground;
        Picasso.with(context).load(item.getUrlImageBackground()).into(ivImageBackground);

        ImageView ivAvatar = viewHolder.ivAvatar;
        Picasso.with(context).load(item.getUrlAvatar()).into(ivAvatar);


    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ClickListener listener;

        public TextView tvName;
        public ImageView ivImageBackground, ivAvatar;
        public CardView cvSerie;

        public ViewHolder(View view, ClickListener listener) {
            super(view);

            this.listener = listener;

            tvName = (TextView) itemView.findViewById(R.id.tvName);
            ivAvatar = (ImageView) itemView.findViewById(R.id.ivAvatar);
            ivImageBackground = (ImageView) itemView.findViewById(R.id.ivImageBackground);
            cvSerie = (CardView) itemView.findViewById(R.id.cvSerie);

            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (listener != null) {
                SeriesViewModel item = (SeriesViewModel) view.getTag();
                listener.onItemClicked(item);
            }
        }
    }


    public SeriesViewModel getItem(int index) {
        return items.get(index);
    }


    public void add(SeriesViewModel item) {
        items.add(item);
    }


    public void remove(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }


    public void addAll(Collection<SeriesViewModel> elements) {
        this.items.addAll(elements);
    }

    public void removeAll(Collection<SeriesViewModel> elements) {
        this.items.removeAll(elements);
    }

    public void clear() {
        items.clear();
    }



    public interface ClickListener {
        void onItemClicked(SeriesViewModel item);
    }
}
