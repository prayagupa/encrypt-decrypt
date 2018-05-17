client enc - server dec
-----------------------

```
brew install gnupg
```

```bash
$ gpg --gen-key
gpg (GnuPG) 2.2.7; Copyright (C) 2018 Free Software Foundation, Inc.
This is free software: you are free to change and redistribute it.
There is NO WARRANTY, to the extent permitted by law.

Note: Use "gpg --full-generate-key" for a full featured key generation dialog.

GnuPG needs to construct a user ID to identify your key.

Real name: upd
Email address: upd@gmail.com
You selected this USER-ID:         
    "upd <upd@gmail.com>"

Change (N)ame, (E)mail, or (O)kay/(Q)uit? O
We need to generate a lot of random bytes. It is a good idea to perform
some other action (type on the keyboard, move the mouse, utilize the
disks) during the prime generation; this gives the random number
generator a better chance to gain enough entropy.
We need to generate a lot of random bytes. It is a good idea to perform
some other action (type on the keyboard, move the mouse, utilize the
disks) during the prime generation; this gives the random number
generator a better chance to gain enough entropy.
gpg: /Users/upd/.gnupg/trustdb.gpg: trustdb created
gpg: key 8306655FE9F6361F marked as ultimately trusted
gpg: directory '/Users/upd/.gnupg/openpgp-revocs.d' created
gpg: revocation certificate stored as '/Users/upd/.gnupg/openpgp-revocs.d/26E6D111D1205398935EB04D8306655FE9F6361F.rev'
public and secret key created and signed.

pub   rsa2048 2018-05-17 [SC] [expires: 2020-05-16]
      26E6D111D1205398935EB04D8306655FE9F6361F
uid                      upd <upd@gmail.com>
sub   rsa2048 2018-05-17 [E] [expires: 2020-05-16]
```

```
$ gpg --list-keys
gpg: checking the trustdb
gpg: marginals needed: 3  completes needed: 1  trust model: pgp
gpg: depth: 0  valid:   1  signed:   0  trust: 0-, 0q, 0n, 0m, 0f, 1u
gpg: next trustdb check due at 2020-05-16
/Users/upd/.gnupg/pubring.kbx
----------------------------------
pub   rsa2048 2018-05-17 [SC] [expires: 2020-05-16]
      26E6D111D1205398935EB04D8306655FE9F6361F
uid           [ultimate] upd <upd@gmail.com>
sub   rsa2048 2018-05-17 [E] [expires: 2020-05-16]
```

