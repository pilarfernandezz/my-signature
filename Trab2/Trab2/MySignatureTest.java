public class MySignatureTest {
    public static void main(String args[]) throws Exception {
        if (args == null || args.length < 2) {
            System.out.println("Por favor envie a mensagem e o padrão de assinatura pela linha de comando");
            System.exit(1);
        }

        String message = args[0];
        String pattern = args[1];

        switch (pattern) {
            case "MD5withRSA":
                pattern = "MD5";
                break;
            case "SHA1withRSA":
                pattern = "SHA-1";
                break;
            case "SHA256withRSA":
                pattern = "SHA-256";
                break;
            case "SHA512withRSA":
                pattern = "SHA-512";
                break;
            default:
                System.out.println("Padrão de assinatura inválido.");
                System.exit(1);
        }

        MySignature.getInstance(pattern);
        MySignature.initSign(message);
        MySignature.update(message);
        byte[] signature = MySignature.sign();
        MySignature.initVerify();
        MySignature.verify(message, signature);
    }
}