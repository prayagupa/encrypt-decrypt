# Asymmetric Encryption — RSA Deep Dive

## How RSA Works

RSA (Rivest–Shamir–Adleman) is a **trapdoor one-way function** based on the computational hardness of factoring the product of two large primes.

```
Key generation
  1. Choose two large primes p, q
  2. n = p × q                    (modulus, public)
  3. λ(n) = lcm(p-1, q-1)        (Carmichael's totient)
  4. e: 1 < e < λ(n), gcd(e,λ(n))=1   (public exponent, typically 65537)
  5. d ≡ e⁻¹ (mod λ(n))          (private exponent)

Public key  = (n, e)
Private key = (n, d)

Encryption:  c = m^e mod n
Decryption:  m = c^d mod n
```

## Key Sizes and Security

| Key size | Security bits | Status (2025)        |
|----------|---------------|----------------------|
| 1024-bit | ~80           | **Broken / deprecated** |
| 2048-bit | ~112          | Acceptable (min)      |
| 3072-bit | ~128          | Recommended           |
| 4096-bit | ~140          | Long-term / paranoid  |

> ⚠️ **This project uses 1024-bit keys for demo purposes only.** NIST deprecated 1024-bit RSA in 2010. Production systems must use ≥ 2048-bit.

## Padding Scheme — OAEP

This project uses `RSA/ECB/OAEPWithSHA1AndMGF1Padding`.

**Why OAEP?**

- **Textbook RSA** (`m^e mod n` with no padding) is deterministic and malleable — the same plaintext always produces the same ciphertext, making it trivially distinguishable under CPA.
- **PKCS#1 v1.5** padding (older) is vulnerable to Bleichenbacher's 1998 padding oracle attack.
- **OAEP** (Optimal Asymmetric Encryption Padding) applies a randomised MGF (Mask Generation Function) before encryption, providing **IND-CCA2** security (indistinguishability under adaptive chosen-ciphertext attacks).

```
OAEP construction (encrypt path):
  seed  ← random (hLen bytes)
  DB    = lHash || PS || 0x01 || M
  dbMask  = MGF(seed, k - hLen - 1)
  maskedDB = DB XOR dbMask
  seedMask = MGF(maskedDB, hLen)
  maskedSeed = seed XOR seedMask
  EM  = 0x00 || maskedSeed || maskedDB
  c   = OS2IP(EM)^e mod n
```

## Scala Implementation

### Client — Encrypt with Public Key (`AsymmetricEncryptor`)

```scala
// Loads DER-encoded public key (X.509 SubjectPublicKeyInfo format)
val x509PublicSpec = new X509EncodedKeySpec(Files.readAllBytes(Paths.get(filename)))
val publicKey      = KeyFactory.getInstance("RSA").generatePublic(x509PublicSpec)

val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
cipher.init(Cipher.ENCRYPT_MODE, publicKey)
// ciphertext as hex string
new BigInteger(cipher.doFinal(data.getBytes())).toString(16)
```

Key points:
- **DER format** is the binary encoding of the ASN.1 structure; `X509EncodedKeySpec` expects this.
- Ciphertext is serialised as a hex (`base-16`) string for transport.
- `ECB` in the cipher string is a misnomer from JCE naming — RSA operates on a single block; there is no block-chaining mode.

### Server — Decrypt with Private Key (`AsymmetricServer`)

```scala
// Loads DER-encoded private key (PKCS#8 format)
val privateKeySpec = new PKCS8EncodedKeySpec(Files.readAllBytes(Paths.get(filename)))
val privateKey     = KeyFactory.getInstance("RSA").generatePrivate(privateKeySpec)

val cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding")
cipher.init(Cipher.DECRYPT_MODE, privateKey)
new String(cipher.doFinal(hexBytes))
```

## Key Formats

| Format | Encoding | JCE Spec class            | `openssl` command                               |
|--------|----------|---------------------------|-------------------------------------------------|
| PEM    | Base64   | Not directly loadable     | `openssl genrsa`, `openssl rsa`                 |
| DER    | Binary   | `X509EncodedKeySpec` (pub) / `PKCS8EncodedKeySpec` (priv) | `openssl rsa -outform DER` |

### Generate DER keys

```bash
# 2048-bit (use in production; project uses 1024 for demo)
openssl genrsa -out private_key.pem 2048
openssl rsa    -in  private_key.pem -pubout -outform DER -out public_key.der
openssl pkcs8  -topk8 -nocrypt -in private_key.pem -outform DER -out private_key.der
```

### Generate PEM keys (included scripts)

```bash
openssl genrsa -out private_key.pem 1024
openssl rsa    -in  private_key.pem -out public_key.pem -outform PEM -pubout
```

Encrypt / decrypt via `openssl` (PEM):

```bash
# encrypt
echo '{"name": "prayagupd"}' > input_payload.json
openssl rsautl -encrypt -inkey public_key.pem -pubin -in input_payload.json -out output_payload.enc

# decrypt
openssl rsautl -decrypt -inkey private_key.pem -in output_payload.enc -out decrypted.json
```

## Maximum Plaintext Size

For RSA-OAEP with SHA-1 (hLen = 20 bytes):

```
maxPlaintext = keyLen - 2 × hLen - 2
             = 128    - 40        - 2   (for 1024-bit key)
             = 86 bytes
```

For 2048-bit: `256 - 42 = 214 bytes`.  
**RSA is not designed to encrypt large payloads.** Use it to encrypt a symmetric key (hybrid encryption) for large data.

## References

- [OAEP — Wikipedia](https://en.wikipedia.org/wiki/Optimal_asymmetric_encryption_padding)
- [PKCS#1 RFC 8017](https://datatracker.ietf.org/doc/html/rfc8017)
- [Bleichenbacher 1998 attack](https://link.springer.com/chapter/10.1007/BFb0055716)
- [NIST SP 800-131A — Transitioning Key Sizes](https://csrc.nist.gov/publications/detail/sp/800-131a/rev-2/final)
