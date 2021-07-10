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

fun String.toIpRange(): IpRange {
    if(!this.matches(Regex("^([0-9]{1,3}.){3}[0-9]{1,3}($|/([0-9]{1,3}))$")))
        throw IllegalArgumentException("String is not a CIDR notation")
    val (ip, notation) = this.split("/");
    val hosts = (2.0).pow(32 - notation.toInt()).toInt()
    val numericIp = ip.toNumericIp()
    val from = numericIp - numericIp % hosts;
    val to = from + hosts-1;
    return IpRange(from, to)
}

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

fun String.toNumericIp(): Long {
    if(!this.matches(Regex("^([0-9]{1,3}.){3}[0-9]{1,3}$")))
        throw IllegalArgumentException("String is not an iPv4 address")
    return this.split(".")
        .map { seq -> seq.toLong() }
        .reversed()
        .reduceIndexed { index, acc, num -> acc + num * (256.0).pow(index).toLong() }
}
