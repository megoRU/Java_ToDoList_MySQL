import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Base64Class {

  protected static String encrypt(final String secret, final String data) {
    byte[] decodedKey = Base64.getDecoder().decode(secret);
    try {
      Cipher cipher = Cipher.getInstance("AES");
      // rebuild key using SecretKeySpec
      SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
      cipher.init(Cipher.ENCRYPT_MODE, originalKey);
      byte[] cipherText = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
      return Base64.getEncoder().encodeToString(cipherText);
    } catch (Exception e) {
      throw new RuntimeException(
          "Error occured while encrypting data", e);
    }
  }

  protected static String decrypt(final String secret, final String encryptedString) {
    byte[] decodedKey = Base64.getDecoder().decode(secret);
    try {
      Cipher cipher = Cipher.getInstance("AES");
      // rebuild key using SecretKeySpec
      SecretKey originalKey = new SecretKeySpec(Arrays.copyOf(decodedKey, 16), "AES");
      cipher.init(Cipher.DECRYPT_MODE, originalKey);
      byte[] cipherText = cipher.doFinal(Base64.getDecoder().decode(encryptedString));
      return new String(cipherText);
    } catch (Exception e) {
      throw new RuntimeException(
          "Error occured while decrypting data", e);
    }
  }
}