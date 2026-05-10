# Security Threat Model & Production Hardening

## Threat Model

### Assets

| Asset | Sensitivity |
|-------|-------------|
| Plaintext payload | High |
| Private key (`private_key.der`) | Critical |
| AES shared secret | Critical |
| Ciphertext in transit | Low (by design) |

### Attacker Capabilities (STRIDE)

| Threat | Vector | Current Mitigation | Gap |
|--------|--------|--------------------|-----|
| Spoofing | Forged client identity | None | No mutual TLS / client cert |
| Tampering | Bit-flip on ciphertext | None (no AEAD) | ECB has no integrity check |
| Repudiation | Replay of captured ciphertext | None | No nonce / timestamp |
| Info Disclosure | Key exfiltration | Keys on filesystem | No HSM / vault |
| DoS | Large POST bodies to server | None | No request size limit |
| Elevation | PKCS#1 padding oracle | RSA-OAEP used ✓ | OK |

## Key Management

### Current (demo)
Keys are stored as files on disk:
```
src/main/resources/keypair_DER/private_key.der   ← must NEVER be committed
src/main/resources/keypair_DER/public_key.der
src/main/resources/secret_key_AES/
```

### Production recommendations

1. **Hardware Security Modules (HSMs)** — AWS CloudHSM, Google Cloud HSM, or YubiHSM. Private key operations happen inside the HSM; the raw key material never leaves.
2. **Secrets Managers** — HashiCorp Vault, AWS Secrets Manager, Azure Key Vault. Rotate keys without redeployment.
3. **Envelope Encryption** — Encrypt the DEK with a Key Encryption Key (KEK) stored in the KMS; store only the encrypted DEK on disk.
4. **Never commit private keys to version control.** Add to `.gitignore`:
   ```
   **/*.der
   **/*.pem
   **/secret_key*
   ```

## Transport Security

The current server (`AsymmetricServer`) is a raw TCP socket over HTTP — no TLS.

**Production requirements:**
- Terminate TLS at the server or at an ingress (nginx, Envoy).
- Use TLS 1.3 (TLS 1.2 as fallback minimum; disable SSLv3, TLS 1.0, 1.1).
- Pin server certificates for internal service-to-service calls.
- Enforce `Content-Length` validation and max body size to prevent DoS.

## Cryptographic Agility

Hard-coding `"RSA/ECB/OAEPWithSHA1AndMGF1Padding"` makes migration painful when SHA-1 is deprecated.

**Recommendation:** externalise algorithm selection via config:

```hocon
# application.conf
crypto {
  asymmetric-algorithm = "RSA/ECB/OAEPWithSHA-256AndMGF1Padding"
  symmetric-algorithm  = "AES/GCM/NoPadding"
  key-size-bits        = 2048
}
```

## Known Issues in This Codebase

| Issue | File | Severity | Fix |
|-------|------|----------|-----|
| 1024-bit RSA keys | `keypair_DER/`, `keypair_PEM/` | High | Regenerate at ≥ 2048-bit |
| ECB mode for AES | `SymmetricEncryptor`, `SymmetricDecryptor` | High | Switch to AES/GCM/NoPadding |
| No MAC / AEAD on RSA path | `AsymmetricServer` | High | Combine with HMAC or switch to AESGCM wrapping |
| Raw password → key | `SymmetricEncryptor.secretKeyBytes` | High | Use PBKDF2 / Argon2 |
| No request body size limit | `AsymmetricServer.start()` | Medium | Validate `Content-Length` |
| Private key on classpath | `keypair_DER/private_key.der` | Critical | Move to vault / env var |
| No authentication of client | `AsymmetricServer` | Medium | mTLS or signed JWT |
