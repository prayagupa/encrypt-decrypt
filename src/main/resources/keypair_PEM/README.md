Base62 b2t encoding
-------------------

find RSA algo bytes

126 bytes

won't work if the encrypted bytes is > 126 bytes

```
openssl rsa -text -noout -in private_key.pem 
Private-Key: (1024 bit)
modulus:
    00:d9:76:b4:81:84:1b:db:6d:9c:a0:fa:27:75:bf:
    76:8b:0d:65:7f:bb:dc:55:c7:ca:e6:17:db:d6:83:
    a4:fc:8f:3d:b8:aa:2a:92:1a:33:64:8c:37:54:a3:
    21:4b:18:3d:51:31:f1:6d:0e:2d:9e:f8:c4:69:06:
    d5:e0:dd:41:73:51:66:b2:09:f5:9b:9c:6a:43:4b:
    ee:04:a8:35:e8:10:df:23:12:08:69:be:7f:e6:36:
    57:7c:1b:51:85:44:ed:9f:e1:f8:6d:89:b5:1a:e2:
    f4:50:26:bc:c1:f8:e9:fc:59:cd:31:1b:43:da:67:
    33:01:b0:e0:05:dc:2b:78:81
publicExponent: 65537 (0x10001)
privateExponent:
    2e:c5:e9:6d:32:0d:0b:d5:37:ab:28:22:f4:a0:89:
    ce:be:c2:e6:06:72:0d:c1:3b:52:e2:2e:d6:a4:da:
    07:b8:87:7e:00:9b:c7:70:fb:3c:d5:2b:d1:78:84:
    e0:b2:05:fe:40:cf:bc:b5:1f:fa:e1:66:dd:70:2a:
    f0:82:a6:60:d1:ca:87:db:94:e5:c4:2f:b3:b4:7f:
    96:94:c3:46:d1:76:31:c9:68:64:24:88:0a:61:e5:
    11:20:46:c3:66:6f:1c:01:ff:41:88:56:ae:8b:11:
    ea:63:86:50:81:fa:ae:ba:2d:11:d1:38:da:18:2a:
    8b:15:3a:17:6c:bb:83:01
prime1:
    00:f1:91:64:a5:66:b7:85:f0:4f:d1:24:6b:c7:e5:
    22:a8:f0:3f:78:4a:a8:92:91:33:6d:44:e7:62:8b:
    e3:2e:9c:50:6f:b8:2c:9b:48:59:2e:02:09:da:0b:
    1e:bf:2d:22:b4:24:78:ea:32:90:c9:01:63:4f:6b:
    6d:13:9b:f1:8d
prime2:
    00:e6:74:a7:a6:de:7c:2f:a8:4c:e6:d2:b1:c9:78:
    a9:22:3f:9c:6a:7b:8c:0d:33:4b:73:12:3c:49:eb:
    0d:12:34:35:46:2b:44:92:29:82:09:c9:7e:92:b5:
    ec:4d:00:d5:33:3f:4f:e7:a5:db:c2:b8:f8:2c:79:
    e3:56:99:b3:c5
exponent1:
    00:9a:4a:96:31:3a:d9:2f:58:5d:72:a4:c3:9e:aa:
    90:a7:bc:c3:9d:4e:7a:3b:1b:38:f0:aa:d8:eb:4d:
    93:ee:5a:72:d9:d8:95:cd:1c:b9:b1:34:26:1c:b9:
    8b:8a:ea:06:47:6f:20:37:dd:d2:32:40:8b:b0:d7:
    59:94:f3:be:a5
exponent2:
    11:21:ca:f4:d4:c5:56:a5:a4:51:e4:87:e2:fa:5d:
    58:76:72:22:58:5c:e8:fe:4f:35:96:bd:74:a1:4c:
    af:75:c2:20:1d:47:53:b6:52:3c:78:06:38:d1:dd:
    67:3c:10:9d:66:af:82:01:0a:7d:47:52:eb:bb:83:
    f3:04:2a:21
coefficient:
    00:e8:2f:c3:1d:75:8b:c4:74:72:3a:2a:7d:37:2d:
    a4:60:2f:33:db:34:7c:b2:c7:1b:43:c9:20:6e:04:
    b5:97:b8:1e:3b:0d:e4:2b:56:fa:2e:0e:59:61:01:
    08:d5:32:e9:ee:2f:cf:a1:0b:28:68:2b:a7:f2:96:
    d0:79:61:50:65
```

```
echo 'This will be encrypted' | openssl rsautl -encrypt -pubin -inkey public_key.pem
???U?	??{?	?n>\S?ȉ???lC|?0?'?CT?f??t?6H??4??
                                                 w$E?^e??2?x?CN????3pq????@?@%e\?????2W?=ߝ??3??=V
??d8?>!
```
