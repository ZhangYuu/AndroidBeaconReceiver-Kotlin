package cn.panzi.receiver.ext

import android.app.Activity
import android.widget.Toast

/**
 * Activity显示Toast
 */
fun Activity.showToast(content: String) {
    Toast.makeText(this, content, Toast.LENGTH_SHORT).show()
}