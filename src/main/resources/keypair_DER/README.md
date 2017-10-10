ASN.1 encoding format/ X.690/ Distinguished Encoding Rules(D Enc R) encoding
----------------------------------------------------------------------------

https://github.com/astrapi69/mystic-crypt/wiki/Generate-public-and-private-key

```
openssl genrsa -out keypair.pem 2048
openssl pkcs8 -topk8 -in keypair.pem -outform DER -out private.der -nocrypt
openssl rsa -in keypair.pem -outform DER -pubout -out public.der
```

https://en.wikipedia.org/wiki/X.690#DER_encoding

http://luca.ntop.org/Teaching/Appunti/asn1.html


DER vs Base64 b2t
-----------------

https://mssec.wordpress.com/2014/03/06/what-is-the-difference-between-the-formats-der-encoded-and-base64-encoded-when-exporting-a-certificate/

https://www.lifewire.com/base64-encoding-overview-1166412
