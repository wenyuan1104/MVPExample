package com.wenyuan.mvpexample.model;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;

/**
 * Created by wenyuan on 2017/2/12 23:38.
 * Description:
 */

public class GithubModelImpl<T> implements GithubModel<T> {

    //    private GithubContract.GithubPresenter mGithubPresenter;//提供者 mvp模式的presenter
    private GithubMCallback<T> mGithubCallback;
    private URL mURL;
    private Class<T> mTClass;
    private TypeToken<T> mTypeToken;

    public GithubModelImpl(Class<T> pClass) {
        this.mTClass = pClass;
    }

    public GithubModelImpl(TypeToken<T> tTypeToken) {
        this.mTypeToken = tTypeToken;
    }

    @Override
    public void getGithubData(String url, Map param, GithubMCallback<T> tGithubMCallback) {
        try {
            this.mGithubCallback = tGithubMCallback;
            mURL = new URL(url);
            new GithubTask().execute(mURL);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private class GithubTask extends AsyncTask<URL, Void, T> {

        @Override
        protected T doInBackground(URL... params) {
            try {
                URL url = params[0];
                //打开链接
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                //设置请求方式 get post
                urlConnection.setRequestMethod("GET");
                //设置请求超时
                urlConnection.setConnectTimeout(5000);
                //设置读数据超时
                urlConnection.setReadTimeout(5000);
                //获取响应码
                int code = urlConnection.getResponseCode();
                if (code == 200) {//请求成功
                    //提取 并 解析数据
                    //1、提取数据
                    BufferedReader reader = new BufferedReader(
                            new InputStreamReader(urlConnection.getInputStream()));
                    char[] buf = new char[2048];
                    StringBuilder builder = new StringBuilder();
                    int size = 0;
                    while (-1 != (size = reader.read(buf))) {
                        builder.append(buf, 0, size);
                    }
                    reader.close();//关闭流
                    //2、解析数据
                    T parsedGSON = null;
                    if (null != mTClass)
                        parsedGSON = (T) new Gson().fromJson(builder.toString(), mTClass);
                    else if (null != mTypeToken)
                        parsedGSON = (T) new Gson().fromJson(builder.toString(), mTypeToken.getType());

                    return parsedGSON;
                } else {
                    Log.d("GithubModelImpl", "code:" + code + "==>请求失败信息：" + urlConnection.getResponseMessage());
                    return null;
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(T t) {
            if (t == null) {
                mGithubCallback.callbackFail("Fail");
//                mGithubPresenter.onRequestFail("Fail", mRequestFlag);
            } else {
                mGithubCallback.callbackSuceed(t);
//                mGithubPresenter.onRequestSuccess(t, mRequestFlag);
            }
        }
    }
}
