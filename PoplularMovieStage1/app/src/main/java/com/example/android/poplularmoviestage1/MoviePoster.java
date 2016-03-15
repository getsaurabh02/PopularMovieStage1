package com.example.android.poplularmoviestage1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * @brief For movie poster on display on main activity
 * Created by Saurabh on 3/14/2016.
 */
public class MoviePoster extends ArrayAdapter<String> {

    private LayoutInflater movieLayoutInflator;
    private Context context;
    private int layoutId;

    private int imageViewID;

    /**
     * @brief Constructor
     * @param mContext
     * @param mLayoutId
     * @param mImageViewID
     * @param mURLs
     */
    public MoviePoster(Context mContext, int mLayoutId, int mImageViewID, ArrayList<String> mURLs) {

        super(mContext, 0, mURLs);

        this.movieLayoutInflator = LayoutInflater.from(mContext);
        this.layoutId = mLayoutId;
        this.imageViewID = mImageViewID;
        this.context = mContext;
    }

    /**
     * @brief Get the View
     * @param mPosition
     * @param mConvertView
     * @param mParent
     * @return
     */
    @Override
    public View getView(int mPosition, View mConvertView, ViewGroup mParent) {

        String mURLs;
        View view = mConvertView;

        if (view == null) {

            view = movieLayoutInflator.inflate(layoutId, mParent, false);
        }

        ImageView mImageView = (ImageView) view.findViewById(imageViewID);

        mURLs = getItem(mPosition);

        //Load
        Picasso.with(context).load(mURLs).into(mImageView);

        return view;
    }
}
