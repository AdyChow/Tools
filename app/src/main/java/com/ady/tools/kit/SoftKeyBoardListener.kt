package com.ady.tools.kit

import android.app.Activity
import android.graphics.Rect
import android.view.View

class SoftKeyBoardListener(activity: Activity) {
    private val rootView: View
    private var rootViewVisibleHeight = 0
    private var onSoftKeyBoardChangeListener: OnSoftKeyBoardChangeListener? = null

    init {
        rootView = activity.window.decorView

        // 监听视图树中全局布局发生改变或者视图树中的某个视图的可视状态发生改变
        rootView.viewTreeObserver.addOnGlobalLayoutListener {

            // 获取当前根视图在屏幕上显示的大小
            val r = Rect()
            rootView.getWindowVisibleDisplayFrame(r)
            val visibleHeight = r.height()
            if (rootViewVisibleHeight == 0) {
                rootViewVisibleHeight = visibleHeight
                return@addOnGlobalLayoutListener
            }

            // 根视图显示高度没有变化，可以看作软键盘显示／隐藏状态没有改变
            if (rootViewVisibleHeight == visibleHeight) {
                return@addOnGlobalLayoutListener
            }

            // 根视图显示高度变小超过 300，可以看作软键盘显示了
            if (rootViewVisibleHeight - visibleHeight > 300) {
                if (onSoftKeyBoardChangeListener != null) {
                    onSoftKeyBoardChangeListener!!.onKeyboardShow(
                        rootViewVisibleHeight - visibleHeight
                    )
                }
                rootViewVisibleHeight = visibleHeight
                return@addOnGlobalLayoutListener
            }

            // 根视图显示高度变大超过 300，可以看作软键盘隐藏了
            if (visibleHeight - rootViewVisibleHeight > 300) {
                if (onSoftKeyBoardChangeListener != null) {
                    onSoftKeyBoardChangeListener!!.onKeyboardHide(
                        visibleHeight - rootViewVisibleHeight
                    )
                }
                rootViewVisibleHeight = visibleHeight
                return@addOnGlobalLayoutListener
            }
        }
    }

    private fun setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener: OnSoftKeyBoardChangeListener) {
        this.onSoftKeyBoardChangeListener = onSoftKeyBoardChangeListener
    }

    interface OnSoftKeyBoardChangeListener {
        fun onKeyboardShow(height: Int)
        fun onKeyboardHide(height: Int)
    }

    companion object {
        fun setListener(
            activity: Activity, onSoftKeyBoardChangeListener: OnSoftKeyBoardChangeListener
        ) {
            val softKeyBoardListener = SoftKeyBoardListener(activity)
            softKeyBoardListener.setOnSoftKeyBoardChangeListener(onSoftKeyBoardChangeListener)
        }
    }
}