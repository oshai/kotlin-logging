## Reporting security issues

Please report security issues related to the project to the
following email address:

   ohadshai(at)gmail.com




## Verifying contents

All `kotlin-logging` project artifacts published on Maven central are signed. For
each artifact, there is an associated signature file with the .asc
suffix.

### 2.1.14 +

To ensure the integrity and authenticity of [Software Name], follow these steps to verify its digital signature:

**1. Download and Import the Public Key:**

You can download and import my public key directly from the `keys.openpgp.org` keyserver using the following command:

```bash
gpg --keyserver hkps://keys.openpgp.org --recv-keys 47EB6836245D2D40E89DFB4136D4E9618F3ADAB5
```

Alternatively, you can use the Ubuntu keyserver:

```bash
gpg --keyserver hkps://keyserver.ubuntu.com --recv-keys 47EB6836245D2D40E89DFB4136D4E9618F3ADAB5
```
**2. Verify the Key Fingerprint (Important!):**

After importing the key, verify that its fingerprint matches the one below. This ensures you have the correct key and not a malicious imposter:

Fingerprint: `47EB6836245D2D40E89DFB4136D4E9618F3ADAB5`

Verify the fingerprint by running the following command and comparing the output:

```bash
gpg --fingerprint 47EB6836245D2D40E89DFB4136D4E9618F3ADAB5
```

**Do not proceed if the fingerprints do not match!**

**3. Download the Files:**

Download both the file you want to verify (e.g., `kotlin-logging-jvm-7.0.3.jar`) and its corresponding signature file (e.g., `kotlin-logging-jvm-7.0.3.jar.asc`) from [maven central](https://repo.maven.apache.org/maven2/io/github/oshai).

**4. Verify the Signature:**

Navigate to the directory where you downloaded the files and run the following command, replacing the file names with the actual names of the downloaded files:

```bash
gpg --verify kotlin-logging-jvm-7.0.3.jar.asc kotlin-logging-jvm-7.0.3.jar
```

You should see output similar to this if the verification is successful:

```bash
$ gpg --verify kotlin-logging-jvm-7.0.3.jar.asc kotlin-logging-jvm-7.0.3.jar
gpg:                using RSA key 36D4E9618F3ADAB5
gpg: Good signature from "Ohad Shai <ohadshai@gmail.com>" [unknown]
gpg: WARNING: This key is not certified with a trusted signature!
gpg:          There is no indication that the signature belongs to the owner.
Primary key fingerprint: 47EB 6836 245D 2D40 E89D  FB41 36D4 E961 8F3A DAB5
```

**If you see "Good signature"**, it means the file has not been tampered with and is genuinely from the person who signed it.

**If you see "BAD signature", do not trust the file**. It may have been corrupted or tampered with. Delete it and try downloading it again from a trusted source. If the problem persists, contact the mail above.


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
