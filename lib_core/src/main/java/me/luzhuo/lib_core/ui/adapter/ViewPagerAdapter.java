/* Copyright 2020 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.ui.adapter;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/**
 * Description: Fragment的ViewPager适配器
 * @Author: Luzhuo
 * @Creation Date: 2020/3/14 17:12
 * @Copyright: Copyright 2020 Luzhuo. All rights reserved.
 **/
public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<ViewPagerBean> datas;

    public ViewPagerAdapter(AppCompatActivity activity, List<ViewPagerBean> datas) {
        this(activity.getSupportFragmentManager(), datas);
    }
    public ViewPagerAdapter(Fragment fragment, List<ViewPagerBean> datas) {
        this(fragment.getChildFragmentManager(), datas);
    }
    public ViewPagerAdapter(@NonNull FragmentManager fm, List<ViewPagerBean> datas) {
        super(fm);
        this.datas = datas;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return datas.get(position).fragment;
    }

    @Override
    public int getCount() {
        return datas.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return datas.get(position).title;
    }

    // ================================= 供外部使用的类 =================================

    public static class ViewPagerBean {
        public Fragment fragment;
        public String title;

        public ViewPagerBean(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }

        public ViewPagerBean(Fragment fragment) {
            this(fragment, null);
        }
    }
}
