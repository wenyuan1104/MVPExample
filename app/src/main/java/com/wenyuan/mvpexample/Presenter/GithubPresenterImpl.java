package com.wenyuan.mvpexample.Presenter;

import com.google.gson.reflect.TypeToken;
import com.wenyuan.mvpexample.GithubContract;
import com.wenyuan.mvpexample.model.GithubDataManager;

import java.util.Map;

/**
 * Created by wenyuan on 2017/2/12 23:38.
 * Description:
 */

public class GithubPresenterImpl implements GithubContract.GithubPresenter {

    private GithubContract.GithubView mGithubView;//mvp模式的视图控制

    public GithubPresenterImpl(GithubContract.GithubView githubView) {
        this.mGithubView = githubView;
    }


    @Override
    public <T> void onRequest(String url, Map param, Class<T> tClass,String flag) {
        mGithubView.showLoading();
        GithubDataManager.getInstance().getGithubData(url, param, flag, tClass, this);
//        this.mGithubModel.getGithubData(url, param, flag);//之前的另一种方法 在presenter层直接去控制model
    }

    @Override
    public <T> void onRequest(String url, Map param, TypeToken<T> tTypeToken, String flag) {
        mGithubView.showLoading();
        GithubDataManager.getInstance().getGithubData(url, param, flag, tTypeToken, this);
    }

    @Override
    public <T> void onRequestSuccess(T data, String flag) {
        mGithubView.showSuccessHandle(data, flag);
    }

    @Override
    public void onRequestFail(String msg, String flag) {
        mGithubView.showFailHandle(msg, flag);
    }
}
