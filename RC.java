import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Scanner;

public class RC {
    private String username;
    private String password;
    private String hashAddress;
    private int balance;

    public static int petSiseVal = 50;
    public static int birBucukPetSiseVal = 150;
    public static int kolaKutusuVal = 330;
    public static int gazeteVal = 25;

    public RC(String username, String password) {
        this.username = username;
        this.password = password;
        this.hashAddress = HashAddress.createHash();
        this.balance = 0;
    }

    public static void register(String username, String password, String hashAddress, int balanceVal) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            bw.write(username + "," + password + "," + hashAddress + "," + balanceVal);
            bw.newLine();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean login(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                if (userData[0].equals(username) && userData[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void seeValues() {
        System.out.println("0,5lt pet şişe : 50 carbon");
        System.out.println("1,5lt pet şişe : 150 carbon");
        System.out.println("0,33lt gazlı içecek kutusu : 330 carbon");
        System.out.println("1 gazete : 25 carbon");
    }

    public static void depositRecycle() {
        Scanner scn = new Scanner(System.in);
        String hashAddress = null;
        System.out.println("Please enter your hash: ");
        hashAddress = scn.nextLine();
        User user = UserFinder.findUserByHash(hashAddress);
        if(user != null) {
            System.out.println("1. 0,5lt pet şişe");
            System.out.println("2. 1,5lt pet şişe");
            System.out.println("3. 0,33lt gazlı içecek kutusu");
            System.out.println("4. 1 gazete");
            System.out.println("Your option: ");
            int option = scn.nextInt();
            if(option == 1) {
                boolean balanceSet = UserFinder.setUserBalance(hashAddress, petSiseVal);
                if(balanceSet) {
                    user.setUserBalance(petSiseVal);
                    System.out.println("User " + user.getUsername() + "'s new balance: " + user.getUserBalance());
                } else {
                    System.out.println("Balance error !!");
                }
            } else if(option == 2) {
                boolean balanceSet = UserFinder.setUserBalance(hashAddress, birBucukPetSiseVal);
                if(balanceSet) {
                    user.setUserBalance(birBucukPetSiseVal);
                    System.out.println("User " + user.getUsername() + "'s new balance: " + user.getUserBalance());
                } else {
                    System.out.println("Balance error !!");
                }
            } else if(option == 3) {
                boolean balanceSet = UserFinder.setUserBalance(hashAddress, kolaKutusuVal);
                if(balanceSet) {
                    user.setUserBalance(kolaKutusuVal);
                    System.out.println("User " + user.getUsername() + "'s new balance: " + user.getUserBalance());
                } else {
                    System.out.println("Balance error !!");
                }
            } else if(option == 4) {
                boolean balanceSet = UserFinder.setUserBalance(hashAddress, gazeteVal);
                if(balanceSet) {
                    user.setUserBalance(gazeteVal);
                    System.out.println("User " + user.getUsername() + "'s new balance: " + user.getUserBalance());
                } else {
                    System.out.println("Balance error !!");
                }
            }
        } else {
            System.out.println("User not found.");
        }
    }

    public static void accountDetails(String username) {
        User user = UserFinder.findUserByUsername(username);
        if(user != null) {
            System.out.println("Account Statistics :");
            System.out.println("Hash: " + user.getHashAddress());
            System.out.println("Carbon Balance: " + user.getUserBalance());
            System.out.printf("Converted Carbon Balance: %.8f RC\n", ((float)user.getUserBalance()) / 100000000);
            System.out.println("----------------------------");
        } else {
            System.out.println("User not found !");
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputUsername = null;
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Enter option: ");
            int option = scanner.nextInt();

            if (option == 1) {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                String hashAddress = HashAddress.createHash();
                RC.register(username, password, hashAddress, 0);
                System.out.println("Registration successful.");
            } else if (option == 2) {
                System.out.print("Enter username: ");
                String username = scanner.next();
                System.out.print("Enter password: ");
                String password = scanner.next();
                boolean success = RC.login(username, password);
                if (success) {
                    System.out.println("Login successful.");
                    inputUsername = username;
                    scanner.nextLine();
                    while(true) {
                        System.out.println("1. See recycle item carbon values");
                        System.out.println("2. Deposit recycle product");
                        System.out.println("3. See account balance");
                        System.out.println("4. Exit");
                        System.out.print("Enter option: ");
                        int optionAlt = scanner.nextInt();
                        if(optionAlt == 1) {
                            seeValues();
                        } else if(optionAlt == 2) {
                            depositRecycle();
                        } else if(optionAlt == 3) {
                            accountDetails(inputUsername);
                        } else {
                            break;
                        }
                    }
                } else {
                    System.out.println("Invalid username or password.");
                }
            } else if (option == 3) {
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
        scanner.close();
    }
}

