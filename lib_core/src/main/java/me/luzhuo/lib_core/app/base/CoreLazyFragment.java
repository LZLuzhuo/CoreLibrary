/* Copyright 2022 Luzhuo. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package me.luzhuo.lib_core.app.base;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * Description: 懒加载的Fragment, 用于 ViewPager + Fragment
 * 1. 支持 setOffscreenPageLimit(max) + FragmentPagerAdapter; 通过 setUserVisibleHint(true) 去调用 initData()
 * 2. 不支持 setOffscreenPageLimit(max) + FragmentStatePagerAdapter(BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
 * 3. FragmentManager 的 replace 和 change 不受影响
 * @Author: Luzhuo
 * @Creation Date: 2022/5/18 1:17
 * @Copyright: Copyright 2022 Luzhuo. All rights reserved.
 **/
public abstract class CoreLazyFragment extends Fragment {
    protected View view;
    protected Context context;
    protected boolean initialized = false;
    protected boolean created = false;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.created = true;
        if (getUserVisibleHint() && !this.initialized) {
            this.initialized = true;
            initData(savedInstanceState);
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint() && !this.initialized && created) {
            this.initialized = true;
            initData(null);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = getActivity();
        onCreate();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        this.view = initView(inflater, container);
        return this.view;
    }

    /**
     * 初始化ViewModel
     */
    public abstract void onCreate();

    /**
     * 初始化View
     */
    public abstract View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container);

    /**
     * 初始化数据
     */
    public abstract void initData(@Nullable Bundle savedInstanceState);
}
