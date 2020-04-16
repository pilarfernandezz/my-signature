import java.security.*;
import java.util.Scanner;

import javax.crypto.Cipher;

public class trab2 {
    public static void main(String args[]) throws Exception {
        // Reading data from user
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite a mensagem");
        String message = sc.nextLine();
        System.out.println(
                "Digite o padrão de assinatura:\n 1 - MD5withRSA\n 2 - SHA1withRSA\n 3 - SHA256withRSA\n 4 - SHA512withRSA");
        int signatureTyped = sc.nextInt();
        String signatureType = null;

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();

        System.out.println("CHAVE PUBLICA \n" + pub);
        System.out.println("CHAVE PRIVADA \n" + pvt);

        switch (signatureTyped) {
            case 1:
                signatureType = "MD5";
                break;
            case 2:
                signatureType = "SHA-1";
                break;
            case 3:
                signatureType = "SHA-256";
                break;
            case 4:
                signatureType = "SHA-512";
                break;
            default:
                System.out.println("Padrão de assinatura inválido.");
                System.exit(1);
        }

        // Creating the MessageDigest object
        MessageDigest md = MessageDigest.getInstance(signatureType);

        // Passing data to the created MessageDigest Object
        md.update(message.getBytes());

        // Compute the message digest
        byte[] digest = md.digest();
        System.out.println(digest);

        // Converting the byte array in to HexString format
        StringBuffer hexString = new StringBuffer();

        for (int i = 0; i < digest.length; i++) {
            hexString.append(Integer.toHexString(0xFF & digest[i]));
        }
        System.out.println("Hex format : " + hexString.toString());
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pvt);
        byte[] signature = cipher.doFinal(digest);
        for (int i = 0; i < signature.length; i++) {
            hexString.append(Integer.toHexString(0xFF & signature[i]));
        }
        System.out.println("hexString - asssinatura finalizada " + hexString.toString());

        // VALIDAÇÃO DA SIGNATURE
        cipher.init(Cipher.DECRYPT_MODE, pub);

        byte[] digest2 = cipher.doFinal(signature);

        for (int i = 0; i < digest.length; i++) {
            if (digest[i] != digest2[i]) {
                System.out.println("to triste no to feliz");
                break;
            }
        }
        System.out.println("YAAAAAAAAAY porraaaa");
    }
}