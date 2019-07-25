package com.example.supernote.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Behavior_scroll extends CoordinatorLayout.Behavior<BottomNavigationView> {
    public Behavior_scroll() {
        super();
    }

    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull BottomNavigationView child, @NonNull View dependency) {
        Boolean dependOn = dependency instanceof FrameLayout;
        return dependOn;
    }
    @Override
    public void onNestedPreScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View target, int dx, int dy, @NonNull int[] consumed, int type) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type);
        if(dy<0){
            show_bottom(child);
        }else if(dy>0){
            hide_bottom(child);
        }
    }

    private void hide_bottom(BottomNavigationView child) {
        child.animate().translationY(child.getHeight());
    }

    private void show_bottom(BottomNavigationView child) {
        child.animate().translationY(0);
    }

    @Override
    public boolean onStartNestedScroll(@NonNull CoordinatorLayout coordinatorLayout, @NonNull BottomNavigationView child, @NonNull View directTargetChild, @NonNull View target, int axes, int type) {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL;
    }
}
