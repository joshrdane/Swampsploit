package me.smoothhacker.swampsploit.auxilary

import org.apache.commons.net.util.SubnetUtils
import java.io.File
import java.net.Inet4Address
import java.net.InterfaceAddress
import java.net.NetworkInterface.getNetworkInterfaces
import java.net.Socket
import java.net.SocketException


class TcpScanner(private val dataDirPath: String) {
    private val hostList: ArrayList<String> = ArrayList()
    private var targetPort: Int = 22
    private var hasFailed: Boolean = false

    fun addHost(host: String) {
        this.hostList.add(host)
    }

    fun setTargetPort(port: Int) {
        this.targetPort = port
    }

    private fun getLocalIPAddress(): InterfaceAddress? {
        try {
            for (enumIpAddr in getNetworkInterfaces()) {
                if (enumIpAddr.isLoopback)
                    continue
                // Iterate trough IPs
                for (IpAddr in enumIpAddr.inetAddresses) {
                    if (!IpAddr.isLoopbackAddress && IpAddr is Inet4Address) {
                        return enumIpAddr.interfaceAddresses.find { it.address == IpAddr }
                    }
                }
            }
        } catch (ex: SocketException) {
            ex.printStackTrace()
        }
        return null
    }

    fun scanForHosts(): Boolean {
        val ipInterface: InterfaceAddress = getLocalIPAddress()!!

        // check for networks that have more than 64k hosts
        if (32 - ipInterface.networkPrefixLength > 24) return false
        val utils = SubnetUtils(
            "%s/%d".format(
                ipInterface.address.hostAddress,
                32 - ipInterface.networkPrefixLength
            )
        )

        Thread {
            for (ip in utils.info.allAddresses) {
                try {
                    val socket = Socket(ip, targetPort)
                    socket.close()
                } catch (ex: Exception) {
                    this.hasFailed = true
                    //println("%s failed to connect".format(ip))
                    continue
                }
                hostList.add(ip)
            }
        }.start()
        return this.hasFailed
    }

    fun save() {
        val hostsListFile = File(dataDirPath, "hosts_list.dat")
        hostsListFile.bufferedWriter().use { out ->
            {
                this.hostList.forEach { out.write(it) }
            }
        }
    }
}