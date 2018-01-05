package apollozhu.github.io.azdmv.activity

import android.content.Context
import android.database.DataSetObserver
import android.graphics.Color
import android.support.v4.content.res.ResourcesCompat
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListAdapter
import android.widget.TextView
import apollozhu.github.io.azdmv.R
import apollozhu.github.io.azdmv.model.Manual
import apollozhu.github.io.azdmv.model.Section
import apollozhu.github.io.azdmv.model.SubSection

class MainAdapter(private val ctx: Context) : ExpandableListAdapter {
    override fun isEmpty(): Boolean {
        return Manual.needsContext
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun areAllItemsEnabled(): Boolean {
        return true
    }

    override fun isChildSelectable(p0: Int, p1: Int): Boolean {
        return true
    }

    // Group

    override fun getGroupCount(): Int {
        return Manual.sections.size
    }

    override fun getGroupId(p0: Int): Long {
        return p0.toLong()
    }

    override fun getGroup(p0: Int): Any {
        return Manual.sections[p0]
    }

    override fun getGroupView(p0: Int, p1: Boolean, p2: View?, p3: ViewGroup?): View {
        val label = p2 as? TextView ?: TextView(ctx)
        label.textSize = 18.0f
        val section = getGroup(p0) as Section
        label.typeface = ResourcesCompat.getFont(ctx, R.font.icomoon)
        label.text = "${section.icon} ${section.sectionTitle}"
        label.setBackgroundResource(R.color.positive)
        label.setTextColor(Color.WHITE)
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
        return Manual.subsections[p0].size
    }

    override fun getChildId(p0: Int, p1: Int): Long {
        return p1.toLong()
    }

    override fun getChild(p0: Int, p1: Int): Any {
        return Manual.subsections[p0][p1]
    }

    override fun getChildView(p0: Int, p1: Int, p2: Boolean, p3: View?, p4: ViewGroup?): View {
        val label = p3 as? TextView ?: TextView(ctx)
        label.text = (getChild(p0, p1) as SubSection).title
        return label
    }

    override fun getCombinedChildId(p0: Long, p1: Long): Long {
        return getCombinedGroupId(p0) + p1 + 1
    }

    private val dataSetObserverList = mutableListOf<DataSetObserver?>()

    override fun registerDataSetObserver(p0: DataSetObserver?) {
        dataSetObserverList.add(p0)
    }

    override fun unregisterDataSetObserver(p0: DataSetObserver?) {
        dataSetObserverList.remove(p0)
    }
}
