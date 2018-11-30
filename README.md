client enc - server dec
-----------------------
gpg - OpenPGP encryption and signing tool

```bash
brew install gnupg

gpg --help
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

[create ascii armored output](https://access.redhat.com/documentation/en-US/Red_Hat_Enterprise_Linux/4/html/Step_by_Step_Guide/s1-gnupg-export.html)
--

```bash
gpg --armor --export upd@gmail.com > upd.asc
-----BEGIN PGP PUBLIC KEY BLOCK-----

mQENBFr+DzcBCADEGSWzg04eABsb5+W8S5+jbQaSHMLnyp3/YbN6K0cvi/YaOTyT
lvkiC08G7lECIlQHSnNyoXQq55HSBVPi/N9rMHwm9MthNay/L4sWjcntScp8hthw
yy0aCElzqgGG8U2q39uFQB+j6VTjez+MICQAI/c0+P8YJ55JB3tFxI+RYR6KrAHS
0RgkiXzTJtFf5vEqJdJppNBo6v57Vwv6e4V8t0RsZ1nvorgyXWMP606xuqqweguQ
Vw+YgEoFNDBKwXTa/r2Ezydu9FXZeSY1R4i7MZkLwEOcyJCMdvv2YH/W0C73xFMH
Z9rjQ682iDjNnkeHxaBdOf1tWOhv9hnkCpnhABEBAAG0IHByYXlhZ3VwZCA8cHJh
eWFnLnVwZEBnbWFpbC5jb20+iQFUBBMBCAA+FiEEJubREdEgU5iTXrBNgwZlX+n2
Nh8FAlr+DzcCGwMFCQPCZwAFCwkIBwIGFQoJCAsCBBYCAwECHgECF4AACgkQgwZl
X+n2Nh8XCAf/VqmXvMaKiF5EbHl6pcjoAEescgj53IC0y2QMwrGgbmhpDrPTT3Yv
xWOPD0NrkIRTUQ5+tHbw8mpxSR+UfFPAmNlvukOdPieivbynOdoE9t3CFSZFsGjJ
MSvqC8jQ+ZWYn+z2lxybfb4T5/nJ4e/7Cvx0akvopCd9SD/4cRgKZSXewv/g1Si8
9ptXBNitG+jQRJ8bCulJlZxR0nO2hJaxpjVE9OY/d0yUqnyeH8HXb9HKvt6NgKXd
Ma4aYaBJtbAnZ5zlgShC4U2sqT8Leim42eULducwo7ZCCfndx+ZaXlWRlUYs/clh
te8oRVFBJeY6bRBXQsXM3SKDDzged/oa7rkBDQRa/g83AQgAxbbnt1EP3g3vU3ot
qyklYe+6Pb7sC+cMVUtSjkZTUqjR9SiFO7273fXU3hK6ncnlFMiASwZIe9aa9F6T
uWgen5M9BcyjoroABvgf4abRKiJY2cUopcmNc/vePs/o+FNMoT6JbYuzjJuLWQ1e
ePdU7UJQwEmYkZDrms3U4+7nsbIsiiLWX45SIXsy9532VkW/vzF/cnl6mFcwiQ6e
L+EATiFqYjkLuf4v8KVDAgiz1evwa5F+iucR4benk8O1RivQBKFcOk0ba60Ukvzp
H2CJrXARqj6HNIUgq3Sv9w6YPueMq+dJsT+1+dZMuvhDDy+QB/fqfZFIaOEtvQQX
g+f5yQARAQABiQE8BBgBCAAmFiEEJubREdEgU5iTXrBNgwZlX+n2Nh8FAlr+DzcC
GwwFCQPCZwAACgkQgwZlX+n2Nh974Qf+M5q81hy9M92TtvoY0qrVK3C/6/WqGskd
cz6chiGWkt1DmT5K3Lbd8cHb4P+/q8zrDyPt+z68CJ/3BDtMKVFP6cb39qYacygd
N+jgGnI4qedgDXpz2MapkCgXzq1drwFSZca3WFnHCzyWC3XBsISj2OCdK+himVZE
3UvUJCA6Pfzt1ckXFk5blot/Pp4HaWvNK/lMiTwGy3W3bvDSKc3aD7CBI/8kc/bn
YPeFRK5War9ZqCDcwyyl9tpi9wWCZ2JXPWSvojpeD+JyTjuCTcxL8BNEMMrI9Tpq
kMrdd7XvnE4MWeTn54JiDY+iryWq5hPTcUoKpG3PHOYEFxhrncpeQQ==
=Z1Nk
-----END PGP PUBLIC KEY BLOCK-----
```
