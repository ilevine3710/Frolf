import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class Test {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        ArrayList<Round> rounds = readFiles();
        rounds = removePartial(rounds);
        boolean quit = false;
        boolean course = false;
        boolean player = false;
        boolean hole = false;
        do {
            try {
                System.out.println("\nChoose an option: ");
                System.out.println("Sort rounds: 1");
                if (!course) {
                    System.out.println("See certain course: 2");
                } if (!player) {
                    System.out.println("See certain player: 3");
                } if (!hole) {
                    System.out.println("See certain hole: 4");
                }
                System.out.println("View Rounds: 5");
                System.out.println("Quit: 6\n");
                int choice = scan.nextInt();
                scan.nextLine();
                switch (choice) {
                    case (1):
                        System.out.println("\nWhat would you like to sort by?");
                        System.out.println("Sort by Date: 'Date'");
                        if (!course) {
                            System.out.println("Sort by Course: 'Course'");
                        }
                        System.out.println("Sort by Score: 'Score'");
                        if (!player) {
                            System.out.println("Sort by Player Name: 'Name'\n");
                        }
                        sort(scan.next(), rounds);
                        break;
                    case (2):
                        System.out.println("What course would like to view? (South Mountain),(Hanover Community Center), (Covered Bridge Park), (Camp Olympic Park)");
                        String courseName = scan.nextLine();
                        rounds = specificCourse(rounds,courseName);
                        course = true;
                        break;
                    case (3):
                        System.out.println("What player would like to view? (Isaac Levine),(Mark Latvakoski), (Isaac Hanish), (Jeremy Cohen), (Ed Crabbe)");
                        String playerName = scan.nextLine();
                        rounds = specificPlayer(rounds,playerName);
                        player = true;
                        break;
                    case (5):
                        printRounds(rounds);
                        break;
                    case (6):
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (Exception e){
                System.out.println("Invalid choice");
            }
        } while (!quit);
        
    } 
    public static ArrayList<Round> readFiles () {
        ArrayList<Round> rounds;
        try {
            File f = new File("Rounds.txt");
            Scanner scan = new Scanner(f);
            String [] file = new String [100];
            int length = 0;
            while (scan.hasNextLine()) { 
                file [length] = scan.nextLine();
                length++;
            }
            rounds = new ArrayList<>();
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
                rounds.add(round);
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
    public static void sort (String name, ArrayList<Round> array) {
        switch (name) {
            case ("Date"):
                for (int i = 0; i < array.size(); i++) {
                    Round min = array.get(i);
                    int index = i;
                    for (int j = i + 1; j < array.size(); j++) {
                        if (array.get(j).getDate().compareTo(min.getDate()) < 0) { // swap < to sort reverse
                            min = array.get(j);
                            index = j;
                        }
                    } Round temp = array.get(index);
                    array.set(index,array.get(i));
                    array.set(i, temp);
                } break;
            case ("Name"):
                for (int i = 0; i < array.size(); i++) {
                    Round min = array.get(i);
                    int index = i;
                    for (int j = i + 1; j < array.size(); j++) {
                        if (array.get(j).getPlayer().compareTo(min.getPlayer()) < 0) { // swap < to sort reverse
                            min = array.get(j);
                            index = j;
                        }
                    } Round temp = array.get(index);
                    array.set(index,array.get(i));
                    array.set(i, temp);
                } break;
            case ("Course"):
                for (int i = 0; i < array.size(); i++) {
                    Round min = array.get(i);
                    int index = i;
                    for (int j = i + 1; j < array.size(); j++) {
                        if (array.get(j).getCourseName().compareTo(min.getCourseName()) < 0) { // swap < to sort reverse
                            min = array.get(j);
                            index = j;
                        }
                    } Round temp = array.get(index);
                    array.set(index,array.get(i));
                    array.set(i, temp);
                } break;
            case ("Score"):
                for (int i = 0; i < array.size(); i++) {
                    Round min = array.get(i);
                    int index = i;
                    for (int j = i + 1; j < array.size(); j++) {
                        if (array.get(j).getFinalScore() < min.getFinalScore()) { // swap < to sort reverse
                            min = array.get(j);
                            index = j;
                        }
                    } Round temp = array.get(index);
                    array.set(index,array.get(i));
                    array.set(i, temp);
                } break;
            default:
                System.out.println("Unable to sort by " + name);
                break;
        }
    }
    public static void printRounds(ArrayList<Round>  rounds) {
        System.out.println("\t\t\t\t\t\t\t\t\t\tHole Number:");
        System.out.print(String.format("%-15s%-20s%-30s%-15s", "Date", "Player", "Course","Total Score"));
        for (int i = 1; i < 19; i++) {
            System.out.print(String.format("%-4d", i));
        } System.out.println();
        for (Round i: rounds) {
            System.out.println(i);
        }
    } 
    public static ArrayList<Round> removePartial (ArrayList<Round>  rounds) {
        ArrayList<Round> removed = new ArrayList<>();
        boolean partial = false;
        for (int i = 0; i < rounds.size(); i++) {
            for (int j = 0; j < 18; j++) {
                if (rounds.get(i).getHoles()[j].getScore() == 0) {
                    partial = true;
                }
            } if (!partial) {
                removed.add(rounds.get(i));
            } partial = false;
        } 
        return removed;
    }
    public static ArrayList<Round> specificCourse (ArrayList<Round>  totalRounds, String courseName) {
        ArrayList<Round> newRounds = new ArrayList<Round> ();
        for (int i = 0; i < totalRounds.size(); i++) {
            if (totalRounds.get(i).getCourseName().equals(courseName)) {
                System.out.println("test");
                newRounds.add(totalRounds.get(i));
            }
        } return newRounds;
    }
    public static ArrayList<Round> specificPlayer (ArrayList<Round>  totalRounds, String playerName) {
        ArrayList<Round> newRounds = new ArrayList<Round> ();
        for (int i = 0; i < totalRounds.size(); i++) {
            System.out.println(playerName);
            System.out.println(totalRounds.get(i).getPlayer().equals(playerName));
            if (totalRounds.get(i).getPlayer().equals(playerName)) {
                newRounds.add(totalRounds.get(i));
            }
        } return newRounds;
    } 
    
}
