/*
 * Copyright (c) 2019. Jahir Fiquitiva
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
package jahirfiquitiva.libs.kext.ui.layouts

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.IntRange
import androidx.appcompat.widget.AppCompatButton
import jahirfiquitiva.libs.kext.R
import jahirfiquitiva.libs.kext.extensions.inflate

/**
 * Originally created by Aidan Follestad
 */
open class SplitButtonsLayout @JvmOverloads constructor(
    context: Context,
    attributeSet: AttributeSet? = null,
    style: Int = 0
                                                       ) :
    LinearLayout(context, attributeSet, style) {
    
    var buttonCount: Int = 0
        set(@IntRange(from = 0, to = 4) value) {
            field = value
            weightSum = value.toFloat()
        }
    
    init {
        init()
    }
    
    private fun init() {
        orientation = HORIZONTAL
        if (isInEditMode) {
            buttonCount = 2
            addButton("GitHub", "https://github.com/jahirfiquitiva/kext")
            addButton("Website", "https://jahir.xyz/")
        }
    }
    
    override fun setOrientation(orientation: Int) = super.setOrientation(HORIZONTAL)
    
    fun addButton(text: String, link: String, fillAvailableSpace: Boolean = false) {
        if (hasAllButtons()) {
            Log.e(
                "SplitButtonsLayout",
                "Cannot add more buttons. $buttonCount buttons have already been added")
            return
        }
        val button: AppCompatButton? = context.inflate(R.layout.item_split_button, this)
        val lParams: LayoutParams = if (fillAvailableSpace) {
            LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 1F)
        } else {
            LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
        }
        button?.maxLines = 1
        button?.ellipsize = TextUtils.TruncateAt.END
        button?.id = childCount
        button?.text = text
        button?.tag = link
        button?.let { addView(it, lParams) }
    }
    
    fun hasAllButtons(): Boolean = childCount == buttonCount
}
