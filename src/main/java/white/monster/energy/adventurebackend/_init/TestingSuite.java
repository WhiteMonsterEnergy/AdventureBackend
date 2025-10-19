package white.monster.energy.adventurebackend._init;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class TestingSuite
{
    private static final Random rand = new Random();
    private static final String dateTimeFormat = "h:m d M yyyy";
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateTimeFormat);
    private static final File namesTXT = new File("./src/main/java/white/monster/energy/adventurebackend/_init/TestingNames.txt");

    static void setSeed(Object seed) {rand.setSeed(seed.hashCode());}
    static void resetSeed()
    {
        long o = ((long)new Object().hashCode()) << 32 | new Object().hashCode();
        long seed = System.nanoTime() ^ o;
        for (int i = 0; i < 3; i++)
        {
            seed ^= seed >>> rand.nextInt(4,32);
            seed ^= seed <<  rand.nextInt(4,32);
        }
        rand.setSeed(seed);
    }

    static void ascii() // print ASCII table to console, to sort out localization
    {
        for (int i = 26; i < 256; i++)
        {
            System.out.print(i + "\t" + (char)i + " | ");
            if ((i-25) % 10 == 0) System.out.println();
        }
    }

    static int     getInt   ()                       {return rand.nextInt();}
    static int     getInt   (int bound)              {return rand.nextInt(bound);}
    static int     getInt   (int min, int max)       {return rand.nextInt(min, max);}
    static double  getDouble()                       {return rand.nextDouble();}
    static double  getDouble(double bound)           {return rand.nextDouble(bound);}
    static double  getDouble(double min, double max) {return rand.nextDouble(min, max);}
    static boolean chance   (double percent)         {return percent > rand.nextDouble()*100;}

    static <T> T oneOf(List<T> objects)
    {
        if (objects.isEmpty()) return null;
        return objects.get(rand.nextInt(objects.size()));
    }

    static String getPhoneNumber() {return rand.nextInt(89999999)+10000000+"";}

    static LocalDateTime getTime(){return getTime(LocalDateTime.now(),LocalDateTime.now().plusYears(1));}
    static LocalDateTime getTime(LocalDateTime earliest, LocalDateTime latest)
    {
        long span = earliest.until(latest, ChronoUnit.NANOS);
        return earliest.plusNanos(rand.nextLong(span));
    }

    static LocalDate getDay(){return getDay(LocalDate.now(),LocalDate.now().plusYears(1));}
    static LocalDate getDay(LocalDate earliest, LocalDate latest)
    {
        long span = earliest.until(latest, ChronoUnit.DAYS);
        return earliest.plusDays(rand.nextLong(span));
    }

    // returns and removes a random name from stored 'list'
    static String getName()
    {
        if (nameList.isEmpty()) {resetNames();}
        return nameList.remove(rand.nextInt(nameList.size()));
    }

    // loads original list of names into 'list'.
    private static void resetNames()
    {
        Scanner scanner;
        try
        {
            scanner = new Scanner(namesTXT);
        }
        catch (FileNotFoundException e)
        {
            throw new RuntimeException(e);
        }

        while (scanner.hasNextLine())
        {
            nameList.add(scanner.nextLine());
        }

        scanner.close();

        addLastNames();
    }

    // adds a random last name initial to each name stored in 'list'
    // also duplicates random first names, while ensuring different last names.
    private static void addLastNames()
    {
        ArrayList<String> newNames = new ArrayList<>();
        StringBuilder last = new StringBuilder();
        String a;

        for (String name : nameList)
        {
            for (int i = 0; i < 5; i++)
            {
                a = "" + (char)('A' + rand.nextInt(12) + rand.nextInt(12));
                if (!last.toString().contains(a))
                {
                    last.append(a);
                }
            }

            for (char b : last.toString().toCharArray())
            {
                newNames.add(name + " " + b);
            }
        }

        nameList.clear();
        nameList.addAll(newNames);
    }

    private static final ArrayList<String> nameList = new ArrayList<>();
}
