package mylittlechat.banancheg.com.mylittlechat

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class MyItemDecorator(var space: Int): RecyclerView.ItemDecoration() {
    fun MyItemDecorator(space: Int)
    {
        this.space = space;
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = space;

    }
}