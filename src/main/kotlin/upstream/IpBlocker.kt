package upstream

class IpBlocker(suspiciousIps: List<String>) {

    // this is a sorted and merged list of the suspicious ip ranges
    private val suspiciousIps: List<IpRange> = suspiciousIps.map { it.toIpRange() }.merge()

    fun isAllowed(incomingIp: String): Boolean {
        val numericIp = incomingIp.toNumericIp()
        // it searches for the placement of the numericIp in the suspiciousIps list
        val index = suspiciousIps.binarySearch { it.to.compareTo(numericIp) }
        // if we found an index, then it means we have a range starting with that value, it is not allowed
        if (index >= 0) return false;
        // we found the placement
        val insertionPoint = -(index+1)
        // if the placement is at the end of the array then we should compare the last element
        if (insertionPoint == suspiciousIps.size) return numericIp !in suspiciousIps[suspiciousIps.size-1]
        // otherwise we should compare to the value currently in placement
        val ipRange = suspiciousIps[insertionPoint]
        return numericIp !in ipRange
    }
}

