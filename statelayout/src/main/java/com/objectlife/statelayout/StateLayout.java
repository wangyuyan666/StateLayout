/*
 * Copyright (C) 2016 objectlife
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.objectlife.statelayout;

import android.content.Context;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 *
 * A subclass of FrameLayout that can display different state of view.like contentView, emptyView,
 * errorView and loadingView. you can set state view by {@link #setContentView(View)} or {@link #setLoadingViewResId(int)},
 * and you can switch state by call {@link #setState(int)}.
 *
 * @author objectlife (wangyuyanmail[at]gmail[dot]com)
 */
public class StateLayout extends FrameLayout {

    @IntDef({VIEW_CONTENT, VIEW_EMPTY, VIEW_ERROR, VIEW_LOADING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {}

    public static final int VIEW_CONTENT = 0x00000000;
    public static final int VIEW_EMPTY   = 0x00000001;
    public static final int VIEW_ERROR   = 0x00000002;
    public static final int VIEW_LOADING = 0x00000003;

    private View mContentView;
    private View mEmptyView;
    private View mErrorView;
    private View mLoadingView;

    private int defViewState = VIEW_LOADING;

    public StateLayout(Context context) {
        this(context, null);
    }

    public StateLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StateLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *<p>Set content view.</p>
     * @param contentView The content view to add
     * @return This StateLayout object to allow for chaining of calls to set methods
     */
    public StateLayout setContentView(View contentView) {
        this.mContentView = contentView;
        initStateView(mContentView);
        return this;
    }

    /**
     *<p>Specify content view with the given id</p>
     * @param viewResId The id to specify
     * @return This StateLayout object to allow for chaining of calls to set methods
     */
    public StateLayout setContentViewResId(int viewResId) {
        mContentView = findViewById(viewResId);
        return this;
    }

    /**
     *<p>Set empty view.</p>
     * @param emptyView The empty view to add
     * @return This StateLayout object to allow for chaining of calls to set methods
     */
    public StateLayout setEmptyView(View emptyView) {
        this.mEmptyView = emptyView;
        initStateView(mEmptyView);
        return this;
    }

    /**
     *<p>Specify empty view with the given id</p>
     * @param viewResId The id to specify
     * @return This StateLayout object to allow for chaining of calls to set methods
     */
    public StateLayout setEmptyViewResId(int viewResId) {
        mEmptyView = findViewById(viewResId);
        return this;
    }

    /**
     *<p>set error view.</p>
     * @param errorView the error view to add
     * @return This StateLayout object to allow for chaining of calls to set methods
     */
    public StateLayout setErrorView(View errorView) {
        this.mErrorView = errorView;
        initStateView(mErrorView);
        return this;
    }

    /**
     *<p>Specify error view with the given id</p>
     * @param viewResId The id to specify
     * @return This StateLayout object to allow for chaining of calls to set methods
     */
    public StateLayout setErrorViewResId(int viewResId) {
        mErrorView = findViewById(viewResId);
        return this;
    }

    /**
     *<p>Set loading view.</p>
     * @param loadingView the loading view to add
     * @return This StateLayout object to allow for chaining of calls to set methods
     */
    public StateLayout setLoadingView(View loadingView) {
        this.mLoadingView = loadingView;
        initStateView(mLoadingView);
        return this;
    }

    /**
     *<p>Specify loading view with the given id</p>
     * @param viewResId The id to specify
     * @return This StateLayout object to allow for chaining of calls to set methods
     */
    public StateLayout setLoadingViewResId(int viewResId) {
        mLoadingView = findViewById(viewResId);
        return this;
    }

    /**
     * first init and call one of
     * {@link #setContentView(View)}
     * {@link #setEmptyView(View)}
     * {@link #setErrorView(View)}
     * {@link #setLoadingView(View)} ,you must call it to init state.
     *
     * @param state
     */
    public void initWithState(@ViewState int state) {
        if (state == defViewState){// default view state
            showLoadingView();
        } else {
            setState(state);
        }
    }


    public void setState(@ViewState int state) {
        if (defViewState == state) {
            return;
        }
        defViewState = state;
        switch (state) {
            case VIEW_CONTENT:
                showContentView();
                break;

            case VIEW_EMPTY:
                showEmptyView();
                break;

            case VIEW_ERROR:
                showErrorView();
                break;

            case VIEW_LOADING:
                showLoadingView();
                break;
        }
    }

    private void initStateView(View stateView) {
        if (stateView != null) {
            addView(stateView);
        }
    }

    private void showContentView() {
        showView(mContentView);
        hideView(mEmptyView);
        hideView(mErrorView);
        hideView(mLoadingView);
    }

    private void showEmptyView() {
        showView(mEmptyView);
        hideView(mContentView);
        hideView(mErrorView);
        hideView(mLoadingView);
    }

    private void showErrorView() {
        showView(mErrorView);
        hideView(mEmptyView);
        hideView(mContentView);
        hideView(mLoadingView);
    }

    private void showLoadingView() {
        showView(mLoadingView);
        hideView(mEmptyView);
        hideView(mErrorView);
        hideView(mContentView);
    }


    private void showView(View view) {
        if (view != null) {
            view.setVisibility(VISIBLE);
        }
    }

    private void hideView(View view) {
        if (view != null) {
            view.setVisibility(GONE);
        }
    }

}
