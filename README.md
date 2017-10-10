client enc - server dec
-----------------------

pub private key pair example in scala


```bash

curl -XPOST 10.0.0.179:9999 -d "5aae8b0e3db810c13876838452b18cbede9890c665f2fd2ffe2dcccd7ba414687c1a9b531ca359e26c6f4433c54644c7bdbff159e55a5544905fed7598397476fdc4164c424c1505fd7cf5b2d0fc22b6981b2e12b4daf2c55180e4324c3917a1bbbe2d03ac55b801d5f6dcdc4e57ed9404b85082574530dbb80288de837757d0f51d49d74bd31297c75d18f03c43a403f7ffd21e69c556afa6747b02df8bbff6389b5e31ff5eff3eb2d402f8b97eb310391c73212ddb51fa6b1b130a51583d8a33151cf66fd3abc18ed22f5e78b2962cb99b881ee5f63e09096c10d96f95a830cf7f8e96b2efb54f7d7b7955786b3edb0f0678c09a86b67b1ded612ad4ad5be3" -H "Accept: application/json"

{"decrypted": "data to encrypt", "encrypted": "5aae8b0e3db810c13876838452b18cbede9890c665f2fd2ffe2dcccd7ba414687c1a9b531ca359e26c6f4433c54644c7bdbff159e55a5544905fed7598397476fdc4164c424c1505fd7cf5b2d0fc22b6981b2e12b4daf2c55180e4324c3917a1bbbe2d03ac55b801d5f6dcdc4e57ed9404b85082574530dbb80288de837757d0f51d49d74bd31297c75d18f03c43a403f7ffd21e69c556afa6747b02df8bbff6389b5e31ff5eff3eb2d402f8b97eb310391c73212ddb51fa6b1b130a51583d8a33151cf66fd3abc18ed22f5e78b2962cb99b881ee5f63e09096c10d96f95a830cf7f8e96b2efb54f7d7b7955786b3edb0f0678c09a86b67b1ded612ad4ad5be3"}
```


transformation format
---------------------

RSA/ECB/OAEPWithSHA1AndMGF1Padding

https://stackoverflow.com/a/19002068/432903

https://stackoverflow.com/a/37397545/432903

http://www.mysamplecode.com/2011/08/rsa-encryption-decryption-using-bouncy.html

http://www.mysamplecode.com/2011/08/java-rsa-encrypt-string-using-bouncy.html

https://en.wikipedia.org/wiki/Padding_(cryptography)

```
Official messages often start and end in predictable ways: My dear ambassador, Weather report, Sincerely yours, etc. The primary use of padding with classical ciphers is to prevent the cryptanalyst from using that predictability to find known plaintext[1] that aids in breaking the encryption. Random length padding also prevents an attacker from knowing the exact length of the plaintext message.
```

https://en.wikipedia.org/wiki/Optimal_asymmetric_encryption_padding

```
a padding scheme often used together with RSA encryption.
```
