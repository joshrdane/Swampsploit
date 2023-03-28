package me.smoothhacker.swampsploit

import kotlinx.serialization.cbor.Cbor
import me.smoothhacker.swampsploit.utils.ExploitContext
import me.smoothhacker.swampsploit.utils.ExploitContextSerializer
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File

class ExploitContextTests {
    @Test
    fun testSavingToDisk() {
        val dataFile = File("testExploitContext")
        val ctx = ExploitContext(dataFile)
        ctx.setPort(2222)
        ctx.setTimeout(1000)
        ctx.addHost("192.168.1.255")

        Cbor.encodeToByteArray(ExploitContextSerializer, ctx)

        val newCtx = ExploitContext(dataFile)
        assertEquals(newCtx.getPort(), ctx.getPort())
        assertEquals(newCtx.getTimeout(), ctx.getTimeout())
        assertEquals(newCtx.getHost(0), ctx.getHost(0))
    }
}