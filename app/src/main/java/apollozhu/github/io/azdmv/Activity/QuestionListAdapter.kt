package apollozhu.github.io.azdmv.Activity

import android.content.Context
import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup
import android.widget.ListAdapter
import android.widget.TextView
import apollozhu.github.io.azdmv.Model.Quiz
import apollozhu.github.io.azdmv.Model.SubSection

class QuestionListAdapter(val ctx: Context) : ListAdapter {

    val ids = SubSection.current!!.quizIDs

    override fun isEmpty(): Boolean {
        return ids.isEmpty()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }

    override fun isEnabled(p0: Int): Boolean {
        return true
    }

    override fun getCount(): Int {
        return ids.size
    }

    override fun getItem(p0: Int): Any {
        return SubSection.current!!.quizzes[p0]
    }

    override fun getItemId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemViewType(p0: Int): Int {
        return 0
    }

    override fun getView(p0: Int, p1: View?, p2: ViewGroup?): View {
        val label = if (p1 is TextView) p1 else TextView(ctx)
        label.text = "#${ids[p0]}: ${(getItem(p0) as Quiz).question}"
        return label
    }

    private val dataSetObserverList = mutableListOf<DataSetObserver?>()

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        dataSetObserverList.add(p0)
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        dataSetObserverList.remove(p0)
    }
}