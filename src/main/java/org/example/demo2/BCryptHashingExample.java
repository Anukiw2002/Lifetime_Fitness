import org.mindrot.jbcrypt.BCrypt;

import java.util.Scanner;

public class BCryptHashingExample{
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter a passwoord to hash: ");
        String password = scanner.nextLine();

        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

        System.out.println("Hashed password: " + hashedPassword);
    }
}