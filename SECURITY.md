## Reporting security issues

Please report security issues related to the project to the
following email address:

   ohadshai(at)gmail.com




## Verifying contents

All SLF4J project artifacts published on Maven central are signed. For
each artifact, there is an associated signature file with the .asc
suffix.

### 2.1.14 +

To verify the signature use [this public key](https://keys.openpgp.org/vks/v1/by-fingerprint/47EB6836245D2D40E89DFB4136D4E9618F3ADAB5). 
Here is its fingerprint:
```
pub   rsa3072 2021-11-27 [SCEA]
      47EB6836245D2D40E89DFB4136D4E9618F3ADAB5
sub   rsa3072 2021-11-27 [E]
```

A copy of this key is stored on the
[keys.openpgp.org](https://keys.openpgp.org) keyserver. To add it to
your public key ring use the following command:

```
> FINGER_PRINT=47EB6836245D2D40E89DFB4136D4E9618F3ADAB5
> gpg  --keyserver hkps://keys.openpgp.org --recv-keys $FINGER_PRINT
```

### 2.0.8-2.0.11

To verify the signature use [this public key](https://keyserver.ubuntu.com/pks/lookup?op=get&search=0x637b8fb6cd0b57ca1e833e897f083a4ab2af5107).
Here is its fingerprint:
```
pub   rsa3072 2021-05-30 [SC]
      637B8FB6CD0B57CA1E833E897F083A4AB2AF5107
uid           Ohad Shai <ohadshai@gmail.com>
sub   rsa3072 2021-05-30 [E]
```

A copy of this key is stored on the
[keyserver.ubuntu.com](https://keyserver.ubuntu.com) keyserver. To add it to
your public key ring use the following command:

```
> FINGER_PRINT=637B8FB6CD0B57CA1E833E897F083A4AB2AF5107
> gpg  --keyserver hkps://keyserver.ubuntu.com --recv-keys $FINGER_PRINT
```
### 2.0.2-2.0.7

To verify the signature use [this public key](https://keyserver.ubuntu.com/pks/lookup?op=get&search=0xe52567d2589415bd74eb4c2867631bc0568801c3).
Here is its fingerprint:
```
pub   rsa4096 2016-08-29 [SC]
      E52567D2589415BD74EB4C2867631BC0568801C3
uid           Ohad Shai <ohadshai@gmail.com>
sub   rsa4096 2016-08-29 [E]
```

A copy of this key is stored on the
[keyserver.ubuntu.com](https://keyserver.ubuntu.com) keyserver. To add it to
your public key ring use the following command:

```
> FINGER_PRINT=E52567D2589415BD74EB4C2867631BC0568801C3
> gpg  --keyserver hkps://keyserver.ubuntu.com --recv-keys $FINGER_PRINT
```
