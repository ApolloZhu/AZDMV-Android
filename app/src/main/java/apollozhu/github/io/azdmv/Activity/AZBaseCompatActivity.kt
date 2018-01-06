package apollozhu.github.io.azdmv.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import apollozhu.github.io.azdmv.model.Manual
import apollozhu.github.io.azdmv.model.QuizSet
import com.pgyersdk.activity.FeedbackActivity
import com.pgyersdk.feedback.PgyFeedbackShakeManager
import com.pgyersdk.views.PgyerDialog

open class AZBaseCompatActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        assignContext()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        assignContext()
    }

    private fun assignContext() {
        if (Manual.needsContext) Manual.setContext { this }
        if (QuizSet.needsContext) QuizSet.setContext { this }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (PackageManager.PERMISSION_GRANTED !=
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 0)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 自定义摇一摇的灵敏度，默认为950，数值越小灵敏度越高。
        // PgyFeedbackShakeManager.setShakingThreshold(1000)

        // 以对话框的形式弹出，对话框只支持竖屏
        PgyFeedbackShakeManager.register(this)

        PgyerDialog.setDialogTitleBackgroundColor("#008080")
        PgyerDialog.setDialogTitleTextColor("#FFFFFF")
        // 设置顶部导航栏和底部bar的颜色
        FeedbackActivity.setBarBackgroundColor("#008080")
        // 设置顶部按钮和底部按钮按下时的反馈色
        FeedbackActivity.setBarButtonPressedColor("#339999")
        // 设置颜色选择器的背景色
        FeedbackActivity.setColorPickerBackgroundColor("#F79700")

        // 以Activity的形式打开，这种情况下必须在AndroidManifest.xml配置FeedbackActivity
        // 打开沉浸式,默认为false
        // FeedbackActivity.setBarImmersive(true);
        //PgyFeedbackShakeManager.register(this, true); 相当于使用Dialog的方式；
        PgyFeedbackShakeManager.register(this, false)
    }

    override fun onPause() {
        super.onPause()
        PgyFeedbackShakeManager.unregister()
    }
}
