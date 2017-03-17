package com.wenyuan.mvpexample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.wenyuan.mvpexample.Presenter.GithubPresenterImpl;

/**
 * 使用MVP模式
 */
public class ModeActivity extends AppCompatActivity implements View.OnClickListener, GithubContract.GithubView {

    private static final String REQUEST_TAG_GITHUB = "github";
    private static final String REQUEST_TAG_GITHUB_2 = "github_2";

    private Button mButMvpGet;
    private ProgressBar mLoadingMvp;
    private TextView mTextMvpShow;
    private Button mButMvpGet2;
    private Toolbar mToolbar;

    private GithubContract.GithubPresenter mGithubPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvp);
        initView();
        mGithubPresenter = new GithubPresenterImpl(this);
    }


    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle(getResources().getString(R.string.app_name));
        setSupportActionBar(mToolbar);
        mButMvpGet = (Button) findViewById(R.id.but_mvp_get);
        mButMvpGet.setOnClickListener(this);
        mLoadingMvp = (ProgressBar) findViewById(R.id.loading_mvp);
        mLoadingMvp.setOnClickListener(this);
        mTextMvpShow = (TextView) findViewById(R.id.text_mvp_show);
        mTextMvpShow.setOnClickListener(this);
        mButMvpGet2 = (Button) findViewById(R.id.but_mvp_get_2);
        mButMvpGet2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.but_mvp_get:
                mGithubPresenter
                        .onRequest("https://api.github.com/repos/CymChad/BaseRecyclerViewAdapterHelper", null, GithubBean.class, REQUEST_TAG_GITHUB);
//                mGithubPresenter
//                        .onRequest("https://api.github.com/repos/CymChad/BaseRecyclerViewAdapterHelper", null, new TypeToken<GithubBean>(){},REQUEST_TAG_GITHUB);
                break;
            case R.id.but_mvp_get_2:
                mGithubPresenter
                        .onRequest("https://api.github.com/repos/google/guava", null, new TypeToken<GithubBean>() {
                        }, REQUEST_TAG_GITHUB_2);
                break;
        }
    }

    /**
     * 请求显示加载中
     */
    @Override
    public void showLoading() {
        mLoadingMvp.setVisibility(0);
    }

    /**
     * 请求失败的操作
     *
     * @param tag request flag
     */
    @Override
    public void showFailHandle(String msg, String flag) {
        if (REQUEST_TAG_GITHUB.equals(flag)) {
            mLoadingMvp.setVisibility(View.GONE);
            mTextMvpShow.setText(msg);
        } else if (REQUEST_TAG_GITHUB_2.equals(flag)) {
            mLoadingMvp.setVisibility(View.GONE);
            mTextMvpShow.setText(msg);
        }
    }

    /**
     * 请求成功的操作
     *
     * @param tag request flag
     */
    @Override
    public void showSuccessHandle(Object data, String flag) {
        if (REQUEST_TAG_GITHUB.equals(flag)) {
            mLoadingMvp.setVisibility(View.GONE);
            mTextMvpShow.setText("name：" + ((GithubBean) data).getName());
        } else if (REQUEST_TAG_GITHUB_2.equals(flag)) {
            mLoadingMvp.setVisibility(View.GONE);
            mTextMvpShow.setText("full_name：" + ((GithubBean) data).getFull_name());
        }
    }

}
