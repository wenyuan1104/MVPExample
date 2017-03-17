package com.wenyuan.mvpexample.model;

import java.util.Map;

/**
 * Created by wenyuan on 2017/2/12 16:59.
 * Description: MVP模式的model层
 */

public interface GithubModel<T> {

    /**
     * 回调
     */
    interface GithubMCallback<T> {
        void callbackSuceed(T t);

        void callbackFail(String mag);
    }

    /**
     * 获取数据
     *
     * @param url
     * @param param
     * @param tGithubMCallback
     */
    void getGithubData(String url, Map param, GithubMCallback<T> tGithubMCallback);
}
