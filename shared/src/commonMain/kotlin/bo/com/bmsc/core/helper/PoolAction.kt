package bo.com.bmsc.core.helper

import kotlinx.coroutines.delay
import kotlin.time.Clock

object PoolAction {
  suspend fun async(durationSeconds: Int, action: suspend (Int) -> Boolean) {
    val durationInSeconds = durationSeconds

    var progressInMillis = 0L
    var attempt = 1
    var completed = false

    do {
      val progressStart = Clock.System.now().epochSeconds

      try {
        completed = action(attempt)

        if (!completed) {
          delay(timeMillis = 1000)
        }
      } catch (ex: Throwable) {
        println("MakePoolAction: MakePoolAction: Error pooling requests $ex")
      }

      progressInMillis += (Clock.System.now().epochSeconds - progressStart)
      attempt++
    } while (!completed && progressInMillis < durationInSeconds)
  }
}