package com.volokh.danylo.videolist.visibility_demo.adapter.items;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;

import com.example.gsyvideoplayer.holder.RecyclerItemNormalHolder;
import com.volokh.danylo.visibility_utils.items.ListItem;

/**
 * Created by danylo.volokh on 06.01.2016.
 */
public class VisibilityItem implements ListItem {

    private static final boolean SHOW_LOGS = true;
    private static final String TAG = VisibilityItem.class.getSimpleName();

    private final Rect mCurrentViewRect = new Rect();
    private final ItemCallback mItemCallback;
    private RecyclerItemNormalHolder holder;

    public void onBindViewHolder(RecyclerItemNormalHolder holder) {
        this.holder = holder;
    }


    public interface ItemCallback {
        void makeToast(String text);
        void onActiveViewChangedActive(View newActiveView, int newActiveViewPosition);
    }

    public VisibilityItem(ItemCallback callback) {
        mItemCallback = callback;
    }

    @Override
    public int getVisibilityPercents(View view) {
        if(SHOW_LOGS) Log.i(TAG, ">> getVisibilityPercents view " + view);

        int percents = 100;

        view.getLocalVisibleRect(mCurrentViewRect);
        if(SHOW_LOGS) Log.i(TAG, "getVisibilityPercents mCurrentViewRect top " + mCurrentViewRect.top + ", left " + mCurrentViewRect.left + ", bottom " + mCurrentViewRect.bottom + ", right " + mCurrentViewRect.right);

        int height = view.getHeight();
        if(SHOW_LOGS) Log.i(TAG, "getVisibilityPercents height " + height);

        if(viewIsPartiallyHiddenTop()){
            // view is partially hidden behind the top edge
            percents = (height - mCurrentViewRect.top) * 100 / height;
        } else if(viewIsPartiallyHiddenBottom(height)){
            percents = mCurrentViewRect.bottom * 100 / height;
        }

        if(SHOW_LOGS) Log.i(TAG, "<< getVisibilityPercents, percents " + percents);

        return percents;
    }

    @Override
    public void setActive(View newActiveView, int newActiveViewPosition) {
        if(SHOW_LOGS) Log.i(TAG, "setActive, newActiveViewPosition " + newActiveViewPosition);

//        final View whiteView = newActiveView.findViewById(R.id.white_view);
//        // animate alpha to show that active view has changed
//        whiteView
//                .animate()
//                .alpha(1f)
//                .setDuration(500)
//                .withEndAction(new Runnable() {
//            @Override
//            public void run() {
//                // animate alpha back
//                whiteView
//                        .animate()
//                        .alpha(0f)
//                        .setDuration(500)
//                        .start();
//            }
//        }).start();

        holder.autoPlay();

        mItemCallback.makeToast("New Active View at position " + newActiveViewPosition);
        mItemCallback.onActiveViewChangedActive(newActiveView, newActiveViewPosition);
    }


    private boolean viewIsPartiallyHiddenBottom(int height) {
        return mCurrentViewRect.bottom > 0 && mCurrentViewRect.bottom < height;
    }

    private boolean viewIsPartiallyHiddenTop() {
        return mCurrentViewRect.top > 0;
    }


    @Override
    public void deactivate(View currentView, int position) {
        mItemCallback.makeToast("Deactivate View");
    }
}
