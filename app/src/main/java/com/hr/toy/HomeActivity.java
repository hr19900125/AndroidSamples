package com.hr.toy;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hr.toy.router.Subject;
import com.hr.toy.utils.FileUtils;

import java.io.IOException;
import java.lang.reflect.Type;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

public class HomeActivity extends AppCompatActivity implements BaseFragment.OnSelectedFragmentDelegate {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private Toolbar mToolbar;
    private NavigationView mNavigationView;
    private BaseFragment mSelectedFragment;
    private Subjects mSubjects;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mSubjects = loadSubjects();
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mNavigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupNavigationView(mNavigationView, mSubjects);
        setupDrawerContent(mNavigationView);
        setUpProfileImage();
    }

    private Subjects loadSubjects() {
        try {
            Type type = new TypeToken<Subjects>() {
            }.getType();
            Subjects result = new Gson().fromJson(FileUtils.readFileFromAssert("page_config.json"), type);
            if (result != null) {
                for (Subject subject : result) {
                    subject.setId(View.generateViewId());
                    result.putSubject(subject.getId(), subject);
                }
            }
            Log.e("Home", new Gson().toJson(result));
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Subjects();
    }

    private void setupNavigationView(NavigationView navigationView, Subjects subjects) {
        for(Subject subject : subjects) {
            navigationView.getMenu().add(0, subject.getId(), subject.getId(), subject.getName()).setIcon(R.drawable.ic_favorite);
        }
    }

    private void switchToFragment(Subject subject) {
        try {
            Class fragmentClass = Class.forName(subject.getFragmentClass());
            Fragment fragment = (Fragment) fragmentClass.newInstance();
            Bundle bundle = new Bundle();
            bundle.putParcelable(Subject.SUBJECT, subject);
            fragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, fragment).commit();
            mToolbar.setTitle(subject.getName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }

//    private void switchToHome() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new HomeFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_home);
//    }
//
//    private void switchToCodeSnippet() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new CodeSnippetExampleFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_codesnippet);
//    }
//
//    /**
//     *
//     */
//    private void switchToExample() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new ExampleFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_example);
//    }
//
//    private void switchToDesignPattern() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new DesignExampleFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_design_pattern);
//    }
//
//    private void switchToFramework() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AppFrameworkExampleFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_framework);
//    }
//
//    private void switchToJavaConcurrent() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new JavaConcurrentExampleFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_concurrent);
//    }
//
//    private void switchToRxJavaExample() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new RxJavaExampleFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_rxjava);
//    }
//
//    private void switchToRealm() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new RealmExampleFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_realm);
//    }
//
//    private void switchToGreenDao() {
////        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new GreenDaoExampleFragment()).commit();
////        mToolbar.setTitle(R.string.navigation_greendao);
//    }
//
//    private void switchToWebView() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new WebViewFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_webview);
//    }
//
//    private void switchToWiki() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new WikiFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_wiki);
//    }
//
//    private void switchToWidget() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new WidgetFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_widget);
//    }
//
//    private void switchToAnimation() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AnimationExampleFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_animation);
//    }
//
//    private void switchToBlog() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new BlogFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_blog);
//    }
//
//    private void switchToAbout() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frame_content, new AboutFragment()).commit();
//        mToolbar.setTitle(R.string.navigation_about);
//    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                Subject subject = mSubjects.getSubject(item.getItemId());
                if(subject == null) return true;
                switchToFragment(subject);
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                return true;
            }
        });
    }

    private void setUpProfileImage() {
        View headerView = mNavigationView.inflateHeaderView(R.layout.navigation_header);
        View profileView = headerView.findViewById(R.id.profile_image);
        profileView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                switchToBlog();
                mDrawerLayout.closeDrawers();
                mNavigationView.getMenu().getItem(0).setChecked(true);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * When using the ActionBarDrawerToggle, you must call it during
     * onPostCreate() and onConfigurationChanged()...
     */
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Pass any configuration change to the drawer toggls
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void setupSelectedFragment(BaseFragment fragment) {
        mSelectedFragment = fragment;
    }

    private long exitTime = 0;

    public void doExitApp() {
        long time = System.currentTimeMillis();
        if ((time - exitTime) > 2000) {
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            exitTime = time;
        } else {
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        if (mSelectedFragment == null || !mSelectedFragment.onBackPressed()) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
            } else {
                doExitApp();
            }
        }
    }
}
