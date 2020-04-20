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
    static Cipher cipher;
    static byte[] signature;
    static byte[] digest;

    public MySignature(String pattern) {
        MySignature.setPattern(pattern);
    }

    public static MySignature getInstance(String pattern) {
        return new MySignature(pattern);
    }

    // Gera a chave pública e privada para iniciar a assinatura
    public static void initSign(String msg) throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        MySignature.setPublicKey(kp.getPublic());
        MySignature.setPrivateKey(kp.getPrivate());

        System.out.println("Gerando chave pública: " + MySignature.getPublicKey();
        System.out.println("Gerando chave privada: " + MySignature.getPrivateKey().getEncoded() + "\n");

        MySignature.setMD(MessageDigest.getInstance(MySignature.pattern));
    }

    // Gera o digest da mensagem fornecida
    public static void update(String msg) {
        System.out.println("Gerando o digest da mensagem... ");

        MySignature.getMD().update(msg.getBytes());
        MySignature.setDigest(MySignature.getMD().digest());

        System.out.println("Digest gerado: " + MySignature.convertToHex(MySignature.getDigest()).toString() + '\n');
    }

    // Inicia a verificação da assinatura digital, instanciando o decriptador
    public static void initVerify() throws InvalidKeyException {
        System.out.println("Iniciando a verificação da assinatura...");
        MySignature.getCipher().init(Cipher.DECRYPT_MODE, MySignature.publicKey);
    }

    // Recebe a mensagem fornecida e a assinatura, calcula o digest e compara com o
    // digest após a assinatura. Caso seja o mesmo, a assinatura é válida e a
    // mensagem estava integra.
    public static void verify(String message, byte[] signature)
            throws IllegalBlockSizeException, BadPaddingException, NoSuchAlgorithmException {

        MySignature.update(message);
        byte[] digestAfterSignature = MySignature.getCipher().doFinal(signature);

        System.out.println("Digest recalculado: " + MySignature.convertToHex(digestAfterSignature));

        for (int i = 0; i < MySignature.getDigest().length; i++) {
            if (MySignature.getDigest()[i] != digestAfterSignature[i]) {
                System.out.println("Assinatura inválida");
                System.exit(1);
            }
        }
        System.out.println("Assinatura válida");
    }

    // Produz a assinatura digital da mensagem fornecida
    public static byte[] sign() throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException,
            IllegalBlockSizeException, BadPaddingException {
        System.out.println("Assinando digitalmente a mensagem...");

        MySignature.setCipher(Cipher.getInstance("RSA"));
        MySignature.getCipher().init(Cipher.ENCRYPT_MODE, MySignature.privateKey);
        MySignature.setSignature(MySignature.getCipher().doFinal(MySignature.getDigest()));

        System.out.println(
                "Assinatura finalizada: " + MySignature.convertToHex(MySignature.getSignature()).toString() + '\n');
        return signature;
    }

    // ***************** AUXILIAR METHOD *****************//
    private static StringBuffer convertToHex(byte[] vec) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < vec.length; i++) {
            hexString.append(Integer.toHexString(0xFF & vec[i]));
        }
        return hexString;
    }
    // ***************************************************//

    // ***************** GETTERS AND SETTERS *****************//
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

    public static byte[] getDigest() {
        return MySignature.digest;
    }

    public static void setDigest(byte[] digest) {
        MySignature.digest = digest;
    }
    // *******************************************************//
}