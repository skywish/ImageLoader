package com.example.skywish.imageloader;

import android.app.Activity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.GridView;

import com.example.skywish.imageloader.Resouce.Images;
import com.example.skywish.imageloader.adapter.PhotoWallAdapter;


public class MainActivity extends Activity {

    private GridView mPhotoWall;
    private PhotoWallAdapter mAdpater;
    private int mImageThumbSize;
    private int mImageThumbSpacing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mImageThumbSize = getResources().getDimensionPixelSize(R.dimen.image_thumbnail_size);
        mImageThumbSpacing = getResources().getDimensionPixelSize(
                R.dimen.image_thumbnail_spacing);
        mPhotoWall = (GridView) findViewById(R.id.photo_wall);
        mAdpater = new PhotoWallAdapter(this, 0, Images.imageThumbUrls, mPhotoWall);
        mPhotoWall.setAdapter(mAdpater);

        //getViewTreeObserver().addOnGlobalLayoutListener()来获得宽度或者高度。
        mPhotoWall.getViewTreeObserver().addOnGlobalLayoutListener(
                //interface ViewTreeObserver.OnGlobalLayoutListener
                //当在一个视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变时
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        final int numColumns = (int) Math.floor(mPhotoWall.getWidth() /
                                (mImageThumbSize + mImageThumbSpacing));
                        if (numColumns > 0) {
                            int columnWidth = (mPhotoWall.getWidth() / numColumns) -
                                    mImageThumbSpacing;
                            int columnHeight = (int) Math.floor(columnWidth * 1.5);
                            mAdpater.setItemHeight(columnHeight);
                            mPhotoWall.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        }
                    }
                });
    }

    @Override
    protected void onPause() {
        super.onPause();
        //将缓存记录同步到journal文件中。
        mAdpater.flushCache();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出程序时结束所有的下载任务
        mAdpater.cancelAllTask();
    }
}
