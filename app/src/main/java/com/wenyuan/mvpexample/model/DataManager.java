package com.wenyuan.mvpexample.model;

import com.google.gson.reflect.TypeToken;
import com.wenyuan.mvpexample.Presenter.GithubPresenterImpl;

import java.util.Map;

/**
 * Created by wenyuan on 2017/2/18 0:12.
 * Description: <h1>多项model的控制集合</h1>
 * <p>
 * 在此接口中定义 各个model层操作数据的方法，然后通过实现类实现.
 * 接口中用到的泛型T通过外部调用方法的
 * </p>
 */

public interface DataManager {


    /**
     * 从github上获取数据
     *
     * @param url
     * @param parem
     * @param flag
     * @param tClass
     * @param tGithubPresenter
     */
    <T> void getGithubData(String url, Map parem, String flag, Class<T> tClass, GithubPresenterImpl tGithubPresenter);

    /**
     * 从github上获取数据
     *
     * @param url
     * @param parem
     * @param flag
     * @param tTypeToken
     * @param tGithubPresenter
     * @param <T>
     */
    <T> void getGithubData(String url, Map parem, String flag, TypeToken<T> tTypeToken, GithubPresenterImpl tGithubPresenter);


    /**
     * 获取本地数据库中的数据的model
     *
     * @param <T>
     */
    <T> void getDataFromDB(Class<T> tClass);
}
