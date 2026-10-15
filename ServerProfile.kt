package com.flyh.darknet.transport

/** Non-secret endpoint metadata. Never store passwords in this class. */
data class ServerProfile(val tlsHost: String, val tlsPort: Int?, val sshUsername: String) {
    fun validationError(): String? = when {
        tlsHost.isBlank() || tlsHost.length > 253 || tlsHost.any { it.isWhitespace() } -> "Domínio TLS inválido"
        tlsPort == null || tlsPort !in 1..65535 -> "Porta TLS inválida"
        sshUsername.isBlank() -> "Utilizador SSH obrigatório"
        else -> null
    }
}
