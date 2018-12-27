package mylittlechat.banancheg.com.mylittlechat

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

class MyItemDecorator(var space: Int): RecyclerView.ItemDecoration() {
    fun MyItemDecorator(space: Int)
    {
        this.space = space;
    }

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        outRect.bottom = space;

    }
}