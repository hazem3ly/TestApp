package com.example.android.testtwo.Fragments;


import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.testtwo.Events.MessageEvent;
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
    boolean countStarted = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_timer, container, false);

        txtProgress = (TextView) rootView.findViewById(R.id.counter_text);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        TextView textView = (TextView) rootView.findViewById(R.id.toolbar_title);
        toolbar.setBackgroundColor(ContextCompat.getColor(getContext(),R.color.TimerTitle));
        textView.setText("Drug Details");
        textView.setTextColor(ContextCompat.getColor(getContext(),R.color.TimerTitleText));


        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (!countStarted) {
            countStarted = true;
            starCount();
        }else {
            EventBus.getDefault().post(new MessageEvent("Timer Already Finished"));
            countStarted = false;
            progressBar.setProgress(0);
            txtProgress.setText(0 + " %");
        }
    }


    private void starCount() {

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
                EventBus.getDefault().post(new MessageEvent("Timer Finished"));
            }
        }).start();


    }
}