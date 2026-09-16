/*
 * Copyright (C) 2016 objectlife
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 */
package com.objectlife.statelayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewParent;
import android.widget.FrameLayout;

import androidx.annotation.IdRes;
import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.IdentityHashMap;

/**
 * A {@link FrameLayout} that displays one of four mutually exclusive child views: content,
 * empty, error, or loading.
 */
public class StateLayout extends FrameLayout {

    @IntDef({VIEW_CONTENT, VIEW_EMPTY, VIEW_ERROR, VIEW_LOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {}

    public static final int VIEW_CONTENT = 0;
    public static final int VIEW_EMPTY = 1;
    public static final int VIEW_ERROR = 2;
    public static final int VIEW_LOADING = 3;

    private final IdentityHashMap<View, Boolean> programmaticallyAddedViews =
            new IdentityHashMap<>();

    private View contentView;
    private View emptyView;
    private View errorView;
    private View loadingView;

    @IdRes private int contentViewId = View.NO_ID;
    @IdRes private int emptyViewId = View.NO_ID;
    @IdRes private int errorViewId = View.NO_ID;
    @IdRes private int loadingViewId = View.NO_ID;

    @ViewState private int viewState = VIEW_LOADING;
    private boolean hasXmlConfiguration;

    public StateLayout(@NonNull Context context) {
        this(context, null);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttributes(context, attrs, defStyleAttr);
    }

    private void readAttributes(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        if (attrs == null) {
            return;
        }
        TypedArray values = context.obtainStyledAttributes(
                attrs, R.styleable.StateLayout, defStyleAttr, 0);
        try {
            contentViewId = values.getResourceId(
                    R.styleable.StateLayout_sl_contentView, View.NO_ID);
            emptyViewId = values.getResourceId(
                    R.styleable.StateLayout_sl_emptyView, View.NO_ID);
            errorViewId = values.getResourceId(
                    R.styleable.StateLayout_sl_errorView, View.NO_ID);
            loadingViewId = values.getResourceId(
                    R.styleable.StateLayout_sl_loadingView, View.NO_ID);
            viewState = values.getInt(
                    R.styleable.StateLayout_sl_initialState, VIEW_LOADING);
            validateState(viewState);
            hasXmlConfiguration = contentViewId != View.NO_ID
                    || emptyViewId != View.NO_ID
                    || errorViewId != View.NO_ID
                    || loadingViewId != View.NO_ID
                    || values.hasValue(R.styleable.StateLayout_sl_initialState);
        } finally {
            values.recycle();
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (!hasXmlConfiguration) {
            return;
        }
        contentView = findConfiguredView(contentViewId, "sl_contentView");
        emptyView = findConfiguredView(emptyViewId, "sl_emptyView");
        errorView = findConfiguredView(errorViewId, "sl_errorView");
        loadingView = findConfiguredView(loadingViewId, "sl_loadingView");
        showState(viewState);
    }

    @Nullable
    private View findConfiguredView(@IdRes int id, String attributeName) {
        if (id == View.NO_ID) {
            return null;
        }
        View view = findViewById(id);
        if (view == null) {
            throw new IllegalStateException(attributeName + " must reference a child of StateLayout");
        }
        return view;
    }

    /** Sets the content view, adding it as a child if it has no parent. */
    @NonNull
    public StateLayout setContentView(@Nullable View view) {
        contentView = replaceManagedView(contentView, view);
        return this;
    }

    /** Finds and sets the content view using a descendant ID. */
    @NonNull
    public StateLayout setContentViewResId(@IdRes int viewResId) {
        contentView = findRequiredView(viewResId);
        return this;
    }

    /** Sets the empty view, adding it as a child if it has no parent. */
    @NonNull
    public StateLayout setEmptyView(@Nullable View view) {
        emptyView = replaceManagedView(emptyView, view);
        return this;
    }

    /** Finds and sets the empty view using a descendant ID. */
    @NonNull
    public StateLayout setEmptyViewResId(@IdRes int viewResId) {
        emptyView = findRequiredView(viewResId);
        return this;
    }

    /** Sets the error view, adding it as a child if it has no parent. */
    @NonNull
    public StateLayout setErrorView(@Nullable View view) {
        errorView = replaceManagedView(errorView, view);
        return this;
    }

    /** Finds and sets the error view using a descendant ID. */
    @NonNull
    public StateLayout setErrorViewResId(@IdRes int viewResId) {
        errorView = findRequiredView(viewResId);
        return this;
    }

    /** Sets the loading view, adding it as a child if it has no parent. */
    @NonNull
    public StateLayout setLoadingView(@Nullable View view) {
        loadingView = replaceManagedView(loadingView, view);
        return this;
    }

    /** Finds and sets the loading view using a descendant ID. */
    @NonNull
    public StateLayout setLoadingViewResId(@IdRes int viewResId) {
        loadingView = findRequiredView(viewResId);
        return this;
    }

    @Nullable
    private View replaceManagedView(@Nullable View previous, @Nullable View replacement) {
        if (previous == replacement) {
            return replacement;
        }
        ViewParent replacementParent = replacement == null ? null : replacement.getParent();
        if (replacementParent != null && replacementParent != this) {
            throw new IllegalArgumentException("State view already belongs to another parent");
        }
        if (previous != null && programmaticallyAddedViews.remove(previous)) {
            removeView(previous);
        }
        if (replacement == null) {
            return null;
        }
        if (replacementParent == null) {
            addView(replacement);
            programmaticallyAddedViews.put(replacement, Boolean.TRUE);
        }
        return replacement;
    }

    @NonNull
    private View findRequiredView(@IdRes int id) {
        View view = findViewById(id);
        if (view == null) {
            throw new IllegalArgumentException("No descendant found for view ID " + id);
        }
        return view;
    }

    /** Initializes the layout and displays {@code state}. */
    public void initWithState(@ViewState int state) {
        setState(state);
    }

    /** Displays exactly the view assigned to {@code state}; missing views are ignored. */
    public void setState(@ViewState int state) {
        validateState(state);
        viewState = state;
        showState(state);
    }

    /** Returns the current state. */
    @ViewState
    public int getState() {
        return viewState;
    }

    private static void validateState(int state) {
        if (state < VIEW_CONTENT || state > VIEW_LOADING) {
            throw new IllegalArgumentException("Unknown view state: " + state);
        }
    }

    private void showState(@ViewState int state) {
        setViewVisible(contentView, state == VIEW_CONTENT);
        setViewVisible(emptyView, state == VIEW_EMPTY);
        setViewVisible(errorView, state == VIEW_ERROR);
        setViewVisible(loadingView, state == VIEW_LOADING);
    }

    private static void setViewVisible(@Nullable View view, boolean visible) {
        if (view != null) {
            view.setVisibility(visible ? VISIBLE : GONE);
        }
    }

    @Nullable
    @Override
    protected Parcelable onSaveInstanceState() {
        Parcelable superState = super.onSaveInstanceState();
        SavedState savedState = new SavedState(superState);
        savedState.viewState = viewState;
        return savedState;
    }

    @Override
    protected void onRestoreInstanceState(Parcelable state) {
        if (!(state instanceof SavedState)) {
            super.onRestoreInstanceState(state);
            return;
        }
        SavedState savedState = (SavedState) state;
        super.onRestoreInstanceState(savedState.getSuperState());
        setState(savedState.viewState);
    }

    static class SavedState extends BaseSavedState {
        int viewState;

        SavedState(Parcelable superState) {
            super(superState);
        }

        private SavedState(Parcel source) {
            super(source);
            viewState = source.readInt();
        }

        @Override
        public void writeToParcel(@NonNull Parcel destination, int flags) {
            super.writeToParcel(destination, flags);
            destination.writeInt(viewState);
        }

        public static final Parcelable.Creator<SavedState> CREATOR =
                new Parcelable.Creator<SavedState>() {
                    @Override
                    public SavedState createFromParcel(Parcel source) {
                        return new SavedState(source);
                    }

                    @Override
                    public SavedState[] newArray(int size) {
                        return new SavedState[size];
                    }
                };
    }
}
