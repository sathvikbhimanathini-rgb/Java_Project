import java.util.*;

interface Bookable {
    void book() throws Exception;
}

interface Printable {
    void print();
}

// CO4 - Abstraction
abstract class Movie {
     String name;
     double price;

    Movie(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
            return name;
          }
    public double getPrice()
      {
         return price;
      }

    abstract void display();
}

class CinemaMovie extends Movie {
    CinemaMovie(String name, double price) {
        super(name, price);
    }

    void display() {
        System.out.println(getName() + " - Rs." + getPrice());
    }
}

// CO4 - Multiple interfaces
class Booking implements Bookable, Printable {
    String customer, movie;
    int tickets;
    double total;

    Booking(String c, String m, int t, double amount) {
        customer = c;
        movie = m;
        tickets = t;
        total = amount;
    }

    public void book() throws Exception {
        if (tickets <= 0) {
            throw new Exception("Invalid tickets!");
            } 
        System.out.println("Booking Confirmed!");
    }

    public void print() {
        System.out.println("Customer: " + customer);
        System.out.println("Movie: " + movie);
        System.out.println("Tickets: " + tickets);
        System.out.println("Total: Rs." + total);
    }
}

public class CinemaTicketBookingSystem {
    static final int ROWS = 3, COLS = 5;
    static boolean[][] seats = new boolean[ROWS][COLS];

    // CO6 - Two collections
    static ArrayList<Movie> movies = new ArrayList<>();
    static HashSet<String> customers = new HashSet<>();

    // CO3 - Method
    static void displaySeats() {
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++)
                System.out.print(seats[i][j] ? "X " : "O ");
            System.out.println();
        }
    }

    // CO3 - Method overloading
    static double calculatePrice(int t, double p) {
        return t * p;
    }

    static double calculatePrice(int t, double p, double d) {
        return t * p - d;
    }

    static void selectSeats(Scanner sc, int tickets) {
        int count = 0;

        while (count < tickets) {
            displaySeats();
            System.out.print("Enter row and seat: ");
            int r = sc.nextInt(), c = sc.nextInt();

            if (r < 1 || r > ROWS || c < 1 || c > COLS)
                System.out.println("Invalid seat!");
            else if (seats[r - 1][c - 1])
                System.out.println("Already booked!");
            else {
                seats[r - 1][c - 1] = true;
                count++;
            }
        }
    }

    // CO5 - String methods
    static void stringDemo(String name) {
        name = name.trim();
        System.out.println("Length: " + name.length());
        System.out.println("Uppercase: " + name.toUpperCase());
        System.out.println("First character: " + name.charAt(0));
        System.out.println("Replace: " + name.replace("a", "@"));
        System.out.println("Words: " + name.split(" ").length);

        String a = new String(name), b = new String(name);
        System.out.println("== : " + (a == b));
        System.out.println("equals(): " + a.equals(b));

        StringTokenizer st = new StringTokenizer(name);
        while (st.hasMoreTokens())
            System.out.print(st.nextToken() + " ");
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        try {
            System.out.print("Customer name: ");
            String customer = sc.nextLine().trim();

            if (customer.isEmpty())
                throw new Exception("Name cannot be empty!");

            stringDemo(customer);
            customers.add(customer);

            movies.add(new CinemaMovie("RomCom", 200));
            movies.add(new CinemaMovie("Action", 250));
            movies.add(new CinemaMovie("Comedy", 180));

            for (int i = 0; i < movies.size(); i++) {
                System.out.print((i + 1) + ". ");
                movies.get(i).display();
            }

            System.out.print("Choose movie (1-3): ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1: case 2: case 3:
                    break;
                default:
                    throw new Exception("Invalid movie choice!");
            }

            Movie m = movies.get(choice - 1);

            System.out.print("Number of tickets: ");
            int tickets = sc.nextInt();

            if (tickets < 1 || tickets > ROWS * COLS)
                throw new Exception("Invalid ticket count!");

            selectSeats(sc, tickets);

            double total = calculatePrice(tickets, m.getPrice());
            System.out.println("Total: Rs." + total);

            System.out.print("Confirm booking (1=Yes, 0=No): ");
            int confirm = sc.nextInt();

            if (confirm == 1) {
                Booking b = new Booking(customer, m.getName(), tickets, total);
                b.book();
                b.print();
            } else {
                System.out.println("Booking cancelled.");
            }

        } catch (InputMismatchException e) {
            System.out.println("Enter valid input!");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            System.out.println("Thank you for using KLH Cinema!");
            sc.close();
        }

        System.out.println("Unique customers: " + customers);
    }
}

