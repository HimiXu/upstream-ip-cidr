package upstream

import java.lang.IllegalArgumentException
import kotlin.math.pow

data class IpRange(val from: Long, val to: Long) {
    operator fun contains(ip: String): Boolean {
        return contains(ip.toNumericIp())
    }
    operator fun contains(numericIp: Long): Boolean {
        return numericIp in from..to
    }
}

/**
 * @return IpRange represented in that string
 */
fun String.toIpRange(): IpRange {

    if(!this.matches(Regex("^([0-9]{1,3}.){3}[0-9]{1,3}($|/([0-9]{1,3}))$")))
        throw IllegalArgumentException("String is not a CIDR notation")

    val (ip, ones) = this.split("/");
    // calculate the amount of hosts by cidr notation
    val hosts = (2.0).pow(32 - ones.toInt()).toInt()

    val numericIp = ip.toNumericIp()

    // calculate the range of valid ips in the current mask
    val fromIp = numericIp - numericIp % hosts;
    val toIp = fromIp + hosts-1;

    return IpRange(fromIp, toIp)
}

/**
 * This function takes a list of IpRanges
 * - sorts the list
 * - merges any intersecting ranges
 *
 * @return sorted merged list of IpRanges
 */
fun List<IpRange>.merge(): List<IpRange> {
    val sorted = this.sortedBy { it.from }
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

/**
 * @return numeric value of an iPv4 address (base 256 for every segment)
 */
fun String.toNumericIp(): Long {
    if(!this.matches(Regex("^([0-9]{1,3}.){3}[0-9]{1,3}$")))
        throw IllegalArgumentException("String is not an iPv4 address")
    return this.split(".")
        .map { seq -> seq.toLong() }
        .reversed()
        .reduceIndexed { index, acc, num -> acc + num * (256.0).pow(index).toLong() }
}
