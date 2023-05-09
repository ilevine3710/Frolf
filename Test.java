import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Test {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Round [] rounds = readFiles();
        rounds = removePartial(rounds);
        boolean quit = false;
        do {
            printRounds(rounds);
            System.out.println("Sort by: Name, Date, Course, Score (or Quit)");
            String entry = scan.next();
            quit = sort(entry,rounds);
        } while (!quit);
        
    } 
    public static Round [] readFiles () {
        Round [] rounds;
        try {
            File f = new File("Rounds.txt");
            Scanner scan = new Scanner(f);
            String [] file = new String [100];
            int length = 0;
            while (scan.hasNextLine()) { 
                file [length] = scan.nextLine();
                length++;
            }
            rounds = new Round [length];
            for (int i = 0; i < length; i ++) {
                Round round = new Round();
                String [] s = file[i].split(",");
                round.setDate(s[0]);
                round.setPlayer(s[1]);
                round.setCourseName(s[2]);
                for (int j = 3; j < 21; j++) {
                    round.addScore(j - 3, Integer.valueOf(s[j]));
                }
                round.getTotalScoreString();
                rounds[i] = round;
            } scan.close();
            return rounds;
        } catch (FileNotFoundException e){
            System.out.println("File not found");
        } catch (InvalidDateTimeException e) {
            System.out.println(e.getMessage());
        } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
        } return null;
    }
    public static boolean sort (String name, Round [] array) {
        switch (name) {
            case ("Date"):
                for (int i = 0; i < array.length; i++) {
                    Round min = array[i];
                    int index = i;
                    for (int j = i + 1; j < array.length; j++) {
                        if (array [j].getDate().compareTo(min.getDate()) < 0) { // swap < to sort reverse
                            min = array[j];
                            index = j;
                        }
                    } Round temp = array [index];
                    array [index] = array [i];
                    array [i] = temp;
                } return false;
            case ("Name"):
                for (int i = 0; i < array.length; i++) {
                    Round min = array[i];
                    int index = i;
                    for (int j = i + 1; j < array.length; j++) {
                        if (array [j].getPlayer().compareTo(min.getPlayer()) < 0) { // swap < to sort reverse
                            min = array[j];
                            index = j;
                        }
                    } Round temp = array [index];
                    array [index] = array [i];
                    array [i] = temp;
                } return false;
            case ("Course"):
                for (int i = 0; i < array.length; i++) {
                    Round min = array[i];
                    int index = i;
                    for (int j = i + 1; j < array.length; j++) {
                        if (array [j].getCourseName().compareTo(min.getCourseName()) < 0) { // swap < to sort reverse
                            min = array[j];
                            index = j;
                        }
                    } Round temp = array [index];
                    array [index] = array [i];
                    array [i] = temp;
                } return false;
            case ("Score"):
                for (int i = 0; i < array.length; i++) {
                    Round min = array[i];
                    int index = i;
                    for (int j = i + 1; j < array.length; j++) {
                        if (array [j].getFinalScore() < min.getFinalScore()) { // swap < to sort reverse
                            min = array[j];
                            index = j;
                        }
                    } Round temp = array [index];
                    array [index] = array [i];
                    array [i] = temp;
                } return false;
            case ("Quit"):
                return true;
            default:
                System.out.println("Unable to sort by " + name);
                return false;
        }
    }
    public static void printRounds(Round [] rounds) {
        System.out.println("\t\t\t\t\t\t\t\t\tHole Number:");
        System.out.print(String.format("%-15s%-15s%-30s%-15s", "Date", "Player", "Course","Total Score"));
        for (int i = 1; i < 19; i++) {
            System.out.print(String.format("%-4d", i));
        } System.out.println();
        for (Round i: rounds) {
            System.out.println(i);
        }
    } public static Round [] removePartial (Round [] rounds) {
        ArrayList<Round> removed = new ArrayList<>();
        boolean partial = false;
        for (int i = 0; i < rounds.length; i++) {
            for (int j = 0; j < 18; j++) {
                if (rounds[i].getHoles()[j].getScore() == 0) {
                    partial = true;
                }
            } if (!partial) {
                removed.add(rounds[i]);
            } partial = false;
        } 
        Round [] newRound = new Round [removed.size()];
        for (int i = 0; i < removed.size(); i++) {
            newRound [i] = removed.get(i);
        } 
        return newRound;
    }
}
