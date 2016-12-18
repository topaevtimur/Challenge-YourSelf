package v.challengeyourself.utils;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by penguinni on 18.12.16.
 */

public class RecyclerDividersDecorator extends RecyclerView.ItemDecoration {
    private final Paint paint;

    public RecyclerDividersDecorator(int color) {
        paint = new Paint();
        paint.setColor(color);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
    }

    @Override
    public void getItemOffsets(Rect outRect,
                               View view,
                               RecyclerView recyclerView,
                               RecyclerView.State state) {
        int position = recyclerView.getChildLayoutPosition(view);
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        int itemCount = adapter == null ? 0 : adapter.getItemCount();
        int bottomInset = position >= 0 && position < itemCount - 1
                ? 1
                : 0;
        outRect.set(0, 0, 0, bottomInset);
    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.State state) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        int itemCount = adapter == null ? 0 : adapter.getItemCount();

        int width = recyclerView.getWidth();

        for (int i = 0; i < recyclerView.getChildCount(); i++) {
            View child = recyclerView.getChildAt(i);
            int position = recyclerView.getChildLayoutPosition(child);
            if (position >= 0 && position < itemCount - 1) {
                float y = child.getBottom() + 1;
                c.drawLine(0, y, width, y, paint);
            }
        }
    }
}