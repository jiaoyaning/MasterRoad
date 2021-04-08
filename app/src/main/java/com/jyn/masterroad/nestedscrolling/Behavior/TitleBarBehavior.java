package com.jyn.masterroad.nestedscrolling.Behavior;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.jyn.masterroad.R;

public class TitleBarBehavior extends CoordinatorLayout.Behavior {
    private String TAG = "TitleBarBehavior";
    private float topBarHeight;     //topBar内容高度
    private float contentMaxTransY; //滑动内容最大滑动位置

    public TitleBarBehavior() {
    }

    public TitleBarBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
        contentMaxTransY = context.getResources().getDimension(R.dimen.nested_scrolling_content_translation_y);
        topBarHeight = context.getResources().getDimension(R.dimen.top_bar_height);
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        return dependency.getId() == R.id.recycler_view_content;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull View child, @NonNull View dependency) {
        float translationY = dependency.getTranslationY();
        child.setTranslationY(translationY - topBarHeight);
        return true;
    }
}
