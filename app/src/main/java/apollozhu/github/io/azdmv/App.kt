package apollozhu.github.io.azdmv

import android.app.Application
import com.pgyersdk.crash.PgyCrashManager

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        PgyCrashManager.register(this)
    }
}
