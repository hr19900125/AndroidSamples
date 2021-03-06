package com.hr.toy.widget;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public class QRBarDecoration extends RecyclerView.ItemDecoration {

    private int columnCount;
    private int qrBarHeight;

    public QRBarDecoration(int columnCount, int qrBarHeight) {
        this.columnCount = columnCount;
        this.qrBarHeight = qrBarHeight;
    }

    @Override
    public void getItemOffsets (Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

        // Sets the "inset" of the top views in the grid
        if (parent.getChildPosition(view) < columnCount) {
            outRect.set(0,qrBarHeight,0,0);
        } else {
            outRect.set(0,0,0,0);
        }

    }

}