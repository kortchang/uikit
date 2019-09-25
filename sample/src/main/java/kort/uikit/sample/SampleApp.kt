package kort.uikit.sample

import android.app.Application
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree


/**
 * Created by Kort on 2019/9/16.
 */
class SampleApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(mainModule)
        }
        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }
    }
}