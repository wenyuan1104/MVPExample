package com.wenyuan.mvpexample;

import com.google.gson.reflect.TypeToken;

import java.util.Map;

/**
 * Created by wenyuan on 2017/2/18 0:12.
 * Description:
 */

public interface GithubContract {
    /**
     * Created by wenyuan on 2017/2/12 17:02.
     * Description:MVP模式的view层
     */

    interface GithubView<T> {

        /**
         * 显示加载中
         */
        void showLoading();

        /**
         * 请求失败的操作
         *
         * @param tag request flag
         */
        void showFailHandle(String tag, String flag);

        /**
         * 请求成功的操作
         *
         * @param tag request flag
         */
        void showSuccessHandle(T tag, String flag);
    }

    /**
     * Created by wenyuan on 2017/2/12 17:01.
     * Description:MVP模式的Presenter层
     */

    interface GithubPresenter {

        /**
         * 开始请求
         */
        <T> void onRequest(String url, Map param, Class<T> tClass, String flag);

        /**
         * 开始请求
         */
        <T> void onRequest(String url, Map param, TypeToken<T> tTypeToken, String flag);

        /**
         * 请求成功的回调
         *
         * @param data 数据
         */
        <T> void onRequestSuccess(T data, String flag);

        /**
         * 请求失败的回调
         *
         * @param msg 信息
         */
        void onRequestFail(String msg, String flag);

    }
}
