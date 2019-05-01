package com.example.tools.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 *
 * @author weitianliang
 */
abstract class BaseFragment : Fragment() {

    private var contentView: View? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        contentView = inflater.inflate(getLayoutResId(), container, false)
        onViewCreate(savedInstanceState)
        return contentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        onInflated(savedInstanceState)
    }

    /**
     * 手机返回事件监听
     */
    fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return false
    }

    /**
     * 获取当前fragment的Id
     */
    protected abstract fun getLayoutResId(): Int

    /**
     * 当 fragment inflated 结束的时候调用,此时view以创建完毕
     */
    protected abstract fun onInflated(savedInstanceState: Bundle?)

    /**
     * onCreateView 时调用
     */
    protected abstract fun onViewCreate(savedInstanceState: Bundle?)

    @Suppress("UNCHECKED_CAST")
    protected fun <T : View> findViewById(resId: Int): T {
        return contentView?.findViewById<View>(resId) as T
    }
}