/* Copyright 2021 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.app.appinfo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

/**
 * Description:
 * Activity的统一关闭管理
 * 用于在某些特定情况下, 需要关闭一组Activity的情况
 *
 * @Author: Luzhuo
 * @Creation Date: 2021/12/24 19:16
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class ActivityFinishManager {
    private final Map<String, List<FragmentActivity>> activitys = new LinkedHashMap<>();

    private ActivityFinishManager() { }
    private static class Instance{
        private static final ActivityFinishManager instance = new ActivityFinishManager();
    }

    public static ActivityFinishManager getInstance() {
        return Instance.instance;
    }

    public void add(@NonNull String tag, @NonNull FragmentActivity activity) {
        List<FragmentActivity> fragmentActivities = activitys.get(tag);
        if (fragmentActivities == null) {
            List<FragmentActivity> activityList = new ArrayList<>();
            activityList.add(activity);
            activitys.put(tag, activityList);
        } else {
            fragmentActivities.add(activity);
        }
    }

    public void finish(@NonNull String tag) {
        List<FragmentActivity> fragmentActivities = activitys.remove(tag);
        if (fragmentActivities == null) return;

        for (FragmentActivity fragmentActivity : fragmentActivities) {
            if (!fragmentActivity.isFinishing()) fragmentActivity.finish();
        }
        fragmentActivities.clear();
    }
}
