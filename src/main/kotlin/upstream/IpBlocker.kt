package upstream

class IpBlocker(suspiciousIps: List<String>) {
    private val suspiciousIps: List<IpRange> = suspiciousIps.map { it.toIpRange() }.merge()

    fun isAllowed(incomingIp: String): Boolean {
        val numericIp = incomingIp.toNumericIp()
        val index = suspiciousIps.binarySearch { it.to.compareTo(numericIp) }
        if (index >= 0) return false;
        val insertionPoint = -(index+1)
        if (insertionPoint >= suspiciousIps.size) return true;
        val ipRange = suspiciousIps[insertionPoint]
        return numericIp !in ipRange
    }
}

