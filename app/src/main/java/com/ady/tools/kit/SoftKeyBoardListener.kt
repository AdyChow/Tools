package com.ady.tools.kit

import android.app.Activity
import android.graphics.Rect
import android.view.View
import android.view.ViewTreeObserver.OnGlobalLayoutListener

class SoftKeyBoardListener(activity: Activity) {
    private var rootView: View? = null
    private var rootViewVisibleHeight = 0
    private var onSoftKeyBoardChangeListener: OnSoftKeyBoardChangeListener? = null
    private var onGlobalLayoutListener: OnGlobalLayoutListener? = null

    init {
        rootView = activity.window.decorView

        // 监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        onGlobalLayoutListener = OnGlobalLayoutListener {

            // 获取当前根视图在屏幕上显示的大小
            val r = Rect()
            rootView!!.getWindowVisibleDisplayFrame(r)
            val visibleHeight = r.height()
            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            }

            // 根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
            if (rootViewVisibleHeight == visibleHeight) {
                return@OnGlobalLayoutListener
            }

            // 根视图显示高度变小超过 300，可以看作软键盘显示了
            if (rootViewVisibleHeight - visibleHeight > 300) {
                if (onSoftKeyBoardChangeListener != null) {
                    onSoftKeyBoardChangeListener!!.onKeyboardShow(
                        rootViewVisibleHeight - visibleHeight
                    )
                }
                rootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            }

            // 根视图显示高度变大超过 300，可以看作软键盘隐藏了
            if (visibleHeight - rootViewVisibleHeight > 300) {
                if (onSoftKeyBoardChangeListener != null) {
                    onSoftKeyBoardChangeListener!!.onKeyboardHide(
                        visibleHeight - rootViewVisibleHeight
                    )
                }
                rootViewVisibleHeight = visibleHeight
                return@OnGlobalLayoutListener
            }
        }
        rootView!!.viewTreeObserver.addOnGlobalLayoutListener(onGlobalLayoutListener)
    }

    interface OnSoftKeyBoardChangeListener {
        fun onKeyboardShow(height: Int)
        fun onKeyboardHide(height: Int)
    }

    fun setListener(onSoftKeyBoardChangeListener: OnSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener
    }

    fun removeListener() {
        if (onGlobalLayoutListener != null) {
            rootView?.viewTreeObserver?.removeOnGlobalLayoutListener(onGlobalLayoutListener)
            this.onGlobalLayoutListener = null
            this.onSoftKeyBoardChangeListener = null
        }
    }
}