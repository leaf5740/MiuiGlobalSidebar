package com.milkui1

import android.app.Application
import com.hjq.toast.ToastUtils

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        ToastUtils.init(this);
    }

}