package upstream

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class IpRangeTest {

    @Test
    fun inRange() {

        val ipRange1 = "111.111.111.111/24".toIpRange()
        assert("111.111.111.111" in ipRange1)
        assert("111.111.111.0" in ipRange1)
        assert("111.111.111.255" in ipRange1)
        assert("111.111.111.3" in ipRange1)
        assertFalse("111.111.112.3" in ipRange1)
        assertFalse("111.111.110.3" in ipRange1)

        val ipRange2 = "111.111.111.111/17".toIpRange()
        assert("111.111.111.111" in ipRange2)
        assert("111.111.111.0" in ipRange2)
        assert("111.111.111.255" in ipRange2)
        assert("111.111.111.3" in ipRange2)
        assert("111.111.0.3" in ipRange2)
        assert("111.111.127.3" in ipRange2)
        assertFalse("111.111.128.3" in ipRange2)

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
}