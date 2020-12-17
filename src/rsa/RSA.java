
package rsa;

import java.math.BigInteger;
import java.util.Random;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author kennelra
 */
public class RSA extends Application {
    public static int bitLength = 1024;
    // Prime number checker
    public static boolean isPrime(int p) {
        // Check if the number is negative
        // We also know that 0 and 1 are not prime, so we include them
        // in the check here
        if (p <= 1) {
            return false;
        }
        // Check if number is 2 or 3
        if (p == 2) {
            return true;
        }
        if (p == 3) {
            return true;
        }
        // Otherwise, all prime numbers greater than 3 can be written in the
        // form 6n+1 or 6n-1, so we'll use that for the check
        // First, check if p can be put in the form 6n+1
        int checkValue = (6 * p) + 1;
        int LHS = p - 1;
        checkValue = LHS % 6;
        if (checkValue == 0) {
            return true;
        }
        // Check if p can be put in the form 6n-1
        checkValue = (6 * p) - 1;
        LHS = p + 1;
        checkValue = LHS % 6;
             
        if (checkValue == 0) {
            return true;
        }
        // If it can't, then it's not prime
        return false;
    }
    
    // Take in the user input x & check validity
    public static boolean inputValidator(String x) {
        // Check if integers
        try {
            int intX = Integer.parseInt(x);
        } catch (NumberFormatException nfe) {
            System.out.println("Number format exception error.");
            return false;
        }
        return true;
    }
    
    // Use the user's input integer to generate a large prime number for p or q
    // via Fermat's Little Theorem: a^(n-1) % n = 1
    public static BigInteger pqGenerator(int a) {
        long aLong = (long) a;
        // Seed the random generator with the user's input
        Random r = new Random(aLong);
        
        // Use the random number to generate a probable prime with 
        BigInteger bigA = BigInteger.probablePrime(bitLength, r);
 
        return bigA;
    }
    
    public static String[] getKeys(BigInteger p, BigInteger q) {
        // the modulus, n = pq
        BigInteger n = p.multiply(q);
        String nString = n.toString();

        BigInteger pMinus1 = p.subtract(BigInteger.ONE);
        BigInteger qMinus1 = q.subtract(BigInteger.ONE);
        
        // Calculate the totient --> phi = (p-1)(q-1)
        BigInteger phi = pMinus1.multiply(qMinus1);
        
        // e must be relatively prime to phi and 1 < e < phi
        Random r = new Random();
        BigInteger e = BigInteger.probablePrime(bitLength/2, r);

        // while the gcd of phi & e is greater than 1 AND 
        // e is less than phi
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && (e.compareTo(phi) < 0)) {
            e.add(BigInteger.ONE);
        }
        String eString = e.toString();
        
        // The private decryption key d is the inverse of 
        // e mod phi. 1 < d < phi
        BigInteger d = e.modInverse(phi);
        
        String dString = d.toString();
        
        String keys[] = {nString, eString, dString};
        
        return keys;
    }; 
    
    
    public static String encryptMessage(String nString, String eString, String message) {
        // Conver n and e to integers
        
        BigInteger n = new BigInteger(nString);
        BigInteger e = new BigInteger(eString);
        
        // Convert ASCII string into BigInteger of bytes
        BigInteger m = new BigInteger(message.getBytes());
        // encrypted message = m^e mod n
        BigInteger encryptedMessageInt = m.modPow(e,n);
        // return encrypted message string
        String encryptedMessage = encryptedMessageInt.toString();
        
        return encryptedMessage;
    }
    
    public static String decryptMessage(String nString, String dString, String message) {
        // Convert n, d, and message to big integers
        BigInteger n = new BigInteger(nString);
        BigInteger d = new BigInteger(dString);
        BigInteger encMessage = new BigInteger(message);
        
        // Decrypted message = encMessage ^ d % n
        BigInteger decMessageInt = encMessage.modPow(d, n);
        
        // return decrypted message String
        String decryptedMessage = new String(decMessageInt.toByteArray());
        
        return decryptedMessage;
    }
  

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("RSAFXML.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("RSA Encryption Key Generator");
        stage.setScene(scene);
        stage.show();
    }
    
    // main ----------------------------------
    public static void main(String[] args) {
        launch(args);
    } // end main
    
}
