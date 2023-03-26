package me.smoothhacker.swampsploit

import me.smoothhacker.swampsploit.utils.ExploitContext

import org.junit.Test
import org.junit.Assert.*
import java.io.File

class ExploitContextTests {
    @Test
    fun testSavingToDisk() {
        val dataFile = File("testExploitContext")
        val ctx = ExploitContext(dataFile)
        ctx.setPort(2222)
        ctx.setTimeout(1000)
        ctx.addHost("192.168.1.255")

        ctx.save()

        val newCtx = ExploitContext(dataFile)
        assertEquals(newCtx.getPort(), ctx.getPort())
        assertEquals(newCtx.getTimeout(), ctx.getTimeout())
        assertEquals(newCtx.getHost(0), ctx.getHost(0))
    }
}