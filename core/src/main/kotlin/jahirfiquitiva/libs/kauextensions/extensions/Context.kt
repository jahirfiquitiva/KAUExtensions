/*
 * Copyright (c) 2017. Jahir Fiquitiva
 *
 * Licensed under the CreativeCommons Attribution-ShareAlike
 * 4.0 International License. You may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *    http://creativecommons.org/licenses/by-sa/4.0/legalcode
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jahirfiquitiva.libs.kauextensions.extensions

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.drawable.Drawable
import android.os.Looper
import android.support.annotation.ArrayRes
import android.support.annotation.AttrRes
import android.support.annotation.BoolRes
import android.support.annotation.ColorInt
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.annotation.DrawableRes
import android.support.annotation.IntegerRes
import android.support.annotation.LayoutRes
import android.support.annotation.StringRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import ca.allanwang.kau.utils.resolveBoolean
import jahirfiquitiva.libs.kauextensions.R
import jahirfiquitiva.libs.kauextensions.utils.Konfigurations

val Context.isFirstRunEver: Boolean
    get() {
        try {
            val firstInstallTime = packageManager.getPackageInfo(packageName, 0).firstInstallTime
            val lastUpdateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime
            return firstInstallTime == lastUpdateTime
        } catch (ignored: Exception) {
        }
        return true
    }

val Context.justUpdated: Boolean
    get() {
        try {
            val firstInstallTime = packageManager.getPackageInfo(packageName, 0).firstInstallTime
            val lastUpdateTime = packageManager.getPackageInfo(packageName, 0).lastUpdateTime
            return firstInstallTime != lastUpdateTime
        } catch (ignored: Exception) {
        }
        return false
    }

val Context.usesLightTheme
    get() = !usesDarkTheme

val Context.usesDarkTheme
    get() = resolveBoolean(R.attr.isDark)

fun Context.getStringFromRes(@StringRes stringRes: Int, fallback: String): String =
        if (stringRes > 0) getString(stringRes) else fallback

fun Context.getStringArray(@ArrayRes arrayRes: Int): Array<String> =
        resources.getStringArray(arrayRes)

fun Context.getColorFromRes(@ColorRes colorRes: Int) = ContextCompat.getColor(this, colorRes)

fun Context.getBoolean(@BoolRes bool: Int) = resources.getBoolean(bool)

fun Context.getInteger(@IntegerRes id: Int): Int = resources.getInteger(id)

fun Context.getDimension(@DimenRes id: Int): Float = resources.getDimension(id)

fun Context.getDimensionPixelSize(@DimenRes id: Int): Int = resources.getDimensionPixelSize(id)

fun Context.getDrawable(@DrawableRes id: Int, fallback: Drawable? = null): Drawable? =
        if (id > 0) ContextCompat.getDrawable(this, id) else fallback

@ColorInt
fun Context.extractColor(attribute: IntArray): Int {
    val typedValue = TypedValue()
    val a = obtainStyledAttributes(typedValue.data, attribute)
    val color = a.getColor(0, 0)
    a.recycle()
    return color
}

fun Context.extractDrawable(@AttrRes drawableAttributeId: Int): Drawable {
    val typedValue = TypedValue()
    val a = obtainStyledAttributes(typedValue.data, intArrayOf(drawableAttributeId))
    val drawable = a.getDrawable(0)
    a.recycle()
    return drawable
}

fun Context.showToast(@StringRes textRes: Int, duration: Int = Toast.LENGTH_SHORT) {
    if (isOnMainThread()) {
        Toast.makeText(this, textRes, duration).show()
    } else {
        (this as? Activity)?.runOnUiThread { Toast.makeText(this, textRes, duration).show() }
    }
}

fun Context.showToast(text: String, duration: Int = Toast.LENGTH_SHORT) {
    if (isOnMainThread()) {
        Toast.makeText(this, text, duration).show()
    } else {
        (this as? Activity)?.runOnUiThread { Toast.makeText(this, text, duration).show() }
    }
}

inline fun <reified T : View> Context.inflate(
        @LayoutRes layout: Int,
        root: ViewGroup,
        attachToRoot: Boolean = false
                                             ): T =
        LayoutInflater.from(this).inflate(layout, root, attachToRoot) as T

fun Context.getAppName(): String = getStringFromRes(R.string.app_name, "KAU Extensions")

fun Context.getLogTag(): String = getAppName()

fun Context.getAppVersion(): String {
    return try {
        packageManager.getPackageInfo(packageName, 0).versionName
    } catch (e: Exception) {
        "Unknown"
    }
}

fun Context.isOnMainThread() = Looper.myLooper() == Looper.getMainLooper()

fun Context.getSharedPrefs(name: String) = getSharedPreferences(name, Context.MODE_PRIVATE)

fun Context.hasReadStoragePermission() =
        ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

fun Context.hasWriteStoragePermission() =
        ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

val Context.konfigs: Konfigurations
    get() = Konfigurations.newInstance("kau_extensions", this)

fun Context.runInAThread(item: () -> Unit) {
    Thread(Runnable(item)).start()
}

val Context.isInHorizontalMode: Boolean
    get() = currentRotation == 90 || currentRotation == 270

val Context.isInPortraitMode: Boolean
    get() = currentRotation == 0 || currentRotation == 180

val Context.currentRotation: Int
    get() {
        val display = (getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
        return display.rotation * 90
    }