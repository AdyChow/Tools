package com.ady.tools.pages

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ady.tools.databinding.ActKeyboardBinding
import com.ady.tools.kit.SoftKeyBoardListener

class KeyboardAct : AppCompatActivity() {

    private var binding: ActKeyboardBinding? = null
    private var softKeyBoardListener: SoftKeyBoardListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActKeyboardBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        softKeyBoardListener = SoftKeyBoardListener(this)
        softKeyBoardListener?.setListener(
            object : SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
                override fun onKeyboardShow(height: Int) {
                    Toast.makeText(this@KeyboardAct, "键盘打开了", Toast.LENGTH_SHORT).show()
                }

                override fun onKeyboardHide(height: Int) {
                    Toast.makeText(this@KeyboardAct, "键盘关闭了", Toast.LENGTH_SHORT).show()
                }

            })
    }

    override fun onResume() {
        super.onResume()
        binding?.edit?.showKeyboard()
    }

    override fun onPause() {
        super.onPause()
        binding?.edit?.hideKeyboard()
    }

    fun EditText.showKeyboard() {
        requestFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(this, 0)
    }

    fun EditText.hideKeyboard() {
        clearFocus()
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        softKeyBoardListener?.removeListener()
    }
}