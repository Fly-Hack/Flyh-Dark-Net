# Flyh Dark Net

Android SSH + TLS VPN **starter project**, Kotlin, Android 9+.

## Current state (v0.1.0)
- Dark Android UI with server host, TLS port and SSH username fields.
- Validates non-secret profile fields.
- Requests Android VPN permission through `VpnService.prepare()`.
- Defines a `VpnService` stub but does not start it or establish a TUN interface.
- **Does not connect to SSH/TLS or route traffic. Not yet a working VPN.**

## Build
1. Open in Android Studio (JDK 17, Android SDK 35).
2. Install Gradle 8.9 if needed; project does not include a Gradle wrapper binary.
3. Run `gradle assembleDebug` or build from Android Studio.
4. Debug APK: `app/build/outputs/apk/debug/app-debug.apk`.

## Roadmap to a working VPN
1. Implement TLS transport with normal X.509 certificate + hostname validation; do not disable validation.
2. Authenticate SSH with verified server host keys and secure credential handling; support key-based auth.
3. Design a server-side packet relay and authenticated framing over SSH channels.
4. Implement TUN-to-transport forwarding (IP, TCP/UDP, DNS, IPv4/IPv6) with bounded queues, cancellation and reconnection.
5. Protect the tunnel socket with `VpnService.protect()`, then establish the TUN and implement foreground service lifecycle and notifications.
6. Add leak prevention, safe logging, tests and real-device validation.

**Important:** A generic SSH dynamic SOCKS proxy is not automatically a device-wide Android VPN. A packet translation or relay engine is required. You must operate or have authorization to use a compatible server. No promise of free mobile data or bypassing operator billing.

## GitHub
Suggested repo: `Fly-Hack/Flyh-Dark-Net`. Do not commit passwords, private keys, API tokens or server secrets.
