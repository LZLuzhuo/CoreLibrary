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
package me.luzhuo.lib_core.data;

import android.net.Uri;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import androidx.core.util.Pair;

/**
 * Description: 无论是 Android 的 Uri 还是 Java 的 URI, 对path中有#字符都会造成错误的处理.
 * 本Uri管理类解决了这个问题
 *
 * Uri结构:
 * 1. 基本结构
 * [scheme:]scheme-specific-part[#fragment]
 * 2. scheme-specific-part 进一步划分
 * [scheme:][//authority][path][?query]][#fragment]
 * 3. 最终结构
 * [scheme:][//host:port][path][?query]][#fragment]
 *
 * @Author: Luzhuo
 * @Creation Date: 2021/10/15 0:10
 * @Copyright: Copyright 2021 Luzhuo. All rights reserved.
 **/
public class UriManager {
    private String uriString;
    private String scheme;
    private String authority;
    private String paths;
    private List<Pair<String, String>> queryParameter = new ArrayList<>();
    private String fragment;

    private int schemeLength;
    private int authorityLength;
    private int pathsLength;
    private int queryParameterLength;
    private int fragmentLength;

    public UriManager() { }

    public UriManager(String uri) {
        if (uri == null) uri = "";
        this.uriString = uri;

        setScheme(getScheme());
        setAuthority(getAuthority());
        setPaths(getPath());
        getQueryParameter();
        setFragment(getFragment());
    }

    /**
     * 设置命名空间
     */
    public UriManager setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    /**
     * 设置主机名
     */
    public UriManager setAuthority(String authority) {
        this.authority = authority;
        return this;
    }

    /**
     * 设置路径
     */
    public UriManager setPaths(String paths) {
        if (!TextUtils.isEmpty(paths) && paths.startsWith("/")) paths = paths.substring(1);
        this.paths = paths;
        return this;
    }

    /**
     * 设置参数
     */
    public UriManager setQueryParameters(List<Pair<String, String>> parameters) {
        this.queryParameter.clear();
        this.queryParameter.addAll(parameters);
        return this;
    }

    public UriManager addQueryParameter(String key, String value) {
        this.queryParameter.add(new Pair(key, value));
        return this;
    }

    public UriManager addQueryParameter(List<Pair<String, String>> parameters) {
        this.queryParameter.addAll(parameters);
        return this;
    }

    /**
     * 删除指定参数
     */
    public UriManager removeQueryParameter(String key, String value) {
        queryParameter.remove(new Pair(key, value));
        return this;
    }

    public UriManager removeQueryParameter(String... keys) {
        for (String key : keys) {
            final ListIterator<Pair<String, String>> lit = this.queryParameter.listIterator();
            while(lit.hasNext()) {
                if(lit.next().first.equals(key)) lit.remove();
            }
        }
        return this;
    }

    /**
     * 设置标签
     */
    public UriManager setFragment(String fragment) {
        this.fragment = fragment;
        return this;
    }

    public Uri getUri() {
        final Uri.Builder builder = new Uri.Builder();
        if (!TextUtils.isEmpty(this.scheme)) builder.scheme(this.scheme);
        if (!TextUtils.isEmpty(this.authority)) builder.authority(this.authority);
        if (!TextUtils.isEmpty(this.paths)) builder.appendEncodedPath(this.paths);
        for (Pair<String, String> parameterPair : queryParameter) {
            builder.appendQueryParameter(parameterPair.first, parameterPair.second);
        }
        if (!TextUtils.isEmpty(this.fragment)) builder.fragment(this.fragment);
        return builder.build();
    }

    @Override
    public String toString() {
        /*String log =  "UriManager{" +
                "scheme='" + scheme + '\'' +
                ", authority='" + authority + '\'' +
                ", paths='" + paths + '\'' +
                ", queryParameter=" + queryParameter +
                ", fragment='" + fragment + '\'' +
                '}';*/
        return getUri().toString();
    }

    protected String getScheme() {
        int ssi = uriString.indexOf(':');
        if (ssi < 0) {
            this.schemeLength = 0;
            return null;
        }

        String scheme = uriString.substring(0, ssi);
        this.schemeLength = scheme.length() + 1;
        return scheme;
    }

    protected String getAuthority() {
        String pendingPath = uriString.substring(schemeLength);
        int length = pendingPath.length();
        if (length > 2 && pendingPath.charAt(0) == '/' && pendingPath.charAt(1) == '/') {

            int end = 2;
            LOOP: while (end < length) {
                switch (pendingPath.charAt(end)) {
                    case '/': // Start of path
                    case '\\':// Start of path
                    case '?': // Start of query
                    case '#': // Start of fragment
                        break LOOP;
                }
                end++;
            }
            String authority = pendingPath.substring(2, end);
            this.authorityLength = authority.length() + 2;
            return authority;
        }
        this.authorityLength = 0;
        return null;
    }

    protected int findSSI() {
        return schemeLength + authorityLength + pathsLength + queryParameterLength + fragmentLength;
    }

    protected String getPath() {
        int ssi = findSSI();

        String tempPath = "";
        String pendingPath = uriString.substring(ssi);

        String[] pathSplit = pendingPath.split("/");
        for (int i = 0; i < pathSplit.length - 1; i++) {
            if (TextUtils.isEmpty(pathSplit[i])) {
                this.pathsLength += 1;
                continue;
            }
            String childPath = pathSplit[i] + "/";
            tempPath += childPath;
            this.pathsLength += childPath.length();
        }

        if (pathSplit.length <= 0) {
            this.pathsLength = 0;
            return null;
        }

        // home?token=3605cf6618e63145a1af352d5f7d0fdf"
        int pathEnd = 0;
        String pathLast = pathSplit[pathSplit.length - 1];
        LOOP: while (pathEnd < pathLast.length()) {
            switch (pathLast.charAt(pathEnd)) {
                case '?': // Start of query
                case '#': // Start of fragment
                    break LOOP;
            }
            pathEnd++;
        }

        String childPath = pathLast.substring(0, pathEnd);
        tempPath += childPath;
        this.pathsLength = this.pathsLength + childPath.length();

        return tempPath;
    }

    protected void getQueryParameter() {
        int ssi = findSSI();
        String pendingPath = uriString.substring(ssi);
        if (pendingPath.startsWith("?")) {
            this.queryParameterLength += 1;
            pendingPath = pendingPath.substring(1);

            for (String key_value : pendingPath.split("&")) {
                this.queryParameterLength += 1;
                String[] keyValue = key_value.split("=");
                if (keyValue.length >= 2) {
                    queryParameter.add(Pair.create(keyValue[0], keyValue[1]));
                    this.queryParameterLength += keyValue[0].length() + keyValue[1].length() + 1;
                } else {
                    this.queryParameterLength += keyValue[0].length() + 1;
                }
            }
            this.queryParameterLength -= 1;

            if (queryParameter.size() > 0) {
                String lastQueryParameter = queryParameter.get(queryParameter.size() - 1).second;
                if (lastQueryParameter.contains("#")) {
                    ssi = lastQueryParameter.indexOf("#");
                    String childLastQueryParameter = lastQueryParameter.substring(0, ssi);

                    Pair<String, String> data = queryParameter.remove(queryParameter.size() - 1);
                    queryParameter.add(Pair.create(data.first, childLastQueryParameter));

                    this.queryParameterLength += - lastQueryParameter.length() + childLastQueryParameter.length();
                }
            }
        } else {
            this.queryParameterLength = 0;
        }
    }

    protected String getFragment() {
        int ssi = findSSI();
        String pendingPath = uriString.substring(ssi);
        if (pendingPath.startsWith("#")) {
            this.fragmentLength = pendingPath.length();
            return pendingPath.substring(1);
        } else {
            this.fragmentLength = 0;
            return null;
        }
    }

    public String scheme() {
        return this.scheme;
    }

    public String authority() {
        return this.authority;
    }

    public String paths() {
        return this.paths;
    }

    public List<Pair<String, String>> queryParameter() {
        return this.queryParameter;
    }

    public String fragment() {
        return this.fragment;
    }
}