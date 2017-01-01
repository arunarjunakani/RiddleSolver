import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by arun on 12/31/16.
 */
public class RiddleSolver {

    public static String[][][] data;
    public static int houseNumber;
    /**
     * This is the main method where all of the solving will be happening
     * @param String[]
     * @return void
     */
    public static void main(String[] args) {
        data = new String[5][5][];
        houseNumber = -1;
        for(int i = 0; i < 5; i++) //Initializes the array
        {
            for(int j = 0; j < 5; j++)
            {
                switch(i)
                {
                    case 0:
                        data[i][j] = new String[]{"B", "G", "R", "W", "Y"};
                        break;
                    case 1:
                        data[i][j] = new String[]{"B", "D", "G", "N", "S"};
                        break;
                    case 2:
                        data[i][j] = new String[]{"Ble", "Blu", "D", "Pa", "Pr"};
                        break;
                    case 3:
                        data[i][j] = new String[]{"B", "C", "M", "T", "W"};
                        break;
                    case 4:
                        data[i][j] = new String[]{"B", "C", "D", "F", "H"};
                        break;
                }
            }
        }

        while (true)
        {
            data[4][0] = new String[]{"F"};
            data[2][2] = new String[]{"Pa"};
            displayData();

            readRules();

            eliminateOptions();

            //Checks to see if the game was won
            if(gameWon())
            {
                displayData();
                System.out.println("You have cracked the case! The fish is in House " + houseNumber + "!");
                break;
            }
        }
    }

    private static void displayData()
    {
        for (String[][] row: data)
        {
            for(String[] cell: row)
            {
                System.out.print(Arrays.toString(cell));
            }
            System.out.println();
        }
    }

    /**
     * This method will read the rules and see if any definitive options can removed
     * @param null
     * @return void
     */
    private static void readRules()
    {

    }

    /**
     * This method will read the current data array and see if any cells can be definitively proven
     * @param null
     * @return void
     */
    private static void eliminateOptions()
    {
        for(String[][] row : data)
        {
            for(String[] cell: row)
            {
                if(cell.length == 1)
                {
                    String value = cell[0];
                    cell[0] = "1" + value; //All finalized ones will
                    for(int i = 0; i < 5; i++)
                    {
                        ArrayList<String> options = new ArrayList<>(Arrays.asList(row[i]));
                        if(options.contains(value))
                        {
                            options.remove(value);
                        }
                        String[] arr = new String[options.size()];
                        arr = options.toArray(arr);
                        row[i] = arr;
                    }
                }
            }
        }
    }

    /**
     * This method will check to see if the fish has been found
     * @param null
     * @return boolean
     */
    private static boolean gameWon()
    {
        for(int i = 0; i < 5; i++)
        {
            if(data[4][i][0].equals("1F"))
            {
                houseNumber = i;
                return true;
            }
        }
        return false;
    }
}
