package com.liversedge.workoutselector.frontend.videos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;
import com.liversedge.workoutselector.R;

public class YoutubeVideoFragment extends Fragment {
    private static final String YOUTUBE_API_KEY = "AIzaSyCs5Y7_308oXj2lAbRUqUvW_IIRULMpJ90";

    // The fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String VIDEO_ID = "VIDEO_ID";

    // TODO: Rename and change types of parameters
    private String videoID;
    private Integer parentID;

    public YoutubeVideoFragment() {
        // Required empty public constructor
    }

    public static YoutubeVideoFragment newInstance(String videoId) {
        YoutubeVideoFragment fragment = new YoutubeVideoFragment();
        Bundle args = new Bundle();
        args.putString(VIDEO_ID, videoId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            videoID = getArguments().getString(VIDEO_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_youtube, container, false);

        YouTubePlayerSupportFragment youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.flYoutubePlayer, youTubePlayerFragment).commit();

        youTubePlayerFragment.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {

            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider arg0, YouTubePlayer youTubePlayer, boolean b) {
                if (!b) {
                    //youTubePlayer.setFullscreen(true);
                    youTubePlayer.loadVideo(videoID);
                    //yoTubePlayer.play();
                }
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider arg0, YouTubeInitializationResult arg1) {
                // TODO Auto-generated method stub

            }
        });

        return rootView;
    }

}
