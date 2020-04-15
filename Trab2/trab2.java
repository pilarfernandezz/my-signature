import java.security.*;
import java.util.Scanner;

public class trab2 {
    public static void main(String args[]) throws Exception {
        // Reading data from user
        Scanner sc = new Scanner(System.in);
        System.out.println("Digite a mensagem");
        String message = sc.nextLine();
        System.out.println(
                "Digite o padrão de assinatura:\n 1 - MD5withRSA\n 2 - SHA1withRSA\n 3 - SHA256withRSA\n 4 - SHA512withRSA");
        int signatureTyped = sc.nextInt();
        String signature = null;

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");

        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();

        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();

        System.out.println(pub);
        System.out.println(pvt);

        switch (signatureTyped) {
            case 1:
                signature = "MD5";
                break;
            case 2:
                signature = "SHA-1";
                break;
            case 3:
                signature = "SHA-256";
                break;
            case 4:
                signature = "SHA-512";
                break;
            default:
                System.out.println("Padrão de assinatura inválido.");
                System.exit(1);
        }

        // Creating the MessageDigest object
        MessageDigest md = MessageDigest.getInstance(signature);

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
    }
}