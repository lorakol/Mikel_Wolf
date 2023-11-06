package com.training.mdv348o_ce02;



import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.leanback.widget.ImageCardView;
import androidx.leanback.widget.OnItemViewClickedListener;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.Row;
import androidx.leanback.widget.RowPresenter;

import com.bumptech.glide.Glide;

public class CardPresenter extends Presenter {

    private static final int CARD_WIDTH = 300;
    private static final int CARD_HEIGHT = 200;
    private int mSelectedBackgroundColor = -1;
    private int mDefaultBackgroundColor = -1;
    private Context mContext;


//    public CardPresenter(Context context) {
//        mContext = context;
//    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        //View view = LayoutInflater.from(mContext).inflate(R.layout.movie_card, null);
        View view = LayoutInflater.from(mContext).inflate(R.layout.movie_card, null);


        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        Movie movie = (Movie) item;
        //ImageCardView cardView = (ImageCardView) viewHolder.view;
        ImageView bgview = viewHolder.view.findViewById(R.id.bg_img);
        ImageView cardview = viewHolder.view.findViewById(R.id.card_img);
        TextView txtTitle = viewHolder.view.findViewById(R.id.title_txt);
        TextView txtDesc = viewHolder.view.findViewById(R.id.description_txt);

        txtTitle.setText(movie.getTitle());
        txtDesc.setText(movie.getDescription());
        bgview.setImageResource(movie.getBackgroundImageResourceId());
        cardview.setImageResource(movie.getCardImageResourceId());

//        cardView.setTitleText(movie.getTitle());
//        cardView.setContentText(movie.getDescription());
//
//        cardView.setIma
//        cardView.setMainImageDimensions(CARD_WIDTH, CARD_HEIGHT);
//        Glide.with(viewHolder.view.getContext())
//                .load(movie.getCardImageResourceId())
//                .centerCrop()
//                .into(cardView.getMainImageView());
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {
        // Clean up resources here if needed
        ImageCardView cardView = (ImageCardView) viewHolder.view;
        cardView.setBadgeImage(null);
        cardView.setMainImage(null);
    }


}
