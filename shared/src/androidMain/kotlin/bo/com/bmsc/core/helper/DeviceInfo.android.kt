package bo.com.bmsc.core.helper

import android.annotation.SuppressLint
import android.provider.Settings.Secure

@SuppressLint("HardwareIds")
actual fun getDeviceId(): String {
  return "Information"
//  return Secure.getString(BaseApp.instance.contentResolver, Secure.ANDROID_ID).uppercase()
}
