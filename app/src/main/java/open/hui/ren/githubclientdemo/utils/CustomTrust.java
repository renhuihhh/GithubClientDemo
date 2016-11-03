/*
 *   Copyright (C) 2016 The Android Open Source Project
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package open.hui.ren.githubclientdemo.utils;

import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.util.Arrays;
import java.util.Collection;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.CertificatePinner;
import okio.Buffer;

/**
 * @author renhui
 * @date 2016/08/09
 * @desc 自定义的http　TLS　key　store证书列表
 */

public class CustomTrust {

    X509TrustManager trustManager;
    SSLSocketFactory sslSocketFactory;

    public CustomTrust(InputStream inputStream) {
        try {
            trustManager = trustManagerForCertificates(inputStream);
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public CustomTrust() {
        try {
            trustManager = trustManagerForCertificates(trustedCertificatesInputStream());
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, new TrustManager[]{trustManager}, null);
            sslSocketFactory = sslContext.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }
    }

    public X509TrustManager getTrustManager() {
        return trustManager;
    }

    public SSLSocketFactory getSslSocketFactory() {
        return sslSocketFactory;
    }

    /**
     * Returns an input stream containing one or more certificate PEM files. This implementation just
     * embeds the PEM files in Java strings; most applications will instead read this from a resource
     * file that gets bundled with the application.
     */
    private InputStream trustedCertificatesInputStream() {
        String custom = "" +
            "-----BEGIN CERTIFICATE-----\n" +
            "MIIHEjCCBfqgAwIBAgIISDeAVLqbqN0wDQYJKoZIhvcNAQELBQAwSTELMAkGA1UE\n" +
            "BhMCVVMxEzARBgNVBAoTCkdvb2dsZSBJbmMxJTAjBgNVBAMTHEdvb2dsZSBJbnRl\n" +
            "cm5ldCBBdXRob3JpdHkgRzIwHhcNMTYwODE3MTg1NjQzWhcNMTYxMTA5MTgyOTAw\n" +
            "WjBmMQswCQYDVQQGEwJVUzETMBEGA1UECAwKQ2FsaWZvcm5pYTEWMBQGA1UEBwwN\n" +
            "TW91bnRhaW4gVmlldzETMBEGA1UECgwKR29vZ2xlIEluYzEVMBMGA1UEAwwMKi5n\n" +
            "b29nbGUuY29tMFkwEwYHKoZIzj0CAQYIKoZIzj0DAQcDQgAEsbWYcMhHxlbsg4I2\n" +
            "vb9pXesmHUs5QPRm9ZTF1l7pjSLXYKc2TEzouSlCaMvZhIRmdh0rbw401V7yJKme\n" +
            "cWAK0aOCBKowggSmMB0GA1UdJQQWMBQGCCsGAQUFBwMBBggrBgEFBQcDAjCCA2kG\n" +
            "A1UdEQSCA2AwggNcggwqLmdvb2dsZS5jb22CDSouYW5kcm9pZC5jb22CFiouYXBw\n" +
            "ZW5naW5lLmdvb2dsZS5jb22CEiouY2xvdWQuZ29vZ2xlLmNvbYIWKi5nb29nbGUt\n" +
            "YW5hbHl0aWNzLmNvbYILKi5nb29nbGUuY2GCCyouZ29vZ2xlLmNsgg4qLmdvb2ds\n" +
            "ZS5jby5pboIOKi5nb29nbGUuY28uanCCDiouZ29vZ2xlLmNvLnVrgg8qLmdvb2ds\n" +
            "ZS5jb20uYXKCDyouZ29vZ2xlLmNvbS5hdYIPKi5nb29nbGUuY29tLmJygg8qLmdv\n" +
            "b2dsZS5jb20uY2+CDyouZ29vZ2xlLmNvbS5teIIPKi5nb29nbGUuY29tLnRygg8q\n" +
            "Lmdvb2dsZS5jb20udm6CCyouZ29vZ2xlLmRlggsqLmdvb2dsZS5lc4ILKi5nb29n\n" +
            "bGUuZnKCCyouZ29vZ2xlLmh1ggsqLmdvb2dsZS5pdIILKi5nb29nbGUubmyCCyou\n" +
            "Z29vZ2xlLnBsggsqLmdvb2dsZS5wdIISKi5nb29nbGVhZGFwaXMuY29tgg8qLmdv\n" +
            "b2dsZWFwaXMuY26CFCouZ29vZ2xlY29tbWVyY2UuY29tghEqLmdvb2dsZXZpZGVv\n" +
            "LmNvbYIMKi5nc3RhdGljLmNugg0qLmdzdGF0aWMuY29tggoqLmd2dDEuY29tggoq\n" +
            "Lmd2dDIuY29tghQqLm1ldHJpYy5nc3RhdGljLmNvbYIMKi51cmNoaW4uY29tghAq\n" +
            "LnVybC5nb29nbGUuY29tghYqLnlvdXR1YmUtbm9jb29raWUuY29tgg0qLnlvdXR1\n" +
            "YmUuY29tghYqLnlvdXR1YmVlZHVjYXRpb24uY29tggsqLnl0aW1nLmNvbYIaYW5k\n" +
            "cm9pZC5jbGllbnRzLmdvb2dsZS5jb22CC2FuZHJvaWQuY29tggRnLmNvggZnb28u\n" +
            "Z2yCFGdvb2dsZS1hbmFseXRpY3MuY29tggpnb29nbGUuY29tghJnb29nbGVjb21t\n" +
            "ZXJjZS5jb22CGXBvbGljeS5tdGEtc3RzLmdvb2dsZS5jb22CCnVyY2hpbi5jb22C\n" +
            "Cnd3dy5nb28uZ2yCCHlvdXR1LmJlggt5b3V0dWJlLmNvbYIUeW91dHViZWVkdWNh\n" +
            "dGlvbi5jb20wCwYDVR0PBAQDAgeAMGgGCCsGAQUFBwEBBFwwWjArBggrBgEFBQcw\n" +
            "AoYfaHR0cDovL3BraS5nb29nbGUuY29tL0dJQUcyLmNydDArBggrBgEFBQcwAYYf\n" +
            "aHR0cDovL2NsaWVudHMxLmdvb2dsZS5jb20vb2NzcDAdBgNVHQ4EFgQUQk3s7FMa\n" +
            "0l/F7t4USbUzojT2WSowDAYDVR0TAQH/BAIwADAfBgNVHSMEGDAWgBRK3QYWG7z2\n" +
            "aLV29YG2u2IaulqBLzAhBgNVHSAEGjAYMAwGCisGAQQB1nkCBQEwCAYGZ4EMAQIC\n" +
            "MDAGA1UdHwQpMCcwJaAjoCGGH2h0dHA6Ly9wa2kuZ29vZ2xlLmNvbS9HSUFHMi5j\n" +
            "cmwwDQYJKoZIhvcNAQELBQADggEBAAxszqfmCXYIuGDIV2vI00pOG3T8O1QQ8la2\n" +
            "5t8MmEKA5sQLLKyxfIvrrWaDIauDSIFKWgkqRXVBwle6KjNwBeSH6mCzSf2wh2o8\n" +
            "aypo8zHfOD0my0yWCHU+oUXTx5M65g9LsiHlUs7Q/Hq/4hudZTC57PWxWQtg7D9/\n" +
            "LiW33NclXmCmbib3gZ2iY2GkwxHl+jqo3Sp328ZQPsJHkTd+Oo+RKb2asfKZxqBG\n" +
            "hBMylu1YOWqwjcsSvHhhBG1jNNotwi49YBo6Q6G6sdn01mhrCmu9P4570A2kexEN\n" +
            "JJeFmi6xMMo/cH+IfT2u3kI1CjeCOhSMYTkfl3SHNWq2UgBPYK8=\n" +
            "-----END CERTIFICATE-----";
        return new Buffer()
            .writeUtf8(custom)
            .inputStream();
    }

    /**
     * Returns a trust manager that trusts {@code certificates} and none other. HTTPS services whose
     * certificates have not been signed by these certificates will fail with a {@code
     * SSLHandshakeException}.
     * <p/>
     * <p>This can be used to replace the host platform's built-in trusted certificates with a custom
     * set. This is useful in development where certificate authority-trusted certificates aren't
     * available. Or in production, to avoid reliance on third-party certificate authorities.
     * <p/>
     * <p>See also {@link CertificatePinner}, which can limit trusted certificates while still using
     * the host platform's built-in trust store.
     * <p/>
     * <h3>Warning: Customizing Trusted Certificates is Dangerous!</h3>
     * <p/>
     * <p>Relying on your own trusted certificates limits your server team's ability to update their
     * TLS certificates. By installing a specific set of trusted certificates, you take on additional
     * operational complexity and limit your ability to migrate between certificate authorities. Do
     * not use custom trusted certificates in production without the blessing of your server's TLS
     * administrator.
     */
    private X509TrustManager trustManagerForCertificates(InputStream in)
        throws GeneralSecurityException {
        CertificateFactory                certificateFactory = CertificateFactory.getInstance("X.509");
        Collection<? extends Certificate> certificates       = certificateFactory.generateCertificates(in);
        if (certificates.isEmpty()) {
            throw new IllegalArgumentException("expected non-empty set of trusted certificates");
        }

        // Put the certificates a key store.
        char[]   password = "password".toCharArray(); // Any password will work.
        KeyStore keyStore = newEmptyKeyStore(password);
        int      index    = 0;
        for (Certificate certificate : certificates) {
            String certificateAlias = Integer.toString(index++);
            keyStore.setCertificateEntry(certificateAlias, certificate);
        }

        // Use it to build an X509 trust manager.
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(
            KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(keyStore, password);
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(
            TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(keyStore);
        TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();
        if (trustManagers.length != 1 || !(trustManagers[0] instanceof X509TrustManager)) {
            throw new IllegalStateException("Unexpected default trust managers:"
                + Arrays.toString(trustManagers));
        }
        return (X509TrustManager) trustManagers[0];
    }

    private KeyStore newEmptyKeyStore(char[] password) throws GeneralSecurityException {
        try {
            KeyStore    keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            InputStream in       = null; // By convention, 'null' creates an empty key store.
            keyStore.load(in, password);
            return keyStore;
        } catch (IOException e) {
            throw new AssertionError(e);
        }
    }
}
