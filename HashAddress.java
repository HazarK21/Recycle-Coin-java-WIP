import java.security.SecureRandom;

public class HashAddress {
    public static String createHash() {
        SecureRandom random = new SecureRandom();
        byte[] hash = new byte[32];
        random.nextBytes(hash);

        StringBuilder sb = new StringBuilder();
        for (byte b : hash) {
            sb.append(String.format("%02x", b));
        }
        String hashAddress = sb.toString();

        //System.out.println("Random hash address: " + hashAddress);
        return hashAddress;
    }
}
