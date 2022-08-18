package com.milkui1

import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.hjq.xtoast.XToast
import com.hjq.xtoast.draggable.SpringDraggable
import com.kongzue.dialogx.dialogs.PopTip
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var WindowNumber: Int = 300
    var State: Boolean = true
    val data = ArrayList<MyAdapterItems>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        for (i in 1..20) {
            data.add(MyAdapterItems(R.mipmap.ic_launcher))
        }

        button1.setOnClickListener {
            XXPermissions.with(this)
                .permission(Permission.SYSTEM_ALERT_WINDOW)
                .request(object : OnPermissionCallback {
                    override fun onGranted(granted: List<String>, all: Boolean) {
                        if (State) {
                            ShowWindowIcon(application)
                        } else {
                            PopTip.show("悬浮窗已开启")
                        }
                    }

                    override fun onDenied(denied: List<String>, never: Boolean) {
                        PopTip.show("请授权悬浮窗")
                    }
                })

        }

    }

    fun ShowWindowIcon(application: Application?) {
        State = false
        XToast<XToast<*>>(application).apply {
            setContentView(R.layout.window_icon)
            setGravity(Gravity.END or Gravity.TOP)
            setYOffset(WindowNumber)
            setDraggable(SpringDraggable())
            setOnClickListener(
                R.id.window_icon,
                XToast.OnClickListener<ImageView?> { MIconToast: XToast<*>, view: ImageView? ->
                    MIconToast.cancel()
                    ShowWindowMain(application)
                })

        }.show()

    }

    fun ShowWindowMain(application: Application?) {
        XToast<XToast<*>>(application).apply {
            setContentView(R.layout.window_main)
            setGravity(Gravity.END or Gravity.TOP)
            setYOffset(WindowNumber)
            //setDraggable(SpringDraggable())
            setOnClickListener(
                R.id.window_main,
                XToast.OnClickListener<LinearLayout?> { WMainToast: XToast<*>, view: LinearLayout? ->
                    WMainToast.cancel()
                    ShowWindowIcon(application)
                })
            val recyclerview = findViewById<RecyclerView>(R.id.window_recycler1)
            recyclerview.layoutManager = LinearLayoutManager(application)
            val adapter = MyPackageAdapter(data)
            recyclerview.adapter = adapter

        }.show()
    }


}