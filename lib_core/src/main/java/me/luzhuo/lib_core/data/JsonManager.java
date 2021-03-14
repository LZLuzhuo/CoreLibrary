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
package me.luzhuo.lib_core.data;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.List;
import java.util.Map;

/**
 * =================================================
 * 
 * 作者:卢卓
 * 
 * 版本:1.0
 * 
 * 创建日期:2015-9-3 下午8:37:27
 * 
 * 描述:Json数据的各种转换,
 * 	       该类实质使用的是Gson.
 * 	    该类只处理基本类型.
 * 
 * 修订历史:
 * 
 * 
 * =================================================
 **/
public class JsonManager {
	private Gson gson = new Gson();

	/**
	 * Bean --> Json
	 * @param object Bean
	 * @return jsonString
	 */
	public String bean2Json(Object object) {
		if (object == null) return null;

		String gsonString = gson.toJson(object);
		return gsonString;
	}

	/**
	 * Json --> Bean
	 * @param json jsonString
	 * @param cls Bean.class
	 * @return Bean
	 */
	public <T> T json2Bean(String json, Class<T> cls) {
		if (TextUtils.isEmpty(json)) return null;

		T t = gson.fromJson(json, cls);
		return t;
	}
	
	/**
	 * List --> Json
	 * @param list List
	 * @return jsonString
	 */
	public String list2Json(List list){
		if (list == null) return null;

		return bean2Json(list);
	}
	
	/**
	 * Json --> List
	 * @param json jsonString
	 * @return List<Bean>
	 */
	public <T> List<T> json2List(String json) {
		if (TextUtils.isEmpty(json)) return null;

		List<T> list = gson.fromJson(json, new TypeToken<List<T>>() {}.getType());
		return list;
	}
	
	/**
	 * Map --> Json
	 * @param map Map
	 * @return jsonString
	 */
	public String map2Json(Map map){
		String json = gson.toJson(map);
		return json;
	}
	
	/**
	 * Json --> Map
	 * @param json jsonString
	 * @return Map<String, T> T: String会被转成String类型, Int会被转成Double类型
	 */
	public <T> Map<String, T> json2Map(String json) {
		Map<String, T> map = gson.fromJson(json, new TypeToken<Map<String, T>>() {}.getType());
		return map;
	}
}
