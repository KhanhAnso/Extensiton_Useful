package com.example.useful_extensions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import java.math.BigInteger
import java.nio.ByteBuffer
import java.time.ZonedDateTime
import java.util.Calendar
import java.util.UUID

//Tạo ra id ngẫu nhiên ko trùng lặp
fun generateUniqueId(): String {
    var uniqueId = 0L
    do {
        val uid = UUID.randomUUID()
        val buffer = ByteBuffer.wrap(ByteArray(16))
        buffer.putLong(uid.leastSignificantBits)
        buffer.putLong(uid.mostSignificantBits)
        val bi = BigInteger(buffer.array())
        uniqueId = bi.toLong()
    } while (uniqueId < 0)
    return uniqueId.toString()
}

//Gửi dữ liệu liveData và xóa nó khi thay đổi
fun <T> LiveData<T>.observeOne(
    lifecycleOwner: LifecycleOwner,
    observer: Observer<T>
) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T) {
            observer.onChanged(t)
            removeObserver(this)
        }

    })
}

//Lấy ra tất cả permission của app
fun Context.retrievePermissions(): Array<String>? {
    return try {
        this.packageManager.getPackageInfo(
            this.packageName,
            PackageManager.GET_PERMISSIONS
        ).requestedPermissions
    } catch (e: PackageManager.NameNotFoundException) {
        Log.e(
            "Extensions ", e.message ?: """
            |Exception: NameNotFoundException
            |Function: Context.retrievePermissions
            |Something went wrong""".trimIndent()
        )
        null
    }
}

//Kiểm tra các quyền yêu cầu đã được cấp hay chưa
fun Activity.hasPermissionsAndGranted(): Boolean? {
    return this.applicationContext!!.retrievePermissions()?.all { permission ->
        ContextCompat.checkSelfPermission(
            this.applicationContext!!,
            permission
        ) == PackageManager.PERMISSION_GRANTED
    }
}

//Ẩn bàn phím khi click vào UI trên màn hình.
@SuppressLint("ClickableViewAccessibility")
fun setAutoHideKeyboardListener(context: Context, view: View) {
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            setAutoHideKeyboardListener(context, innerView)
        }
    }
    if (view !is ViewGroup) {
        view.setOnTouchListener { v, _ ->
            KeyboardManager(context, v).hideKeyboard()
            false
        }
    }
}

//Loại bỏ lắng nghe tự động ẩn keyboard ở function trên
fun removeAutoHideKeyboardListener(view: View) {
    if (view is ViewGroup) {
        for (i in 0 until view.childCount) {
            val innerView = view.getChildAt(i)
            removeAutoHideKeyboardListener(view)
        }
    }
    if (view !is ViewGroup) {
        view.setOnTouchListener(null)
    }
}

fun getDateTimeToday() =
    if (Build.VERSION.SDK_INT >= 26)
        ZonedDateTime.now().toString()
    else
        Calendar.getInstance().toString()