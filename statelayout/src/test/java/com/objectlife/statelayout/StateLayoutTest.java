package com.objectlife.statelayout;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import android.content.Context;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = 28)
public class StateLayoutTest {

    private Context context;
    private StateLayout layout;
    private View content;
    private View empty;
    private View error;
    private View loading;

    @Before
    public void setUp() {
        context = RuntimeEnvironment.getApplication();
        layout = new StateLayout(context);
        content = new View(context);
        empty = new View(context);
        error = new View(context);
        loading = new View(context);
        layout.setContentView(content)
                .setEmptyView(empty)
                .setErrorView(error)
                .setLoadingView(loading);
    }

    @Test
    public void setStateShowsOnlySelectedView() {
        layout.setState(StateLayout.VIEW_ERROR);

        assertEquals(View.GONE, content.getVisibility());
        assertEquals(View.GONE, empty.getVisibility());
        assertEquals(View.VISIBLE, error.getVisibility());
        assertEquals(View.GONE, loading.getVisibility());
        assertEquals(StateLayout.VIEW_ERROR, layout.getState());
    }

    @Test
    public void eachSupportedStateCanBeSelected() {
        View[] views = {content, empty, error, loading};
        for (int state = StateLayout.VIEW_CONTENT; state <= StateLayout.VIEW_LOADING; state++) {
            layout.setState(state);
            for (int index = 0; index < views.length; index++) {
                assertEquals(index == state ? View.VISIBLE : View.GONE, views[index].getVisibility());
            }
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidStateIsRejected() {
        layout.setState(99);
    }

    @Test(expected = IllegalArgumentException.class)
    public void missingViewIdIsRejected() {
        layout.setContentViewResId(View.generateViewId());
    }

    @Test
    public void assigningTheSameViewTwiceDoesNotDuplicateIt() {
        int childCount = layout.getChildCount();

        layout.setContentView(content);

        assertEquals(childCount, layout.getChildCount());
    }

    @Test
    public void legacyPublicApiRemainsAvailable() throws Exception {
        assertSame(StateLayout.class, StateLayout.class.getMethod(
                "setContentView", View.class).getReturnType());
        assertSame(StateLayout.class, StateLayout.class.getMethod(
                "setContentViewResId", int.class).getReturnType());
        assertSame(StateLayout.class, StateLayout.class.getMethod(
                "setEmptyView", View.class).getReturnType());
        assertSame(StateLayout.class, StateLayout.class.getMethod(
                "setEmptyViewResId", int.class).getReturnType());
        assertSame(StateLayout.class, StateLayout.class.getMethod(
                "setErrorView", View.class).getReturnType());
        assertSame(StateLayout.class, StateLayout.class.getMethod(
                "setErrorViewResId", int.class).getReturnType());
        assertSame(StateLayout.class, StateLayout.class.getMethod(
                "setLoadingView", View.class).getReturnType());
        assertSame(StateLayout.class, StateLayout.class.getMethod(
                "setLoadingViewResId", int.class).getReturnType());
        assertSame(Void.TYPE, StateLayout.class.getMethod(
                "initWithState", int.class).getReturnType());
        assertSame(Void.TYPE, StateLayout.class.getMethod(
                "setState", int.class).getReturnType());
        assertSame(Integer.TYPE, StateLayout.class.getMethod("getState").getReturnType());
        StateLayout.class.getConstructor(Context.class);
        StateLayout.class.getConstructor(Context.class, AttributeSet.class);
        StateLayout.class.getConstructor(Context.class, AttributeSet.class, int.class);
    }

    @Test
    public void replacingProgrammaticViewRemovesOldChild() {
        View replacement = new View(context);

        layout.setContentView(replacement);

        assertEquals(-1, layout.indexOfChild(content));
        assertSame(replacement, layout.getChildAt(layout.indexOfChild(replacement)));
    }

    @Test
    public void stateSurvivesSaveAndRestore() {
        layout.setId(View.generateViewId());
        layout.setState(StateLayout.VIEW_EMPTY);
        Parcelable savedState = layout.onSaveInstanceState();

        StateLayout restored = new StateLayout(context);
        restored.setId(layout.getId());
        View restoredEmpty = new View(context);
        restored.setEmptyView(restoredEmpty);
        restored.onRestoreInstanceState(savedState);

        assertEquals(StateLayout.VIEW_EMPTY, restored.getState());
        assertEquals(View.VISIBLE, restoredEmpty.getVisibility());
    }

    @Test
    @SuppressWarnings("deprecation")
    public void xmlAttributesResolveViewsAndInitialState() {
        AttributeSet attributes = Robolectric.buildAttributeSet()
                .addAttribute(R.attr.sl_contentView, "@android:id/content")
                .addAttribute(R.attr.sl_emptyView, "@android:id/empty")
                .addAttribute(R.attr.sl_loadingView, "@android:id/progress")
                .addAttribute(R.attr.sl_initialState, "empty")
                .build();
        StateLayout inflated = new StateLayout(context, attributes);
        View inflatedContent = viewWithId(android.R.id.content);
        View inflatedEmpty = viewWithId(android.R.id.empty);
        View inflatedLoading = viewWithId(android.R.id.progress);
        inflated.addView(inflatedContent);
        inflated.addView(inflatedEmpty);
        inflated.addView(inflatedLoading);
        inflated.onFinishInflate();

        assertEquals(StateLayout.VIEW_EMPTY, inflated.getState());
        assertEquals(View.GONE, inflatedContent.getVisibility());
        assertEquals(View.VISIBLE, inflatedEmpty.getVisibility());
        assertEquals(View.GONE, inflatedLoading.getVisibility());
    }

    private View viewWithId(int id) {
        View view = new View(context);
        view.setId(id);
        return view;
    }
}
