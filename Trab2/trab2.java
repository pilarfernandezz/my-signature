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
        int typed = sc.nextInt();
        String pattern = null;

        switch (typed) {
            case 1:
                pattern = "MD5";
                break;
            case 2:
                pattern = "SHA-1";
                break;
            case 3:
                pattern = "SHA-256";
                break;
            case 4:
                pattern = "SHA-512";
                break;
            default:
                System.out.println("Padrão de assinatura inválido.");
                System.exit(1);
        }

        MySignature.getInstance(pattern);
        MySignature.initSign(message);
        MySignature.update(message);
        MySignature.sign();
        MySignature.initVerify();
        MySignature.verify();
    }
}