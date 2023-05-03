import java.io.*;

public class UserFinder {
    public static User findUserByHash(String hashInput) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                String username = userData[0];
                String password = userData[1];
                String userHash = userData[2];
                int userBalance = Integer.parseInt(userData[3]);
                if (userHash.equals(hashInput)) {
                    return new User(username, password, userHash, userBalance);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static User findUserByUsername(String usernameInput) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                String username = userData[0];
                String password = userData[1];
                String userHash = userData[2];
                int userBalance = Integer.parseInt(userData[3]);
                if (username.equals(usernameInput)) {
                    return new User(username, password, userHash, userBalance);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static boolean setUserBalance(String userHash, int newBalance) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt", true))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] userData = line.split(",");
                String username = userData[0];
                String password = userData[1];
                String hashInput = userData[2];
                int userBalance = Integer.parseInt(userData[3]);
                if (hashInput.equals(userHash)) {
                    String newLine = String.format("%s,%s,%s,%s", username, password, hashInput, newBalance+userBalance);
                    bw.write(newLine);
                    removeBeforeLine(newLine);
                    bw.newLine();
                    return true;
                } else {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean removeBeforeLine(String lineToKeep) {
        try (BufferedReader br = new BufferedReader(new FileReader("users.txt"));
             BufferedWriter bw = new BufferedWriter(new FileWriter("users.txt"))) {
            String line;
            boolean lineFound = false;
            while ((line = br.readLine()) != null) {
                if (line.equals(lineToKeep)) {
                    lineFound = true;
                }
                if (lineFound) {
                    bw.write(line);
                    bw.newLine();
                }
            }
            return lineFound;
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}

class User {
    private String username;
    private String password;
    private String hashAddress;
    private int balance;

    public User(String username, String password, String hashAddressInput, int balanceInput) {
        this.username = username;
        this.password = password;
        this.hashAddress = hashAddressInput;
        this.balance = balanceInput;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getHashAddress() {
        return hashAddress;
    }

    public int getUserBalance() {
        return balance;
    }

    public void setUserBalance(int balanceInput) {
        this.balance += balanceInput;
    }
}
