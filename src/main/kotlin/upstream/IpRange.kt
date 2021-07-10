package upstream

import kotlin.math.pow

fun String.toIpRange(): IpRange {
    val (ip, notation) = this.split("/");
    val hosts = (2.0).pow(32 - notation.toInt()).toInt()
    val numericIp = ip.toNumericIp()
    val from = numericIp - numericIp % hosts;
    val to = from + hosts-1;
    return IpRange(from, to)
}

data class IpRange(val from: Long, val to: Long) {
    operator fun contains(ip: String): Boolean {
        return contains(ip.toNumericIp())
    }
    operator fun contains(numericIp: Long): Boolean {
        return numericIp in from..to
    }
}

fun String.toNumericIp(): Long {
    return this.split(".")
        .map { seq -> seq.toLong() }
        .reversed()
        .reduceIndexed { index, acc, num -> acc + num * (256.0).pow(index).toLong() }
}
