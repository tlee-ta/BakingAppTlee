package com.example.android.bakingapptlee.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapptlee.R;
import com.example.android.bakingapptlee.databinding.FragmentRecipeStepDetailBinding;
import com.example.android.bakingapptlee.models.Step;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.util.Util;

public class RecipeStepDetailFragment extends Fragment implements Player.EventListener {

    FragmentRecipeStepDetailBinding recipeStepDetailBinding;

    private SimpleExoPlayer exoPlayer;
    private boolean playWhenReady = true;
    private int currentWindow = 0;
    private long playbackPosition = 0;

    private Step stepData;

    public RecipeStepDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            stepData = savedInstanceState.getParcelable(getString(R.string.SELECTED_STEP_KEY));
            playWhenReady = savedInstanceState.getBoolean(getString(R.string.PLAY_WHEN_READY_KEY));
            currentWindow = savedInstanceState.getInt(getString(R.string.CURRENT_WINDOW_KEY));
            playbackPosition = savedInstanceState.getLong(getString(R.string.PLAYBACK_POSITION_KEY));
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        recipeStepDetailBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_step_detail, container, false);

        if (stepData != null) {
            initializePlayer();

            recipeStepDetailBinding.tvRecipeStepDescription.setText(stepData.getStepDescription());
        }

        return recipeStepDetailBinding.getRoot();
    }

    private void initializePlayer() {
        if(stepData != null && exoPlayer == null) {
            if(stepData.getStepVideoURL().isEmpty() && stepData.getStepThumbnailURL().isEmpty()) {
                recipeStepDetailBinding.pvContainer.setVisibility(View.GONE);
                return;
            }
            recipeStepDetailBinding.pvContainer.setVisibility(View.VISIBLE);

            exoPlayer = new SimpleExoPlayer.Builder(getActivity()).build();
            recipeStepDetailBinding.pvRecipeStepVideo.setPlayer(exoPlayer);

            String stepUrl;
            if (!stepData.getStepVideoURL().isEmpty()) {
                stepUrl = stepData.getStepVideoURL();
            }
            else {
                stepUrl = stepData.getStepThumbnailURL();
            }
            MediaItem mediaItem = MediaItem.fromUri(stepUrl);
            exoPlayer.setMediaItem(mediaItem);

            exoPlayer.setPlayWhenReady(playWhenReady);
            exoPlayer.seekTo(currentWindow, playbackPosition);
            exoPlayer.prepare();
        }
    }

    private void releasePlayer() {
        if (exoPlayer != null) {
            playWhenReady = exoPlayer.getPlayWhenReady();
            playbackPosition = exoPlayer.getCurrentPosition();
            currentWindow = exoPlayer.getCurrentWindowIndex();
            exoPlayer.stop();
            exoPlayer.release();
            exoPlayer = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if(Util.SDK_INT >= 24) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if((Util.SDK_INT < 24 || exoPlayer == null)) {
            initializePlayer();
            if(playbackPosition != 0){
                exoPlayer.seekTo(playbackPosition);
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(Util.SDK_INT < 24) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if(Util.SDK_INT >= 24) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putParcelable(getString(R.string.SELECTED_STEP_KEY), stepData);
        outState.putBoolean(getString(R.string.PLAY_WHEN_READY_KEY), playWhenReady);
        outState.putInt(getString(R.string.CURRENT_WINDOW_KEY), currentWindow);
        outState.putLong(getString(R.string.PLAYBACK_POSITION_KEY), playbackPosition);
    }

    public void setStepData(Step step) {
        stepData = step;
    }
}