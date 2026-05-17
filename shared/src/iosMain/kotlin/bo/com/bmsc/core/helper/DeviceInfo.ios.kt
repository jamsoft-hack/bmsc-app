package bo.com.bmsc.core.helper

import es.smarting.logwood.LogWood
import bo.com.bmsc.app.service.KeychainService
import kotlinx.cinterop.ExperimentalForeignApi
import platform.CoreFoundation.kCFBooleanFalse
import platform.Security.kSecAttrAccessible
import platform.Security.kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly
import platform.Security.kSecAttrSynchronizable
import platform.UIKit.UIDevice
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private const val storeDeviceId = "ios_device_id"

@OptIn(ExperimentalUuidApi::class, ExperimentalForeignApi::class)
actual fun getDeviceId(): String {
  val keychain = KeychainService(
    Pair(kSecAttrSynchronizable, kCFBooleanFalse),
    Pair(kSecAttrAccessible, kSecAttrAccessibleAfterFirstUnlockThisDeviceOnly),
  )

  var deviceId = keychain.getStringOrNull(key = bo.com.bmsc.core.helper.storeDeviceId)

  LogWood.d("KeyChain", "keys ${keychain.keys}")

  if (deviceId == null) {
    val previousKeychain = KeychainService()

    deviceId = UIDevice.currentDevice.identifierForVendor?.UUIDString?.replace("-", "")
      ?: Uuid.random().toHexString()

    previousKeychain.remove(bo.com.bmsc.core.helper.storeDeviceId)
    keychain.putString(bo.com.bmsc.core.helper.storeDeviceId, deviceId)
  }

  return deviceId.uppercase()
}
