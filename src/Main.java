import java.io.*;
import java.net.URL;

public class Main {
    public static void main(String[] args) {
        final String FILE_URL = "https://ewib.nbp.pl/plewibnra?dokNazwa=plewibnra.txt";

        try (BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in))) {
            System.out.print("Proszę wpisać 3,4 oraz 5 cyfrę nr konta: ");
            String accountNumber = userInput.readLine().trim();

            if (accountNumber.matches("\\d{3}") && findBank(FILE_URL, accountNumber)) {
                return;
            }
            System.out.println("Nie znaleziono banku dla podanych cyfr.");
        } catch (IOException e) {
            System.err.println("Błąd: " + e.getMessage());
        }
    }

    private static boolean findBank(String fileUrl, String accountNumber) {
        try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(new URL(fileUrl).openStream()))) {
            return fileReader.lines()
                    .map(line -> line.split("\t", 3))
                    .filter(parts -> parts.length >= 3 && parts[0].trim().equals(accountNumber))
                    .peek(parts -> System.out.println("Twój bank: " + parts[0].trim() + "\t" + parts[1].trim()))
                    .findFirst()
                    .isPresent();
        } catch (IOException e) {
            System.err.println("Błąd podczas odczytu pliku: " + e.getMessage());
            return false;
        }
    }
}
