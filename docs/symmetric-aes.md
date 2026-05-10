# Symmetric Encryption — AES Deep Dive

## How AES Works

AES (Advanced Encryption Standard) is a **substitution-permutation network (SPN)** operating on 128-bit (16-byte) blocks, with key sizes of 128, 192, or 256 bits.

```
State = 4×4 byte matrix

Each round:
  1. SubBytes    — non-linear S-box substitution (GF(2^8))
  2. ShiftRows   — cyclic row shifts
  3. MixColumns  — matrix multiplication over GF(2^8)
  4. AddRoundKey — XOR with round key derived from KeySchedule

Rounds: 10 (128-bit key), 12 (192-bit), 14 (256-bit)
```

## Mode of Operation

This project uses `AES` with no explicit mode, which **defaults to ECB (Electronic Code Book)** in Java's JCE.

### ECB — Why It Is Problematic

ECB encrypts each 16-byte block independently with the same key:

```
C_i = AES_K(P_i)
```

**Consequence**: identical plaintext blocks produce identical ciphertext blocks — structure is preserved.  
The "ECB penguin" is the canonical illustration: encrypting a bitmap in ECB mode leaves the image outline visible.

### Recommended Modes

| Mode | IV needed | Authenticated | Notes |
|------|-----------|---------------|-------|
| ECB  | No        | No            | **Do not use in production** |
| CBC  | Yes       | No            | Requires PKCS#7 padding; vulnerable to padding oracle if unauthenticated |
| CTR  | Yes (nonce) | No          | Turns AES into a stream cipher |
| GCM  | Yes       | **Yes**       | **Recommended** — provides confidentiality + integrity |

### Production-Grade AES-GCM Example

```scala
import javax.crypto.{Cipher, KeyGenerator}
import javax.crypto.spec.{GCMParameterSpec, SecretKeySpec}
import java.security.SecureRandom

val KEY_SIZE  = 256          // bits
val GCM_IV    = 12           // bytes (96-bit nonce — NIST recommended)
val GCM_TAG   = 128          // bits

def aesGcmEncrypt(plaintext: Array[Byte], key: SecretKeySpec): (Array[Byte], Array[Byte]) = {
  val iv = new Array[Byte](GCM_IV)
  SecureRandom.getInstanceStrong.nextBytes(iv)

  val cipher = Cipher.getInstance("AES/GCM/NoPadding")
  cipher.init(Cipher.ENCRYPT_MODE, key, new GCMParameterSpec(GCM_TAG, iv))
  (iv, cipher.doFinal(plaintext))   // prepend iv to ciphertext on the wire
}
```

## Key Derivation — Current vs Best Practice

### Current implementation

```scala
// SymmetricEncryptor / SymmetricDecryptor
val key = new SecretKeySpec(secretKeyBytes(secretKey, 16), "AES")
```

`secretKeyBytes` pads the raw string to 16 bytes with ASCII spaces — this is **not key derivation** and has several weaknesses:

- Low-entropy keys (human passwords) are used directly.
- Padding with spaces is predictable and reduces effective key space.
- No salt → identical passwords produce identical keys (rainbow-table vulnerable).

### Recommended — PBKDF2 / Argon2

```scala
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.PBEKeySpec

def deriveKey(password: String, salt: Array[Byte]): SecretKeySpec = {
  val spec    = new PBEKeySpec(password.toCharArray, salt, 310_000, 256)
  val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
  val keyBytes = factory.generateSecret(spec).getEncoded
  new SecretKeySpec(keyBytes, "AES")
}

// salt must be random and stored alongside ciphertext
val salt = new Array[Byte](16)
SecureRandom.getInstanceStrong.nextBytes(salt)
```

PBKDF2 iterations (310 000 for SHA-256) are calibrated to OWASP 2023 recommendations.

## Block Size vs Key Size

| Parameter     | Value |
|---------------|-------|
| Block size    | 128 bits (fixed, all AES variants) |
| Key size      | 128 / 192 / 256 bits |
| This project  | 128-bit (16-byte) key |

AES-128 remains unbroken. AES-256 is preferred for long-lived secrets (quantum resistance via Grover's algorithm halves effective security bits).

## Hybrid Encryption Pattern

RSA (asymmetric) + AES (symmetric) is the standard hybrid pattern used in TLS, PGP, and Signal:

```
Sender
  1. Generate random 256-bit AES key  (DEK — Data Encryption Key)
  2. Encrypt payload    with AES-GCM(DEK)
  3. Encrypt DEK        with RSA-OAEP(recipientPublicKey)
  4. Send: { encryptedDEK, iv, ciphertext, gcmTag }

Receiver
  1. Decrypt DEK        with RSA-OAEP(privateKey)
  2. Decrypt ciphertext with AES-GCM(DEK, iv)
```

This avoids the RSA plaintext size limit (86 bytes for 1024-bit) and achieves both asymmetric key exchange and efficient symmetric bulk encryption.

## References

- [AES — FIPS 197](https://csrc.nist.gov/publications/detail/fips/197/final)
- [NIST SP 800-38D — GCM](https://csrc.nist.gov/publications/detail/sp/800-38d/final)
- [OWASP Password Storage Cheat Sheet](https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html)
- [Padding Oracle Attack — Vaudenay 2002](https://link.springer.com/chapter/10.1007/3-540-46035-7_35)
