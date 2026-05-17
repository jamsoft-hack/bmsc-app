package bo.com.bmsc.core.helper

import bo.com.bmsc.core.domain.InvalidArgument

data class SoftwareVersion(
  val major: Int,
  val minor: Int = 0,
  val patch: Int = 0,
) : Comparable<SoftwareVersion> {
  override fun compareTo(other: SoftwareVersion): Int {
    return compareValuesBy(
      this, other,
      { it.major },
      { it.minor },
      { it.patch }
    )
  }

  companion object {
    fun parse(versionString: String): SoftwareVersion {
      val parts = versionString.split(".").mapNotNull { it.toIntOrNull() }

      if (parts.isEmpty()) throw InvalidArgument("Invalid version string: $versionString")

      return SoftwareVersion(
        major = parts[0],
        minor = parts.getOrNull(index = 1) ?: 0,
        patch = parts.getOrNull(index = 2) ?: 0
      )
    }
  }
}
