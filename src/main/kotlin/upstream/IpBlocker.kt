package upstream

class IpBlocker(suspiciousIps: List<String>) {
    private val suspiciousIps: List<IpRange> = suspiciousIps.map { it.toIpRange() }.merge()

    fun isAllowed(incomingIp: String): Boolean {
        val numericIp = incomingIp.toNumericIp()
        val index = suspiciousIps.binarySearch { it.to.compareTo(numericIp) }
        if (index >= 0) return false;
        val ipRange = suspiciousIps.get(-index-1)
        return numericIp in ipRange
    }
}

