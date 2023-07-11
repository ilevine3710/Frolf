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
        do {
            try {
                System.out.println("\nChoose an option: ");
                System.out.println("Sort rounds: 1");
                if (!course) {
                    System.out.println("See certain course: 2");
                } if (!player) {
                    System.out.println("See certain player: 3");
                }
                    System.out.println("See certain hole: 4");
                
                System.out.println("View Rounds: 5");
                System.out.println("Breakdown: 6");
                System.out.println("Reset Rounds: 7");
                System.out.println("Quit: 8\n");
                int choice = 0;
                try {
                    choice = scan.nextInt();
                } catch (Exception e){
                    String junk = scan.next();
                } scan.nextLine();
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
                    case (4):
                        System.out.println("Enter a hole number (1-18)");
                        try {
                            int hole = scan.nextInt();
                            if (hole < 1 || hole > 18) {
                                throw new Exception();
                            }
                            printRounds(rounds,hole);

                        } catch (Exception e){
                            System.out.println("Invalid entry");
                        }
                        break;
                        
                    case (5):
                        printRounds(rounds);
                        break;
                    case (6):
                        rounds = readFiles();
                        rounds = removePartial(rounds);
                        player = false;
                        course = false;
                        System.out.println("What player would like to view? (Isaac Levine),(Mark Latvakoski), (Isaac Hanish), (Jeremy Cohen), (Ed Crabbe), (All Players)");
                        playerName = scan.nextLine();
                        if (!playerName.equals("All Players")) {
                            rounds = specificPlayer(rounds,playerName);
                        }
                        System.out.println("What course would like to view? (South Mountain),(Hanover Community Center), (Covered Bridge Park), (Camp Olympic Park)");
                        courseName = scan.nextLine();
                        rounds = specificCourse(rounds,courseName);

                        double [] std = getSTDArray(rounds);
                        double [] averages = getAverageArray(rounds);
                        ArrayList<Integer> values = new ArrayList<>();
                        double value = 0;

                        System.out.println(playerName + "\'s Breakdown at " + courseName + ":\n");
                        value = averages[0];
                        values.add(0);
                        for (int i = 1; i < 18; i++) {
                            if (value == averages[i]) {
                                values.add(i);
                            } else if (value > averages[i]) {
                                value = averages[i];
                                values.clear();
                                values.add(i);
                            }
                        }
                        System.out.print("Best Hole(s):");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print("\t" + (values.get(i) + 1));
                        }
                        System.out.println("\nAverage:\t" + String.format("%.2f",averages[values.get(0)]));

                        value = averages[0];
                        values.clear();
                        values.add(0);
                        for (int i = 1; i < 18; i++) {
                            if (value == averages[i]) {
                                values.add(i);
                            } else if (value < averages[i]) {
                                value = averages[i];
                                values.clear();
                                values.add(i);
                            }
                        }
                        System.out.print("\nWorst Hole(s):");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print("\t" + (values.get(i) + 1));
                        }
                        System.out.println("\nAverage:\t" + String.format("%.2f",averages[values.get(0)]));

                        value = std[0];
                        values.clear();
                        values.add(0);
                        for (int i = 1; i < 18; i++) {
                            if (value == std[i]) {
                                values.add(i);
                            } else if (value > std[i]) {
                                value = std[i];
                                values.clear();
                                values.add(i);
                            }
                        }
                        System.out.print("\nMost Consistent Hole(s):");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print("\t" + (values.get(i) + 1));
                        }
                        System.out.print(String.format("%-32s","\nAverage(s):"));
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print(String.format("%-8.1f",averages[values.get(i)]));
                        }
                        System.out.println(String.format("\n%-31s%.1f","Standard Deviation:",std[values.get(0)]));

                        value = std[0];
                        values.clear();
                        values.add(0);
                        for (int i = 1; i < 18; i++) {
                            if (value == std[i]) {
                                values.add(i);
                            } else if (value < std[i]) {
                                value = std[i];
                                values.clear();
                                values.add(i);
                            }
                        }
                        System.out.print("\nLeast Consistent Hole(s):");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print("\t" + (values.get(i) + 1));
                        }
                        System.out.print(String.format("%-32s","\nAverage(s):"));
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print(String.format("%-8.1f",averages[values.get(i)]));
                        }
                        System.out.println(String.format("\n%-31s%.1f","Standard Deviation:",std[values.get(0)]));

                        value = 0;
                        String score = "";
                        for (int i = 0; i < rounds.size(); i++) {
                            value += rounds.get(i).getFinalScore();
                        } value /=  rounds.size();
                        if (value > 0) {
                            score = "+" + String.format("%.2f",value); 
                        } else if (value == 0) {
                            score = "E";
                        } else {
                            score = String.format("%.2f",value);
                        }
                        System.out.println("\nAverage Total Score:\t" + score);

                        int [] birdies = {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0};
                        for (int i = 0; i < rounds.size(); i++) {
                            for (int j = 0; j < 18; j++) {
                                if (rounds.get(i).getPar(j) - rounds.get(i).getScore(j) > 0) {
                                    birdies [j] += 1;
                                }
                            }
                        }
                        value = birdies[0];
                        values.clear();
                        for (int i = 1; i < 18; i++) {
                            if (value == birdies[i]) {
                                values.add(i);
                            } else if (value < birdies[i]) {
                                value = birdies[i];
                                values.clear();
                                values.add(i);
                            }
                        }
                        System.out.print("\nMost Commonly Birdied Hole(s): ");
                        for (int i = 0; i < values.size(); i++) {
                            System.out.print("\t" + (values.get(i) + 1));
                        }
                        System.out.println("\nNumber of Birdies:\t\t" + (int)value);

                        System.out.print("\nHoles never Birdied: ");
                        for (int i = 0; i < 18; i++) {
                            if (birdies[i] == 0) {
                                System.out.print((i + 1) + "  ");
                                
                            }
                        } System.out.println();
                       

                        sort("Score", rounds);
                        printRounds(rounds);
                        rounds = readFiles();
                        rounds = removePartial(rounds);
                        break;
                    case (7):
                        rounds = readFiles();
                        rounds = removePartial(rounds);
                        player = false;
                        course = false;
                        break;
                    case (8):
                        quit = true;
                        break;
                    default:
                        System.out.println("Invalid choice");
                        break;
                }
            } catch (Exception e){
                System.out.println("??");
            }
        } while (!quit);
        scan.close();
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
        System.out.println("\t\t\t\t\t\t\t\t\tHole Number:");
        System.out.print(String.format("%-12s%-18s%-30s%-15s", "Date", "Player", "Course","Total Score"));
        for (int i = 1; i < 19; i++) {
            System.out.print(String.format("%-6d", i));
        } System.out.println();
        for (Round i: rounds) {
            System.out.println(i);
        }
        System.out.println(averageScores(rounds));
    } 
    public static void printRounds(ArrayList<Round>  rounds, int hole) {
        System.out.println("\t\t\t\t\t\t\t\t\tHole Number:");
        System.out.print(String.format("%-12s%-18s%-30s%-15s", "Date", "Player", "Course","Total Score"));
        System.out.println(String.format("%-6d", hole));
        for (Round i: rounds) {
            System.out.println(i.toString(hole));
        } System.out.println(averageScores(rounds,hole));
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
                newRounds.add(totalRounds.get(i));
            }
        } return newRounds;
    }
    public static ArrayList<Round> specificPlayer (ArrayList<Round>  totalRounds, String playerName) {
        ArrayList<Round> newRounds = new ArrayList<Round> ();
        for (int i = 0; i < totalRounds.size(); i++) {
            if (totalRounds.get(i).getPlayer().equals(playerName)) {
                newRounds.add(totalRounds.get(i));
            }
        } return newRounds;
    } 
    public static String averageScores (ArrayList<Round>  rounds) {
        String s = "\t\t\t\t\t\t\t\t\t  ";
        double total = 0;
        for (int i = 0; i < 18; i++) {
            for (int j = 0; j < rounds.size(); j++) {
                total += rounds.get(j).getScore(i);
            } total /= (rounds.size() * 1.0);
            s += String.format("%-5.1f ", total);
            total = 0;
        }
        return s;
    } 
    public static String averageScores (ArrayList<Round>  rounds, int hole) {
        String s = "\t\t\t\t\t\t\t\t\t  ";
        double total = 0;
        for (int j = 0; j < rounds.size(); j++) {
            total += rounds.get(j).getHoles()[hole - 1].getScore();
        } total /= (rounds.size() * 1.0);
        s += String.format("%-5.1f ", total);
        
        return s;
    }
    public static double [] getAverageArray (ArrayList<Round>  rounds)  {
        double [] averages = new double [18];
        for (int i = 0; i < rounds.size(); i++) {
            for (int j = 0; j < 18; j++) {
                averages [j] += rounds.get(i).getScore(j);
            }
        }
        for (int i = 0; i < 18; i++) {
            averages [i] /= rounds.size();
        }
        return averages;
    }
    public static double [] getSTDArray (ArrayList<Round>  rounds)  {
        double [] std = new double [18];
        double [] junk = new double [rounds.size()];
        for (int i = 0; i < std.length; i++) {
            for (int j = 0; j < rounds.size(); j++) {
                junk [j] = rounds.get(j).getScore(i);
            } 
            std [i] = STD(junk);
        }
        return std;
    }
    public static double STD (double [] std) {
        double sum = 0.0, standardDeviation = 0.0;
        int length = std.length;
        for(double num : std) {
            sum += num;
        }
        double mean = sum / length;
        for(double num: std) {
            standardDeviation += Math.pow(num - mean, 2);
        }
        return Math.sqrt(standardDeviation/length);
    }
}