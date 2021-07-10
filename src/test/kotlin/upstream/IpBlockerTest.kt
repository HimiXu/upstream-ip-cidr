package upstream

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class IpBlockerTest {

    @Test
    fun isAllowed_withoutMerge() {
        val ipBlock = IpBlocker(listOf("111.111.1.111/24", "111.111.3.111/24", "111.111.5.111/24"))

        for (i in 0..255) {
            assert(ipBlock.isAllowed("111.111.0.$i"))
            assertFalse(ipBlock.isAllowed("111.111.1.$i"))
            assert(ipBlock.isAllowed("111.111.2.$i"))
            assertFalse(ipBlock.isAllowed("111.111.3.$i"))
            assert(ipBlock.isAllowed("111.111.4.$i"))
            assertFalse(ipBlock.isAllowed("111.111.5.$i"))
            assert(ipBlock.isAllowed("111.111.6.$i"))
        }
    }

    @Test
    fun isAllowed_withMerge() {
        val ipBlock = IpBlocker(listOf("111.111.111.111/24", "111.111.0.111/24", "111.111.1.111/24"))

        for (i in 0..255) {
            assert(ipBlock.isAllowed("111.111.255.$i"))
            assertFalse(ipBlock.isAllowed("111.111.0.$i"))
            assertFalse(ipBlock.isAllowed("111.111.1.$i"))
            assert(ipBlock.isAllowed("111.111.2.$i"))
            assert(ipBlock.isAllowed("111.111.110.$i"))
            assertFalse(ipBlock.isAllowed("111.111.111.$i"))
            assert(ipBlock.isAllowed("111.111.112.$i"))
        }
    }

}