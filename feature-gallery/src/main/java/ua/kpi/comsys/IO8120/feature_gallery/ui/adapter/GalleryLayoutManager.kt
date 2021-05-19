package ua.kpi.comsys.IO8120.feature_gallery.ui.adapter

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.max
import kotlin.math.min

internal class GalleryLayoutManager : RecyclerView.LayoutManager() {
    private val cell: Int
        get() = width / 5

    override fun canScrollVertically(): Boolean = true
    
    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams = RecyclerView
        .LayoutParams(
            RecyclerView.LayoutParams.MATCH_PARENT,
            RecyclerView.LayoutParams.WRAP_CONTENT
        )

    override fun onLayoutChildren(recycler: RecyclerView.Recycler, state: RecyclerView.State) {
        detachAndScrapAttachedViews(recycler)
        if (state.itemCount <= 0) return
        fillDown(recycler)
    }

    private fun fillDown(recycler: RecyclerView.Recycler) {
        val rect = Rect(0, -cell * 4, cell * 5, 0)
        for (i in 0 until itemCount) {
            val view = recycler.getViewForPosition(i)
            addView(view)
            if (i % 6 == 0) {
                rect.offset(0, cell * 4)
                rect.bottom = rect.top
            }
            val k = i % 6
            locateView(rect, view, k)
        }
    }

    private fun measureSmallChild(child: View) {
        measureChildWithMargins(child, 0, 0)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(cell, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(cell, View.MeasureSpec.EXACTLY)
        measureChildWithDecorationsAndMargin(child, widthSpec, heightSpec)
    }

    private fun measureMediumChild(view: View) {
        measureChildWithMargins(view, 0, 0)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(cell * 2, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(cell * 2, View.MeasureSpec.EXACTLY)
        measureChildWithDecorationsAndMargin(view, widthSpec, heightSpec)
    }

    private fun measureBigChild(view: View) {
        measureChildWithMargins(view, 0, 0)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(cell * 3, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(cell * 3, View.MeasureSpec.EXACTLY)
        measureChildWithDecorationsAndMargin(view, widthSpec, heightSpec)
    }

    private fun measureChildWithDecorationsAndMargin(child: View, widthSpec: Int, heightSpec: Int) {
        val decorRect = Rect()
        calculateItemDecorationsForChild(child, decorRect)
        val lp = child.layoutParams as RecyclerView.LayoutParams
        val width = updateSpecWithExtra(
            widthSpec,
            lp.leftMargin + decorRect.left,
            lp.rightMargin + decorRect.right
        )
        val height = updateSpecWithExtra(
            heightSpec,
            lp.topMargin + decorRect.top,
            lp.bottomMargin + decorRect.bottom
        )
        child.measure(width, height)
    }

    private fun updateSpecWithExtra(spec: Int, startInset: Int, endInset: Int): Int {
        if (startInset == 0 && endInset == 0) return spec
        val mode = View.MeasureSpec.getMode(spec)
        return if (mode == View.MeasureSpec.AT_MOST || mode == View.MeasureSpec.EXACTLY) {
            View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.getSize(spec) - startInset - endInset,
                mode
            )
        } else {
            spec
        }
    }

    private fun locateView(r: Rect, v: View, i: Int) = when (i % 10) {
        0 -> {
            measureBigChild(v)
            layoutDecorated(v, r.left, r.top, r.left + cell * 3, r.top + cell * 3)
        }
        1 -> {
            measureMediumChild(v)
            layoutDecorated(v, r.left + cell * 3, r.top, r.left + cell * 5, r.top + cell * 2)
        }
        2 -> {
            measureMediumChild(v)
            layoutDecorated(v, r.left + cell * 3, r.top + cell * 2, r.left + cell * 5, r.top + cell * 4)
        }
        3 -> {
            measureSmallChild(v)
            layoutDecorated(v, r.left, r.top + cell * 3, r.right + cell, r.top + cell * 4)
        }
        4 -> {
            measureSmallChild(v)
            layoutDecorated(v, r.left + cell, r.top + cell * 3, r.right + cell, r.top + cell * 4)
        }
        5 -> {
            measureSmallChild(v)
            layoutDecorated(v, r.left + cell * 2, r.top + cell * 3, r.left + cell * 3, r.bottom + cell * 4)
        }
        else -> error("Your math is really wrong, friend")
    }

    override fun scrollVerticallyBy(
        dy: Int,
        recycler: RecyclerView.Recycler?,
        state: RecyclerView.State?
    ): Int {
        val delta = scrollVerticallyInternal(dy)
        offsetChildrenVertical(-delta)
        return delta
    }

    private fun scrollVerticallyInternal(dy: Int): Int {
        if (childCount == 0) return 0

        val topView = getChildAt(0)!!
        val bottomView = getChildAt(childCount - 1)!!

        val viewSpan = getDecoratedBottom(bottomView) - getDecoratedTop(topView)
        if (viewSpan <= height) return 0

        return if (dy < 0) { // scroll to top
            val firstViewAdapterPos = getPosition(topView)
            if (firstViewAdapterPos > 0) dy else max(getDecoratedTop(topView), dy)
        } else { // scroll to bottom
            val lastViewAdapterPos = getPosition(bottomView)
            if (lastViewAdapterPos < itemCount - 1) {
                dy
            } else {
                val pos = getPosition(bottomView) % 6
                val h = getDecoratedBottom(bottomView) - height + when (pos) {
                    1 -> cell // if has bigger cell before our last view, should be able to scroll one more cell
                    else -> 0
                }
                min(h, dy)
            }
        }
    }
}
