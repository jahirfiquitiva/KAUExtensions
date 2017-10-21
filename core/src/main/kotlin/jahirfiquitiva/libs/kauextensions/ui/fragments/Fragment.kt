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

package jahirfiquitiva.libs.kauextensions.ui.fragments

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jahirfiquitiva.libs.kauextensions.ui.fragments.presenters.FragmentPresenter

abstract class Fragment<in T> : Fragment(), FragmentPresenter<T> {
    lateinit var content: View
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        if (getContentLayout() != 0) {
            val contentView = inflater?.inflate(getContentLayout(), container, false)
            contentView?.let {
                content = it
                initUI(content)
            }
            return contentView
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}