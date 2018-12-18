package test.com.recyclerviewitemresize.anim;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import test.com.recyclerviewitemresize.MainAdapter;

public class ExpandableViewHoldersUtil {

    public static void openH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        MainAdapter.MainViewHolder mholder = (MainAdapter.MainViewHolder) holder;
        if (animate) {
            expandView.setVisibility(View.VISIBLE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    final ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(expandView, View.ALPHA, 1).setDuration(400);
                    alphaAnimator.addListener(new ViewHolderAnimator.ViewHolderAnimatorListener(holder));
                    alphaAnimator.start();
                }
            });
            animator.start();
            mholder.openAnim();
        }
        else {
            expandView.setVisibility(View.VISIBLE);
            expandView.setAlpha(1);
           mholder.openAnimQuike();
        }

    }

    public static void closeH(final RecyclerView.ViewHolder holder, final View expandView, final boolean animate) {
        MainAdapter.MainViewHolder mholder = (MainAdapter.MainViewHolder) holder;
        if (animate) {
            expandView.setVisibility(View.GONE);
            final Animator animator = ViewHolderAnimator.ofItemViewHeight(holder).setDuration(400);
            expandView.setVisibility(View.VISIBLE);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override public void onAnimationEnd(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }
                @Override public void onAnimationCancel(Animator animation) {
                    expandView.setVisibility(View.GONE);
                    expandView.setAlpha(0);
                }
            });
            animator.start();
            mholder.closeAnim();
        }
        else {
            expandView.setVisibility(View.GONE);
            expandView.setAlpha(0);
            mholder.closeAnimQuike();
        }

    }

    public static interface Expandable {
        public View getExpandView();
    }

    public static class KeepOneH<VH extends RecyclerView.ViewHolder & Expandable> {
        private int _opened = -1;

        public void bind(VH holder, int pos) {
            if (pos == _opened) {
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), false);

            }else {
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), false);

            }
        }

        @SuppressWarnings("unchecked")
        public void toggle(VH holder) {
            if (_opened == holder.getPosition()) {
                _opened = -1;
                ExpandableViewHoldersUtil.closeH(holder, holder.getExpandView(), true);
            } else {
                int previous = _opened;
                _opened = holder.getPosition();
                ExpandableViewHoldersUtil.openH(holder, holder.getExpandView(), true);
                final VH oldHolder = (VH) ((RecyclerView) holder.itemView.getParent()).findViewHolderForPosition(previous);
                if (oldHolder != null)
                    ExpandableViewHoldersUtil.closeH(oldHolder, oldHolder.getExpandView(), true);
            }
        }
    }

}
