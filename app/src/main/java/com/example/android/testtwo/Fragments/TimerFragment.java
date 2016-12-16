package com.example.android.testtwo.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.testtwo.MessageEvent;
import com.example.android.testtwo.R;

import org.greenrobot.eventbus.EventBus;

/**
 * A simple {@link Fragment} subclass.
 */
public class TimerFragment extends Fragment {



    public TimerFragment() {
        // Required empty public constructor
    }

    private TextView txtProgress;
    private ProgressBar progressBar;
    private int pStatus = 0;
    private Handler handler = new Handler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View roorView = inflater.inflate(R.layout.fragment_timer, container, false);

        txtProgress = (TextView) roorView.findViewById(R.id.counter_text);
        progressBar = (ProgressBar) roorView.findViewById(R.id.progressBar);
        Toolbar toolbar = (Toolbar) roorView.findViewById(R.id.toolbar_top);
        TextView textView = (TextView) roorView.findViewById(R.id.toolbar_title);
        ImageView imageView = (ImageView) roorView.findViewById(R.id.bg);
        imageView.setImageDrawable(ResourcesCompat.getDrawable(getResources(),R.drawable.header,null));
        textView.setText("Drug Details");

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (pStatus <= 100) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            progressBar.setProgress(pStatus);
                            txtProgress.setText(pStatus + " %");
                        }
                    });
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    pStatus++;
                }
            }
        }).start();

            EventBus.getDefault().post(new MessageEvent("Timer Finished"));


        return roorView;

    }


}