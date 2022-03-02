/* Copyright 2015 Luzhuo. All rights reserved.
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
package me.luzhuo.lib_core.ui.fragment;

import android.os.Bundle;

import java.util.HashSet;
import java.util.Set;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-11-12 下午1:59:06
 * 
 * 描述:这是fragment管理工具.<br>推荐使用{@link #changeFragment(Fragment)}
 * <br>{@link #changeFragment(Fragment)}方法不会影响生命周期, 占用内存高
 * <br>{@link #replaceFragment(Fragment)} 方法会将被切换的Fragment销毁, 然后创建新的Fragment代替, 占用内存低
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class FragmentManager {
	protected androidx.fragment.app.FragmentManager fragmentManager;
	protected @IdRes int containerId;
	protected Fragment oldFragment;

	/**
	 * @param containerId 放Fragment的FrameLayout容器id
	 * @param fragmentActivity
	 * @param containerId 放Fragment的FrameLayout容器id
	 */
	public FragmentManager(@NonNull FragmentActivity fragmentActivity, @IdRes int containerId){
		this.fragmentManager = fragmentActivity.getSupportFragmentManager();
		this.containerId = containerId;
	}

	public FragmentManager(@NonNull Fragment fragment, @IdRes int containerId){
		this.fragmentManager = fragment.getChildFragmentManager();
		this.containerId = containerId;
	}
	
	public void replaceFragment(@NonNull Fragment target){
		replaceFragment(target, false, null);
	}
	
	public void replaceFragment(@NonNull Fragment target, boolean isAddStack){
		replaceFragment(target, isAddStack, null);
	}
	
	public void replaceFragment(@NonNull Fragment target, @Nullable Bundle bundle){
		replaceFragment(target, false, bundle);
	}
	
	/**
	 * 切换Fragment<br>适合处理业务操作简单,数据传输安全级别要求不高的场景.<br>该方法将会销毁旧的fragment,接受new出新的fragment,推荐使用{@link #changeFragment(Fragment)}进行切换
	 * @param target 要切换的Fragment
	 * @param isAddStack 是否添加到栈里面,如果添加到栈里面可以使用返回键;不建议将第一个Fragment添加到栈里
	 * @param bundle / null;存储要传输的数据,getBundle(Fragment)获取数据
	 */
	public void replaceFragment(@NonNull Fragment target, boolean isAddStack, @Nullable Bundle bundle){
		if (bundle != null) {
			// 存储要传输的数据
			target.setArguments(bundle);
		}

		FragmentTransaction replace = fragmentManager.beginTransaction().replace(containerId, target);
		if(isAddStack) // 是否添加到栈里面,如果添加到栈里面可以使用返回键
			replace = replace.addToBackStack(null);
		replace.commitAllowingStateLoss();

		oldFragment = target;
	}
	
	/**
	 * 获取Bundle
	 * @param fragment 与存入Bundle相对应的Fragment
	 * @return Bundle
	 */
	public Bundle getBundle(@NonNull Fragment fragment){
		return fragment.getArguments();
	}
	
	public void addFragment(@NonNull Fragment target){
		addFragment(target, false, null);
	}
	
	public void addFragment(@NonNull Fragment target, boolean isAddStack){
		addFragment(target, isAddStack, null);
	}
	
	public void addFragment(@NonNull Fragment target, @Nullable Bundle bundle){
		addFragment(target, false, bundle);
	}
	
	/**
	 * 添加Fragment到FrameLayout容器中,注意:多次调用,Fragment会叠加
	 * @param target 要添加的Fragment
	 * @param isAddStack 是否添加到栈里面,如果添加到栈里面可以使用返回键;不建议将第一个Fragment添加到栈里
	 * @param bundle / null;存储要传输的数据,getBundle(Fragment)获取数据
	 */
	public void addFragment(@NonNull Fragment target, boolean isAddStack, @Nullable Bundle bundle){
		if(bundle != null){
			// 存储要传输的数据
			target.setArguments(bundle);
		}

		FragmentTransaction add = fragmentManager.beginTransaction().replace(containerId, target);
		if(isAddStack) //是否添加到栈里面,如果添加到栈里面可以使用返回键
			add = add.addToBackStack(null);
		add.commitAllowingStateLoss();

		oldFragment = target;
	}

	private Set<Fragment> fragments = new HashSet<>();
	/**
	 * 切换Fragment,推荐使用该方法,该方法不会销毁旧的Fragment.<br>该方法不需要new出新的Fragment,推荐使用数组管理<br>该方法可以处理复杂业务
	 * <br>不支持传递数据
	 * @param newFragment 切换的Fragment
	 */
	public void changeFragment(@NonNull Fragment newFragment){
		if(newFragment == oldFragment || oldFragment == null) return;
		
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		if(oldFragment != null){
			transaction.hide(oldFragment);
		}
		if (!newFragment.isAdded()) {
			// java.lang.IllegalStateException: Fragment already added:
			if (!fragments.contains(newFragment)) {
				fragments.add(newFragment);
				transaction.add(containerId, newFragment);
			}
		}
		// commitAllowingStateLoss: 会造成 onSaveInstanceState 状态信息丢失
		// commit: 不会造成 onSaveInstanceState 状态信息丢失, 但会抛出异常
		transaction.show(newFragment).commitAllowingStateLoss();

		oldFragment = newFragment;
	}
}
