package com.hr.toy.wiki;

import android.content.Intent;

import com.hr.toy.BaseExampleFragment;

/**
 * 收集一些好的网上的文章，组织成wiki
 */
public class WikiFragment extends BaseExampleFragment {

    @Override
    protected String[] initData() {
        return new String[]{"EventLog日志分析", "dumpsys命令用法", "RxJava操作符：defer", "ReactiveX文档中文翻译", "adb logcat查看日志", "Android内存分析命令", " Android中View自定义XML属性详解以及R.attr与R.styleable的区别", "Android开发之Theme、Style探索及源码浅析", "Android5 Zygote 与 SystemServer 启动流程分析"};
    }

    @Override
    protected void handleClick(int position) {
        Intent intent = null;
        switch (position) {
            case 0:
                intent = new Intent();
                //EventLog 日志分析
                intent.putExtra(ArticleActivity.EXTRA_URL, "http://gityuan.com/2016/05/15/event-log/");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
            case 1:
                intent = new Intent();
                //dumpsys命令用法
                intent.putExtra(ArticleActivity.EXTRA_URL, "http://gityuan.com/2016/05/14/dumpsys-command/");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
            case 2:
                intent = new Intent();
                //RxJava操作符号：defer
                intent.putExtra(ArticleActivity.EXTRA_URL, "http://www.jianshu.com/p/c83996149f5b");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
            case 3:
                intent = new Intent();
                //ReactiveX文档中文翻译
                intent.putExtra(ArticleActivity.EXTRA_URL, "https://www.gitbook.com/book/mcxiaoke/rxdocs/details");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
            case 4:
                intent = new Intent();
                //adb logcat 查看日志
                intent.putExtra(ArticleActivity.EXTRA_URL, "http://blog.csdn.net/xyz_lmn/article/details/7004710");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
            case 5:
                intent = new Intent();
                //Android内存分析命令
                intent.putExtra(ArticleActivity.EXTRA_URL, "http://gityuan.com/2016/01/02/memory-analysis-command/");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
            case 6:
                intent = new Intent();
                //Android中View自定义XML属性详解以及R.attr与R.styleable的区别
                intent.putExtra(ArticleActivity.EXTRA_URL, "http://blog.csdn.net/iispring/article/details/50708044");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
            case 7:
                intent = new Intent();
                //Android开发之Theme、Style探索及源码浅析
                intent.putExtra(ArticleActivity.EXTRA_URL, "http://blog.csdn.net/yanbober/article/details/51015630");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
            case 8:
                intent = new Intent();
                //Android5 Zygote 与 SystemServer 启动流程分析
                intent.putExtra(ArticleActivity.EXTRA_URL, "http://blog.csdn.net/kesalin/article/details/50735920");
                intent.setClass(getActivity(), ArticleActivity.class);
                break;
        }
        if (intent != null) {
            getActivity().startActivity(intent);
        }
    }
}
