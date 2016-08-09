# AndroidStateLayout
-
A library for showing different state of views - Content,Empty,Loading,Error.
![state-layout](https://github.com/objectlife/StateLayout/blob/master/screenshot/state_layout.gif)

##Usage

###1.IMPORT
* Download and import the library module into your workspace.
* OR
* Add these lines to `build.gradle` of your project

```
dependencies {
    compile 'com.objectlife.statelayout:statelayout:1.0.0'
}
```

###2.CODE
####2.1. If ***`has no child`*** in your statelayout , you can use it as follow
```
<com.objectlife.statelayout.StateLayout
        android:id="@+id/sl_layout_state"
        android:layout_width="match_parent"
        android:layout_height="match_parent"/>
```
```
mInflater = LayoutInflater.from(this);

        mStateLayout = (StateLayout) findViewById(R.id.sl_layout_state);
        View contentView = mInflater.inflate(R.layout.view_content,mStateLayout,false);
        View emptyView = mInflater.inflate(R.layout.view_empty,mStateLayout,false);
        View errorView = mInflater.inflate(R.layout.view_error,mStateLayout,false);
        View loadingView = mInflater.inflate(R.layout.view_loading,mStateLayout,false);

        mStateLayout.setEmptyView(emptyView)
                .setContentView(contentView)
                .setErrorView(errorView)
                .setLoadingView(loadingView)
                .initState(StateLayout.VIEW_LOADING);
```
####2.2. If ***`has child`*** in your statelayout , you can use it as follow
```
<com.objectlife.statelayout.StateLayout
        android:id="@+id/sl_layout_state"
        android:layout_width="match_parent"
        android:layout_height="match_parent">

    <include
        android:id="@+id/v_content"
        layout="@layout/view_content"/>

    <include
        android:id="@+id/v_empty"
        layout="@layout/view_empty"/>

    <include
        android:id="@+id/v_error"
        layout="@layout/view_error"/>

    <include
        android:id="@+id/v_loading"
        layout="@layout/view_loading"/>

    </com.objectlife.statelayout.StateLayout>
```
```
mStateLayout = (StateLayout) findViewById(R.id.sl_layout_state);
        mStateLayout.setContentViewResId(R.id.v_content)
                .setErrorViewResId(R.id.v_error)
                .setEmptyViewResId(R.id.v_empty)
                .setLoadingViewResId(R.id.v_loading)
                .initState(StateLayout.VIEW_LOADING);
```
####2.3.Switch view
```
mStateLayout.setState(StateLayout.VIEW_CONTENT);
```
###License
-
```
Copyright (C) 2016 objectlife
Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and limitations under the License.
```