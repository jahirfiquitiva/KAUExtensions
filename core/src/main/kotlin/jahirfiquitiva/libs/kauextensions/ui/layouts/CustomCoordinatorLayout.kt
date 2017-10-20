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
package jahirfiquitiva.libs.kauextensions.ui.layouts

import android.content.Context
import android.support.design.widget.CoordinatorLayout
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

open class CustomCoordinatorLayout : CoordinatorLayout {
    @Deprecated(
            "This variable has been changed in favor of \'scrollAllowed\'",
            ReplaceWith("scrollAllowed"))
    var allowScroll
        get() = scrollAllowed
        set(value) {
            scrollAllowed = value
        }
    
    var scrollAllowed = true
    
    constructor(context: Context) : super(context)
    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet)
    constructor(context: Context, attributeSet: AttributeSet, defStyleAttr: Int)
            : super(context, attributeSet, defStyleAttr)
    
    override fun onStartNestedScroll(child: View, target: View, nestedScrollAxes: Int) =
            scrollAllowed && super.onStartNestedScroll(child, target, nestedScrollAxes)
    
    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean =
            scrollAllowed && super.onInterceptTouchEvent(ev)
}