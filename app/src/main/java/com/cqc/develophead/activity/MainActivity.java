package com.cqc.develophead.activity;

import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;

import com.cqc.develophead.R;
import com.cqc.develophead.fragment.CollectFragment;
import com.cqc.develophead.fragment.CooperationFragment;
import com.cqc.develophead.fragment.CreateSubjectFragment;
import com.cqc.develophead.fragment.FeedBackFragment;
import com.cqc.develophead.fragment.GiftFragment;
import com.cqc.develophead.fragment.HomeFragment;
import com.cqc.develophead.fragment.MySubjectFragment;
import com.cqc.develophead.fragment.ShareFragment;
import com.cqc.develophead.fragment.SubsriptionFragment;
import com.cqc.develophead.utils.ToastUtil;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Context context;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RelativeLayout rl_home;
    private RelativeLayout rl_gift;
    private RelativeLayout rl_share;
    private RelativeLayout rl_my_subjects;
    private RelativeLayout rl_my_collect;
    private RelativeLayout rl_create_subject;
    private RelativeLayout rl_my_create_subjects;
    private RelativeLayout rl_feedback;
    private RelativeLayout rl_cooperation;
    private FragmentManager fm;
    private FragmentTransaction transaction;
    private HomeFragment homeFragment;//首页
    private GiftFragment giftFragment;//礼物兑换
    private ShareFragment shareFragment;//我的分享
    private SubsriptionFragment mySubsFragment;//我的订阅
    private CollectFragment myCollectFragment;//我的收藏
    private CreateSubjectFragment createSubFragment;//立即创建主题
    private MySubjectFragment myCreateSubFragment;//我创建的主题
    private FeedBackFragment feedBackFragment;//意见反馈
    private CooperationFragment cooperationFragment;//合作申请

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        context = this;
        findViews();
        initViews();
    }


    private void findViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        rl_home = (RelativeLayout) findViewById(R.id.rl_home);
        rl_gift = (RelativeLayout) findViewById(R.id.rl_gift);
        rl_share = (RelativeLayout) findViewById(R.id.rl_share);
        rl_my_subjects = (RelativeLayout) findViewById(R.id.rl_subjects);
        rl_my_collect = (RelativeLayout) findViewById(R.id.rl_favorites);
        rl_create_subject = (RelativeLayout) findViewById(R.id.rl_create_subject);
        rl_my_create_subjects = (RelativeLayout) findViewById(R.id.rl_my_subjects);
        rl_feedback = (RelativeLayout) findViewById(R.id.rl_feedback);
        rl_cooperation = (RelativeLayout) findViewById(R.id.rl_cooperation);
    }

    private void initViews() {
        initToolBar();

        //默认用首页替换
        fm = getSupportFragmentManager();
        homeFragment = new HomeFragment();
        fm.beginTransaction().add(R.id.framelayout_content, homeFragment, "HomeFragment").commit();

        // 侧拉页默认选中“首页”
        rl_home.setSelected(true);

        // 点击改变字体颜色和内容页
        rl_home.setOnClickListener(this);
        rl_gift.setOnClickListener(this);
        rl_share.setOnClickListener(this);
        rl_my_subjects.setOnClickListener(this);
        rl_my_collect.setOnClickListener(this);
        rl_create_subject.setOnClickListener(this);
        rl_my_create_subjects.setOnClickListener(this);
        rl_feedback.setOnClickListener(this);
        rl_cooperation.setOnClickListener(this);
    }

    private void initToolBar() {
        //填充toolbar的menu
        toolbar.inflateMenu(R.menu.menu_toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {

                    case R.id.menu_seacher:
                        ToastUtil.showShortToast(context, "这是toolbar的menu：搜索");
                        break;
                }
                return false;
            }
        });

        //点击导航栏弹出侧拉页
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drawerLayout.openDrawer(Gravity.LEFT);
            }
        });
    }

    @Override
    public void onClick(View view) {
        transaction = fm.beginTransaction();
        switch (view.getId()) {
            case R.id.rl_home:
                setAllFalse();
                rl_home.setSelected(true);
                hideAllFragment();
                if (homeFragment == null) {
                    homeFragment = new HomeFragment();
                    transaction.add(R.id.framelayout_content, homeFragment,
                            "HomeFragment");
                }
                transaction.show(homeFragment);
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_gift:
                setAllFalse();
                rl_gift.setSelected(true);
                hideAllFragment();
                if (giftFragment == null) {
                    giftFragment = new GiftFragment();
                    transaction.add(R.id.framelayout_content, giftFragment,
                            "GiftFragment");
                }
                transaction.show(giftFragment);
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_share:
                setAllFalse();
                rl_share.setSelected(true);
                hideAllFragment();
                if (shareFragment == null) {
                    shareFragment = new ShareFragment();
                    transaction.add(R.id.framelayout_content, shareFragment,
                            "ShareFragment");
                }
                transaction.show(shareFragment);
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_subjects://我的订阅
                setAllFalse();
                rl_my_subjects.setSelected(true);
                hideAllFragment();
                if (mySubsFragment == null) {
                    mySubsFragment = new SubsriptionFragment();
                    transaction.add(R.id.framelayout_content, mySubsFragment,
                            "ShareFragment");
                }
                transaction.show(mySubsFragment);
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_favorites://我的收藏
                setAllFalse();
                rl_my_collect.setSelected(true);
                hideAllFragment();
                if (myCollectFragment == null) {
                    myCollectFragment = new CollectFragment();
                    transaction.add(R.id.framelayout_content, myCollectFragment,
                            "CollectFragment");
                }
                transaction.show(myCollectFragment);
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_create_subject://立即创建主题
                setAllFalse();
                rl_create_subject.setSelected(true);
                hideAllFragment();
                if (createSubFragment == null) {
                    createSubFragment = new CreateSubjectFragment();
                    transaction.add(R.id.framelayout_content, createSubFragment,
                            "CollectFragment");
                }
                transaction.show(createSubFragment);
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_my_subjects://我创建的主题
                setAllFalse();
                rl_my_create_subjects.setSelected(true);
                hideAllFragment();
                if (myCreateSubFragment == null) {
                    myCreateSubFragment = new MySubjectFragment();
                    transaction.add(R.id.framelayout_content, myCreateSubFragment,
                            "MySubjectFragment");
                }
                transaction.show(myCreateSubFragment);
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_feedback://意见反馈
                setAllFalse();
                rl_feedback.setSelected(true);
                hideAllFragment();
                if (feedBackFragment == null) {
                    feedBackFragment = new FeedBackFragment();
                    transaction.add(R.id.framelayout_content, feedBackFragment,
                            "FeedBackFragment");
                }
                transaction.show(feedBackFragment);
                drawerLayout.closeDrawers();
                break;
            case R.id.rl_cooperation://合作申请
                setAllFalse();
                rl_cooperation.setSelected(true);
                hideAllFragment();
                if (cooperationFragment == null) {
                    cooperationFragment = new CooperationFragment();
                    transaction.add(R.id.framelayout_content, cooperationFragment,
                            "CooperationFragment");
                }
                transaction.show(cooperationFragment);
                drawerLayout.closeDrawers();
                break;
            default:
                break;
        }
        transaction.commit();
    }

    private void hideAllFragment() {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (giftFragment != null) {
            transaction.hide(giftFragment);
        }
        if (shareFragment != null) {
            transaction.hide(shareFragment);
        }
        if (mySubsFragment != null) {
            transaction.hide(mySubsFragment);
        }
        if (myCollectFragment != null) {
            transaction.hide(myCollectFragment);
        }
        if (createSubFragment != null) {
            transaction.hide(createSubFragment);
        }
        if (myCreateSubFragment != null) {
            transaction.hide(myCreateSubFragment);
        }
        if (feedBackFragment != null) {
            transaction.hide(feedBackFragment);
        }
        if (cooperationFragment != null) {
            transaction.hide(cooperationFragment);
        }
    }

    private void setAllFalse() {
        rl_home.setSelected(false);
        rl_gift.setSelected(false);
        rl_share.setSelected(false);
        rl_my_subjects.setSelected(false);
        rl_my_collect.setSelected(false);
        rl_create_subject.setSelected(false);
        rl_my_create_subjects.setSelected(false);
        rl_feedback.setSelected(false);
        rl_cooperation.setSelected(false);
    }
}
