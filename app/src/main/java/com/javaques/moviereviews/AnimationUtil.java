package com.javaques.moviereviews;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;

/**
 * Created by Anil on 03-Sep-15.
 */
public class AnimationUtil {

    private static final long TIME_IN_MILI_SECONDS = 500 ;

    public static void animate(RecyclerView.ViewHolder holder, boolean  goesDown){
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator animateTranslateY = ObjectAnimator.ofFloat(holder.itemView, "translationY", goesDown == true ? 200 : -200, 0);
        animateTranslateY.setDuration(TIME_IN_MILI_SECONDS);
        animatorSet.playTogether(animateTranslateY);
        animatorSet.start();
    }
}
