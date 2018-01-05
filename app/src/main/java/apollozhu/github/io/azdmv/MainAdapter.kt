package apollozhu.github.io.azdmv

import android.content.Context
import android.database.DataSetObserver
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.TextView
import apollozhu.github.io.azdmv.Model.Manual
import apollozhu.github.io.azdmv.Model.Section
import apollozhu.github.io.azdmv.Model.SubSection

class MainAdapter(val ctx: Context, val manual: Manual) : ExpandableListAdapter {
    private val dataSetObserverList = mutableListOf<DataSetObserver?>()
    override fun isEmpty(): Boolean {
        return false
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun areAllItemsEnabled(): Boolean {
        return false
    }

    // Group

    override fun getGroupCount(): Int {
        return manual.sections.size
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getGroup(p0: Int): Any {
        return manual.sections[p0]
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val label = if (p2 is TextView) p2 else TextView(ctx)
        label.text = (getGroup(p0) as Section).sectionTitle
        return label
    }

    override fun onGroupCollapsed(p0: Int) {
        dataSetObserverList.forEach { it?.onChanged() }
    }

    override fun onGroupExpanded(p0: Int) {
        dataSetObserverList.forEach { it?.onChanged() }
    }

    override fun getCombinedGroupId(p0: Long): Long {
        return p0 * 100
    }

    // Child

    override fun getChildrenCount(p0: Int): Int {
        return manual.subsections[p0].size
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return manual.subsections[p0][p1]
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val label = if (p3 is TextView) p3 else TextView(ctx)
        label.text = (getChild(p0, p1) as SubSection).title
        return label
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return manual.subsections[p0][p1].hasQuiz
    }

    override fun getCombinedChildId(p0: Long, p1: Long): Long {
        return getCombinedGroupId(p0) + p1 + 1
    }

    // ??

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        dataSetObserverList.add(p0)
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        dataSetObserverList.remove(p0)
    }
}