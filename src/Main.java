import java.util.Scanner;
import java.util.Random;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Välkommen!");

        // Input antalet spelare
        System.out.print("Ange antal spelare: ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consuming newline

        // Skapa arrayer för att lagra spelarnas namn och poäng
        String[] playerNames = new String[numPlayers];
        int[] playerScores = new int[numPlayers];

        // Input spelarnas namn och sätt deras poäng till 30
        for (int i = 0; i < numPlayers; i++) {
            System.out.print("Ange namn för spelare " + (i + 1) + ": ");
            playerNames[i] = scanner.nextLine();
            playerScores[i] = 30;
        }

        // Spelets huvudloop
        while (countPlayersWithPoints(playerScores) > 1) {
            for (int i = 0; i < numPlayers; i++) {
                String playerName = playerNames[i];
                System.out.println("\nDet är " + playerName + "'s tur.");
                int points = playerScores[i];

                // Rulla tärningarna
                int[] diceValues = rollDice(6);

                // Visa resultatet av tärningarna
                System.out.println("Du rullade: " + Arrays.toString(diceValues));

                // Input vilka tärningar som ska sparas
                int[] keptDiceValues = selectDiceToKeep(scanner, diceValues);

                // Om spelaren vill rulla om alla tärningar
                if (keptDiceValues.length == 0) {
                    diceValues = rollDice(6);
                    System.out.println("Du rullade: " + Arrays.toString(diceValues));
                    keptDiceValues = selectDiceToKeep(scanner, diceValues);
                }

                // Summera sparade tärningar
                int sum = calculateScore(keptDiceValues);

                // Justera poängen baserat på resultatet
                if (sum >= 30) {
                    // Inget poängavdrag om summan är 30 eller mer
                } else {
                    // Only deduct points if the sum is less than 30
                    playerScores[i] -= (30 - sum);
                }

                // Visa spelarens nya poäng
                System.out.println("Dina poäng är nu: " + playerScores[i]);

                // Om poängen når 0, ta bort spelaren
                if (playerScores[i] <= 0) {
                    playerScores[i] = 0;
                    System.out.println(playerName + " är ute ur spelet!");
                }
            }
        }

        // Visa vinnaren
        for (int i = 0; i < numPlayers; i++) {
            if (playerScores[i] > 0) {
                System.out.println("\nGrattis, " + playerNames[i] + " vann spelet!");
                break;
            }
        }
        scanner.close();
    }

    // Metod för att rulla tärningar och returnera resultaten
    public static int[] rollDice(int numDice) {
        Random rand = new Random();
        int[] diceValues = new int[numDice];
        for (int i = 0; i < numDice; i++) {
            diceValues[i] = rand.nextInt(6) + 1; // Tärningsslag mellan 1 och 6
        }
        return diceValues;
    }

    // Metod för att låta spelaren välja vilka tärningar som ska sparas
    public static int[] selectDiceToKeep(Scanner scanner, int[] diceValues) {
        while (true) {
            System.out.println("Vilka tärningar vill du spara? Ange index (0-6), separerade med mellanslag. (ex: 0 2 4)");
            String input = scanner.nextLine();
            String[] indices = input.split(" ");
            if (indices.length == 0) {
                return new int[0]; // Om spelaren inte sparar något
            }
            int[] keptDiceValues = new int[indices.length];
            boolean validInput = true;
            for (int i = 0; i < indices.length; i++) {
                int index = Integer.parseInt(indices[i]);
                if (index < 0 || index >= diceValues.length) {
                    System.out.println("Ogiltigt index: " + index);
                    validInput = false;
                    break;
                }
                keptDiceValues[i] = diceValues[index];
            }
            if (validInput) {
                return keptDiceValues;
            }
        }
    }


    // Metod för att summera tärningarna
    public static int calculateScore(int[] diceValues) {
        int sum = 0;
        for (int value : diceValues) {
            sum += value;
        }
        return sum;
    }

    // Metod för att räkna antalet spelare med poäng kvar
    public static int countPlayersWithPoints(int[] playerScores) {
        int count = 0;
        for (int score : playerScores) {
            if (score > 0) {
                count++;
            }
        }
        return count;
    }
}

