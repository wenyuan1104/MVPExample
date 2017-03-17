package com.wenyuan.mvpexample.model;

import com.google.gson.reflect.TypeToken;
import com.wenyuan.mvpexample.Presenter.GithubPresenterImpl;

import java.util.Map;

/**
 * Created by wenyuan on 2017/2/18 20:52.
 * Description:
 */

public class GithubDataManager implements DataManager {

    private static volatile GithubDataManager mInstance;

    /**
     * 安全的单例模式
     *
     * @return
     */
    public static GithubDataManager getInstance() {
        GithubDataManager instance = mInstance;
        if (instance == null) {
            synchronized (GithubDataManager.class) {
                instance = mInstance;
                if (null == instance) {
                    instance = new GithubDataManager();
                    mInstance = instance;
                }
            }
        }
        return instance;
    }

    /**
     * 通过网络 从github上获取数据 的model
     *
     * @param url
     * @param parem
     * @param flag
     * @param tClass
     * @param tGithubPresenter
     * @param <T>
     * @return
     */
    @Override
    public <T> void getGithubData(String url, Map parem, final String flag, Class<T> tClass, final GithubPresenterImpl tGithubPresenter) {
        new GithubModelImpl<T>(tClass).getGithubData(url, parem, new GithubModel.GithubMCallback<T>() {
            @Override
            public void callbackSuceed(T t) {
                tGithubPresenter.onRequestSuccess(t, flag);
            }

            @Override
            public void callbackFail(String mag) {
                tGithubPresenter.onRequestFail(mag, flag);
            }
        });
    }

    @Override
    public <T> void getGithubData(String url, Map parem, final String flag, TypeToken<T> tTypeToken, final GithubPresenterImpl tGithubPresenter) {
        new GithubModelImpl<T>(tTypeToken).getGithubData(url, parem, new GithubModel.GithubMCallback<T>() {
            @Override
            public void callbackSuceed(T t) {
                tGithubPresenter.onRequestSuccess(t, flag);
            }

            @Override
            public void callbackFail(String mag) {
                tGithubPresenter.onRequestFail(mag, flag);
            }
        });
    }

    /**
     * 获取本地数据库中的数据的model
     *
     * @param tClass
     */
    @Override
    public <T> void getDataFromDB(Class<T> tClass) {
    }
}
