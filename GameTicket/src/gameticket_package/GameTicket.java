/** Research reference
 *Stack over flow how to get difference between two dates */
package gameticket_package;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.InputMismatchException;

public class GameTicket {

    /**
     * @param args Main function
     */
    public static void main(String[] args) {
        /**
         * Array list for registered fans
         */
        ArrayList<fan> f = new ArrayList<>();
        /**
         * Array list for national and international games
         */
        ArrayList<Game> g = new ArrayList<>();
        /**
         * Array list for seats of stadium with different categories
         */
        ArrayList<seats> s = new ArrayList<>();
        seats s1;
        /**
         * Filling out seats array list for game constructor
         */
        for (int i = 0; i < 27000; i++) {
            if (i < 9000) {
                s1 = new seats(i + 1, 200.0, 1);
                s.add(s1);
            } else if (i < 18000 && i >= 9000) {
                s1 = new seats(i + 1, 150.0, 2);
                s.add(s1);
            } else if (i < 27000 && i >= 18000) {
                s1 = new seats(i + 1, 75.0, 3);
                s.add(s1);
            }
        }
        /**
         * Date variable get the date of running code to determine the
         * difference with the match date Date data type add 1900 years to year
         * parameter and 1 month to month parameter
         */
        Date date2 = new Date();
        /**
         * Filling out games array list national and international games
         */
        Date date = new Date(125, 12, 11, 18, 30);
        national_games n_game = new national_games("Ahly", "Zamalek", 1, "Cairo Stadium", date, s);
        g.add(n_game);
        date = new Date(125, 12, 13, 19, 30);
        n_game = new national_games("Wadi degla", "Pyramids", 3, "Borg El arab", date, s);
        g.add(n_game);
        date = new Date(125, 12, 15, 20, 0);
        international_games in_game = new international_games("Egypt", "Brazil", 2, "Maracanã", date, s);
        g.add(in_game);
        date = new Date(125, 12, 17, 21, 0);
        in_game = new international_games("Germany", "Spain", 4, "Signal Iduna Park", date, s);
        g.add(in_game);
        /**
         * Boolean to help break the loop
         */
        boolean flag = false;
        int choice;
        Scanner input = new Scanner(System.in);
        try {
            /**
             * label for continue statement
             */
            here:
            /**
             * While loop to make the program working, until the user closes the
             * program
             */
            while (!flag) {
                /**
                 * printing out available matches, wether national or
                 * international
                 */
                System.out.println("National Games");
                System.out.println("---------------");
                for (int i = 0; i < g.size(); i++) {
                    if (g.get(i).getClass().getSimpleName().equals("national_games")) {
                        long diff1 = (g.get(i).match_date.getTime() - date2.getTime()) / 86400000;
                        if (diff1 > 0) {
                            System.out.printf("Game Code: %d\t%s VS %s\tDate: %tc\tMatch Stadium: %s\n", g.get(i).game_code, g.get(i).first_team, g.get(i).second_team, g.get(i).match_date, g.get(i).stadium);
                        }
                    }
                }
                System.out.println("International Games");
                System.out.println("---------------------");
                for (int i = 0; i < g.size(); i++) {
                    if (g.get(i).getClass().getSimpleName().equals("international_games")) {
                        long diff2 = (g.get(i).match_date.getTime() - date2.getTime()) / 86400000;
                        if (diff2 > 0) {
                            System.out.printf("Game Code: %d\t%s VS %s\tDate: %tc\tMatch Stadium: %s\n", g.get(i).game_code, g.get(i).first_team, g.get(i).second_team, g.get(i).match_date, g.get(i).stadium);
                        }
                    }
                }
                System.out.println("\nFirst category price: 200.0\tSecond category price: 150.0\tThird category price: 75.0");
                /**
                 * showing user some choices
                 */
                System.out.println("\nPress 1 to reserve a ticket.\nPress 2 to make a bet.\nPress 3 to cancel reservation.\nPress 4 to upgrade seat category\nPress 5 to Display data and exit.");
                System.out.print("Enter your choice: ");
                choice = input.nextInt();
                switch (choice) {
                    /**
                     * Reserve a ticket case
                     */
                    case 1: {
                        /**
                         * Boolean to check the seats availability
                         */
                        boolean flag2 = false;
                        boolean flag3 = false;
                        /**
                         * Boolean to check the games availability
                         */
                        boolean flag4 = false;
                        System.out.print("Enter Game code:");
                        int code = input.nextInt();
                        System.out.print("Enter Seat category (1, 2 or 3):");
                        int s_category = input.nextInt();
                        for (int i = 0; i < g.size(); i++) {
                            /**
                             * Checking if game exist or not
                             */
                            if (g.get(i).game_code == code) {
                                flag4 = true;
                                for (int j = 0; j < g.get(i).seat.size(); j++) {
                                    /**
                                     * Check the seats availability
                                     */
                                    if (g.get(i).seat.get(j).category == s_category) {
                                        int pass;
                                        String name;
                                        System.out.print("Enter your Name:");
                                        name = input.next();
                                        System.out.print("Enter your Password:");
                                        pass = input.nextInt();
                                        for (int k = 0; k < f.size(); k++) {
                                            /**
                                             * Check for presence among
                                             * registered fans
                                             */
                                            if (f.get(k).getName().equals(name) && f.get(k).getPassword() == pass) {
                                                f.get(k).reserve_ticket(code, s_category);
                                                flag3 = true;
                                                break;
                                            }
                                        }
                                        if (!flag3) {
                                            fan f_temp = new fan(pass, name);
                                            f_temp.reserve_ticket(code, s_category);
                                            f.add(f_temp);
                                        }
                                        g.get(i).seat.remove(j);
                                        flag2 = true;
                                        break;
                                    }
                                }
                                break;
                            }
                        }
                        if (!flag4) {
                            try {
                                /**
                                 * User defined exception
                                 */
                                throw new myexception("There is no match with this game code.");
                            } catch (myexception ex) {
                                break;
                            }
                        }
                        if (!flag2) {
                            try {
                                /**
                                 * User defined exception
                                 */
                                throw new myexception("There are no seats available in this category.");
                            } catch (myexception ex) {
                                break;
                            }
                        }
                        break;
                    }
                    /**
                     * Make a bet case
                     */
                    case 2: {
                        boolean flag2 = false;
                        System.out.print("Enter Game code:");
                        int code = input.nextInt();
                        for (int i = 0; i < g.size(); i++) {
                            if (g.get(i).game_code == code) {
                                flag2 = true;
                                System.out.printf("Vote with 1 for %s or 2 for %s:", g.get(i).first_team, g.get(i).second_team);
                                int bet = input.nextInt();
                                if (fan.make_bet(bet)) {
                                    System.out.println("You won the bet.");
                                } else {
                                    System.out.println("You lost the bet.");
                                }
                                break;
                            }
                        }
                        if (!flag2) {
                            try {
                                /**
                                 * User defined exception
                                 */
                                throw new myexception("There is no match with this game code.");
                            } catch (myexception ex) {
                            }
                        }
                        break;
                    }
                    /**
                     * Cancel reservation case
                     */
                    case 3: {
                        /**
                         * Boolean to check for presence among registered fans
                         */
                        boolean flag2 = false;
                        boolean flag3 = false;
                        boolean flag4 = false;
                        int pass;
                        String name;
                        System.out.print("Enter your Name:");
                        name = input.next();
                        System.out.print("Enter your Password:");
                        pass = input.nextInt();
                        for (int i = 0; i < f.size(); i++) {
                            if (f.get(i).getName().equals(name) && f.get(i).getPassword() == pass) {
                                flag2 = true;
                                System.out.print("Enter Game code:");
                                int code = input.nextInt();
                                for (int j = 0; j < g.size(); j++) {

                                    if (g.get(j).game_code == code) {
                                        flag3 = true;
                                        for (int k = 0; k < f.get(i).fan_tickets.size(); k++) {
                                            if (f.get(i).fan_tickets.get(k).game_code == code) {
                                                flag4 = true;
                                                break;
                                            }
                                        }
                                        if (!flag4) {
                                            try {
                                                /**
                                                 * User defined exception
                                                 */
                                                throw new myexception("There is no reservation for this match.");
                                            } catch (myexception ex) {
                                                continue here;
                                            }
                                        }
                                        long diff = (g.get(j).match_date.getTime() - date2.getTime()) / 86400000;
                                        if (diff >= 3) {
                                            f.get(i).cancel_reservation(code);
                                            break;
                                        } else {
                                            try {
                                                /**
                                                 * User defined exception
                                                 */
                                                throw new myexception("You are too late.(You must cancel the reservation at least three days before the match)");
                                            } catch (myexception ex) {
                                                continue here;
                                            }
                                        }

                                    }
                                }
                            }
                        }
                        if (!flag2) {
                            try {
                                /**
                                 * User defined exception
                                 */
                                throw new myexception("You are not registered.");
                            } catch (myexception ex) {
                                break;
                            }
                        }
                        if (!flag3) {
                            try {
                                /**
                                 * User defined exception
                                 */
                                throw new myexception("There is no match with this game code.");
                            } catch (myexception ex) {
                                break;
                            }
                        }
                        break;
                    }
                    /**
                     * Upgrade seat category case
                     */
                    case 4: {
                        /**
                         * Boolean to check for presence among registered fans
                         */
                        boolean flag2 = false;
                        boolean flag3 = false;
                        int pass;
                        String name;
                        System.out.print("Enter your Name:");
                        name = input.next();
                        System.out.print("Enter your Password:");
                        pass = input.nextInt();
                        for (int i = 0; i < f.size(); i++) {
                            if (f.get(i).getName().equals(name) && f.get(i).getPassword() == pass) {
                                flag2 = true;
                                System.out.print("Enter Game code:");
                                int code = input.nextInt();
                                for (int j = 0; j < g.size(); j++) {
                                    if (g.get(j).game_code == code) {
                                        flag3 = true;
                                        break;
                                    }
                                }
                                if (!flag3) {
                                    System.out.println("There is no match with this game code.");
                                    continue here;
                                }
                                f.get(i).upgrade_SeatCategory(code);
                                break;
                            }
                        }
                        if (!flag2) {
                            try {
                                /**
                                 * User defined exception
                                 */
                                throw new myexception("You are not registered.");
                            } catch (myexception ex) {
                            }
                        }
                        break;
                    }
                    /**
                     * Exit program and display data of fans and their tickets
                     */
                    case 5: {
                        for (int i = 0; i < f.size(); i++) {
                            System.out.printf("Fan name: %s\n", f.get(i).getName());
                            if (f.get(i).fan_tickets.size() != 0) {
                                System.out.println("Tickets:");
                                System.out.println("---------");
                                for (int j = 0; j < f.get(i).fan_tickets.size(); j++) {

                                    System.out.printf("Game Code: %d\tSeat category: %d\tPrice: %.1f\n", f.get(i).fan_tickets.get(j).game_code, f.get(i).fan_tickets.get(j).seat_category, f.get(i).fan_tickets.get(j).price);
                                }
                            } else {
                                try {
                                    /**
                                     * User defined exception
                                     */
                                    throw new myexception("there are no tickets.");
                                } catch (myexception ex) {
                                }
                            }
                        }
                        flag = true;
                        break;
                    }
                    default: {
                        System.out.println("Please enter a valid choice.");
                    }
                }
            }
        } /**
         * Java defined exception
         */
        catch (NumberFormatException n) {
            System.out.println("You must enter numbers.");
        } /**
         * Java defined exception
         */
        catch (InputMismatchException ime) {
            System.out.println("Please enter valid input.");
        }
    }
}

/**
 * Abstract class for games
 */
abstract class Game {

    protected String first_team;
    protected String second_team;
    protected int game_code;
    protected String stadium;
    protected Date match_date;
    protected static ArrayList<seats> seat;

    /**
     * Parameterized constructor
     */
    public Game(String first_team, String second_team, int game_code, String stadium, Date match_date, ArrayList<seats> seat) {
        this.first_team = first_team;
        this.second_team = second_team;
        this.game_code = game_code;
        this.match_date = match_date;
        this.stadium = stadium;
        this.seat = seat;
    }

}

/**
 * Class for national games inherited from class game
 */
class national_games extends Game {

    /**
     * @param first_team
     * @param second_team
     * @param game_code
     * @param stadium
     * @param match_date
     * @param seat Calling super class constructor in his subclass parameterized
     * constructor
     */
    public national_games(String first_team, String second_team, int game_code, String stadium, Date match_date, ArrayList<seats> seat) {
        super(first_team, second_team, game_code, stadium, match_date, seat);
    }
}

/**
 * Class for international games inherited from class game
 */
class international_games extends Game {

    /**
     * @param first_team
     * @param second_team
     * @param game_code
     * @param stadium
     * @param match_date
     * @param seat Calling super class constructor in his subclass parameterized
     * constructor
     */
    public international_games(String first_team, String second_team, int game_code, String stadium, Date match_date, ArrayList<seats> seat) {
        super(first_team, second_team, game_code, stadium, match_date, seat);
    }
}

/**
 * Class for seats
 */
class seats {

    public int seat_number, category;
    /**
     * final data member
     */
    public final double price;

    /**
     * @param seat_number
     * @param price
     * @param category Parameterized constructor
     */
    public seats(int seat_number, final double price, int category) {
        this.seat_number = seat_number;
        this.price = price;
        this.category = category;
    }
}

/**
 * Class for tickets
 */
class ticket {

    protected int game_code, seat_category;
    protected double price;

    /**
     * @param game_code
     * @param seat_category
     * @param price Parameterized constructor
     */
    public ticket(int game_code, int seat_category, final double price) {
        this.game_code = game_code;
        this.seat_category = seat_category;
        this.price = price;
    }
}

/**
 * Class fan implement interface reservation
 */
class fan implements reservation {

    private int password;
    private String name;
    /**
     * array list for fan's tickets
     */
    protected ArrayList<ticket> fan_tickets;

    /**
     * Implementation of encapsulation with getter and setter for private data
     * members
     */
    public void setPassword(int password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    /**
     * @param password
     * @param name Parameterized constructor and making instance from fan's
     * ticket array list
     */
    public fan(int password, String name) {
        this.password = password;
        this.name = name;
        fan_tickets = new ArrayList<>();
    }

    /**
     * @param g_code
     * @param s_category Method for reserving tickets
     */
    public void reserve_ticket(int g_code, int s_category) {
        boolean flag = false;
        double pr = 0.0;
        /**
         * Determine seat price depending on its category
         */
        if (s_category == 1) {
            pr = 200.0;
        } else if (s_category == 2) {
            pr = 150.0;
        } else if (s_category == 3) {
            pr = 75.0;
        }
        for (int i = 0; i < fan_tickets.size(); i++) {
            if (fan_tickets.get(i).game_code == g_code) {
                flag = true;
                break;
            }
        }
        if (flag) {
            try {
                /**
                 * User defined exception
                 */
                throw new myexception("It is not allowed to reserve more than one ticket for the match.");
            } catch (myexception ex) {
            }
        } else {
            ticket t = new ticket(g_code, s_category, pr);
            fan_tickets.add(t);
            System.out.println("Your ticket was successfully booked");
        }
    }

    /**
     * @param g_code Method for canceling reservation
     */
    final public void cancel_reservation(int g_code) {
        for (int i = 0; i < fan_tickets.size(); i++) {
            if (fan_tickets.get(i).game_code == g_code) {
                fan_tickets.remove(i);
                System.out.println("Your reservation was successfully canceled.");
                break;
            }
        }
    }

    /**
     * @param g_code Method for upgrading categories
     */
    final public void upgrade_SeatCategory(int g_code) {
        /**
         * Boolean to make sure there is reservation for match or not
         */
        boolean flag = false;
        int ch;
        ticket t;
        Scanner input = new Scanner(System.in);
        for (int i = 0; i < fan_tickets.size(); i++) {
            if (fan_tickets.get(i).game_code == g_code) {
                t = fan_tickets.get(i);
                /**
                 * Calculated data member
                 */
                if (fan_tickets.get(i).seat_category == 3) {
                    System.out.println("Enter 1 for first category or 2 for second category:");
                    ch = input.nextInt();
                    if (ch == 1) {
                        t.seat_category -= 2;
                        t.price = 200.0;
                        System.out.println("Your seat has been upgraded to the first category.");
                    } else if (ch == 2) {
                        t.seat_category--;
                        t.price = 150.0;
                        System.out.println("Your seat has been upgraded to the second category.");
                    } else {
                        System.out.println("Please enter a valid choice");
                    }
                    fan_tickets.set(i, t);
                } else if (fan_tickets.get(i).seat_category == 2) {
                    t.seat_category--;
                    t.price = 200.0;
                    fan_tickets.set(i, t);
                    System.out.println("Your seat has been upgraded to the first category.");
                } else if (fan_tickets.get(i).seat_category == 1) {
                    try {
                        /**
                         * User defined exception
                         */
                        throw new myexception("You booked the highest category.");
                    } catch (myexception ex) {
                    }
                }
                flag = true;
                break;
            }

        }
        if (!flag) {
            try {
                /**
                 * User defined exception
                 */
                throw new myexception("There is no reservation for this match.");
            } catch (myexception ex) {
            }
        }
    }

    /**
     * @param num
     * @return Static method for making a bet
     */
    static public boolean make_bet(int num) {
        /**
         * Using random class
         */
        Random r = new Random();
        int rand = r.nextInt(3);
        /**
         * Check wether fan lose or win the bet
         */
        if (num == rand) {
            return true;
        } else {
            return false;
        }
    }
}

/**
 * Interface for reservation
 */
interface reservation {

    /**
     * @param g_code
     * @param s_category Abstract method for reserving tickets implemented in
     * class fan
     */
    public void reserve_ticket(int g_code, int s_category);
}

/**
 * User defined exception class inherited from Exception class
 */
class myexception extends Exception {

    /**
     * @param msg Calling super class constructor in his subclass parameterized
     * constructor
     */
    public myexception(String msg) {
        super(msg);
        System.out.println(msg);
    }
}