package upstream

// ips are provided in CIDR notation
class IpBlocker(suspiciousIps: List<String>) {

    // this is a sorted and merged list of the suspicious ip ranges
    private val suspiciousIps: List<IpRange> = suspiciousIps.map { it.toIpRange() }.merge()

    fun isAllowed(incomingIp: String): Boolean {
        val numericIp = incomingIp.toNumericIp()
        // it searches for the placement of the numericIp in the suspiciousIps list
        val index = suspiciousIps.binarySearch { it.fromNumericIp.compareTo(numericIp) }
        // if we found an index, then it means we have a range starting with that value, it is not allowed
        if (index >= 0) return false;
        // we found the placement
        val insertionPoint = -(index+1)
        // if the placement is at the beginning of the array then its smaller than every suspiciousIp
        if (insertionPoint == 0) return true
        // otherwise we should check if the ip is within range of the item in insertionPoint-1
        return numericIp !in suspiciousIps[insertionPoint-1]
    }
}

