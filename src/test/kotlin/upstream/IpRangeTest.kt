package upstream

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class IpRangeTest {

    @Test
    fun in_segment1() {
        val ipRange1 = "111.111.111.111/26".toIpRange()
        assert("111.111.111.64" in ipRange1)
        assert("111.111.111.111" in ipRange1)
        assert("111.111.111.127" in ipRange1)
        assertFalse("111.111.111.0" in ipRange1)
        assertFalse("111.111.111.255" in ipRange1)
        assertFalse("111.111.111.63" in ipRange1)
        assertFalse("111.111.111.128" in ipRange1)
        assertFalse("111.111.112.3" in ipRange1)
        assertFalse("111.111.110.3" in ipRange1)
    }

    @Test
    fun in_segment2() {
        val ipRange2 = "111.111.111.111/17".toIpRange()
        assert("111.111.111.111" in ipRange2)
        assert("111.111.111.0" in ipRange2)
        assert("111.111.111.255" in ipRange2)
        assert("111.111.111.3" in ipRange2)
        assert("111.111.0.3" in ipRange2)
        assert("111.111.127.3" in ipRange2)
        assertFalse("111.111.128.3" in ipRange2)
    }

    @Test
    fun in_segment3() {
        val ipRange3 = "111.111.111.111/11".toIpRange()
        assert("111.111.111.111" in ipRange3)
        assert("111.111.111.0" in ipRange3)
        assert("111.111.111.255" in ipRange3)
        assert("111.111.111.3" in ipRange3)
        assert("111.111.0.3" in ipRange3)
        assert("111.111.127.3" in ipRange3)
        assert("111.111.128.3" in ipRange3)
        assert("111.96.128.3" in ipRange3)
        assert("111.127.128.3" in ipRange3)
        assertFalse("111.128.128.3" in ipRange3)
        assertFalse("111.95.128.3" in ipRange3)
    }

    @Test
    fun in_segment4() {
        val ipRange4 = "111.111.111.111/4".toIpRange()
        assert("111.111.111.111" in ipRange4)
        assert("111.111.111.0" in ipRange4)
        assert("111.111.111.255" in ipRange4)
        assert("111.111.111.3" in ipRange4)
        assert("111.111.0.3" in ipRange4)
        assert("111.111.127.3" in ipRange4)
        assert("111.111.128.3" in ipRange4)
        assert("111.96.128.3" in ipRange4)
        assert("111.127.128.3" in ipRange4)
        assert("111.128.128.3" in ipRange4)
        assert("111.95.128.3" in ipRange4)
        assert("96.95.128.3" in ipRange4)
        assertFalse("112.95.128.3" in ipRange4)
        assertFalse("95.95.128.3" in ipRange4)
    }

    @Test
    fun merge() {
        val merged = listOf(IpRange(1, 2), IpRange(2, 3), IpRange(4, 5), IpRange(5, 6)).merge()
        val expected = listOf(IpRange(1, 3), IpRange(4, 6))
        assertEquals(expected, merged)
    }
}