import java.security.*;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class MySignature {
    static String pattern;
    static MessageDigest md;
    static Key privateKey;
    static Key publicKey;
    static Key ey;
    static Cipher cipher;
    static byte[] signature;

    public static String getPattern() {
        return MySignature.pattern;
    }

    public static void setPattern(String pattern) {
        MySignature.pattern = pattern;
    }

    public static MessageDigest getMD() {
        return MySignature.md;
    }

    public static void setMD(MessageDigest md) {
        MySignature.md = md;
    }

    public static Key getPublicKey() {
        return MySignature.publicKey;
    }

    public static void setPublicKey(Key publicKey) {
        MySignature.publicKey = publicKey;
    }

    public static Key getPrivateKey() {
        return MySignature.privateKey;
    }

    public static void setPrivateKey(Key privateKey) {
        MySignature.privateKey = privateKey;
    }

    public static Cipher getCipher() {
        return MySignature.cipher;
    }

    public static void setCipher(Cipher cipher) {
        MySignature.cipher = cipher;
    }

    public static byte[] getSignature() {
        return MySignature.signature;
    }

    public static void setSignature(byte[] signature) {
        MySignature.signature = signature;
    }

    public static MySignature getInstance(String pattern) {
        MySignature.setPattern(pattern);
        return new MySignature();
    }

    public static void initSign(String msg) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        MySignature.setPublicKey(kp.getPublic());
        MySignature.setPrivateKey(kp.getPrivate());

        System.out.println("Gerando chave pública: " + MySignature.getPublicKey());
        System.out.println("Gerando chave privada: " + MySignature.getPrivateKey() + '\n');

        MySignature.setMD(MessageDigest.getInstance(MySignature.pattern));
    }

    public static void update(String msg) {
        System.out.println("Gerando o digest da mensagem... ");

        MySignature.getMD().update(msg.getBytes());
        byte[] digest = MySignature.getMD().digest();
        System.out.println(digest);

        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }

        System.out.println("Digest gerado: " + hexString.toString() + '\n');

    }

    public static void initVerify() throws InvalidKeyException {
        System.out.println("Iniciando a verificação da assinatura...");
        MySignature.getCipher().init(Cipher.DECRYPT_MODE, MySignature.publicKey);
    }

    public static void verify() throws IllegalBlockSizeException, BadPaddingException {
        byte[] digestAfterSignature = MySignature.getCipher().doFinal(signature);

        for (int i = 0; i < MySignature.getMD().digest().length; i++) {
            if (digestAfterSignature[i] != MySignature.getMD().digest()[i]) {
                System.out.println("Assinatura invalida" + '\n');
                return;
            }
        }
        System.out.println("Assinatura válida" + '\n');
    }

    public static void sign() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        System.out.println("Assinando digitalmente a mensagem...");

        StringBuffer hexString = new StringBuffer();

        MySignature.setCipher(Cipher.getInstance("RSA"));
        MySignature.getCipher().init(Cipher.ENCRYPT_MODE, MySignature.privateKey);
        MySignature.setSignature(MySignature.getCipher().doFinal(MySignature.getMD().digest()));
        for (int i = 0; i < MySignature.getSignature().length; i++) {
            hexString.append(Integer.toHexString(0xFF & MySignature.getSignature()[i]));
        }
        System.out.println("Assinatura finalizada: " + hexString.toString() + '\n');
    }
}