package com.flyh.darknet.vpn

import android.net.VpnService

/**
 * Placeholder for future Android TUN packet processing.
 * Intentionally does NOT call Builder.establish(): doing so without
 * a functioning packet-forwarding engine would interrupt connectivity.
 */
class FlyhVpnService : VpnService()
