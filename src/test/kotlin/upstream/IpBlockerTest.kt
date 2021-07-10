package upstream

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IpBlockerTest {

    @Test
    fun merge() {
        val merged = merge(listOf(IpRange(1,2), IpRange(2,3), IpRange(4,5), IpRange(5,6)))
        val expected = listOf(IpRange(1,3),IpRange(4,6))
        assertEquals(expected, merged)
    }

}