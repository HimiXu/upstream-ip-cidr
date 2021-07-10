package upstream

class IpBlocker(suspiciousIps: List<String>) {
    private val suspiciousIps: List<IpRange> = merge(suspiciousIps.map { it.toIpRange() })

    fun isAllowed(incomingIp: String): Boolean {
        val numericIp = incomingIp.toNumericIp()
        val index = suspiciousIps.binarySearch { it.to.compareTo(numericIp) }
        if (index >= 0) return false;
        val ipRange = suspiciousIps.get(-index-1)
        return numericIp in ipRange
    }
}


fun merge(ranges: List<IpRange>): List<IpRange> {
    val sorted = ranges.sortedBy { it.from }
    val merged = mutableListOf<IpRange>()
    var from = sorted.first().from
    var to = sorted.first().to
    sorted.subList(1, sorted.size).forEach{
        if (to < it.from) {
            merged.add(IpRange(from, to))
            from = it.from
        }
        to = it.to
    }
    merged.add(IpRange(from, to))
    return merged
}
