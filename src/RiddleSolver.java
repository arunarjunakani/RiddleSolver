import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Arun on 12/31/16.
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
                        data[i][j] = new String[]{"B", "C", "D", "U", "P"};
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
            displayData();
            //readRules();

            eliminateOptions();

            //Checks to see if the game was won
            if(gameWon())
            {
                displayData();
                System.out.println("You have cracked the case! The fish is in House " + houseNumber + "!");
                break;
            }
            displayData();
            System.out.println("Press enter to continue.");
            new Scanner(System.in).nextLine();
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
        System.out.println();
    }

    /**
     * This method will read the rules and see if any definitive options can removed
     * @param null
     * @return void
     */
    private static void readRules()
    {
        String[] clues = {"1B:0R", "1S:4D", "1D:3T", "0GL0W", "0G:3C", "2U:4B",
                "0Y:2D", "3ME22", "1NE00", "2BA4C", "4HA2D", "2C:3B", "1G:2P", "1NA0B", "2BN3W"};
        for(String s: clues)
        {
            int indexOne = Integer.parseInt(s.substring(0, 1));
            String valueOne = s.substring(1, 2);
            int indexTwo = Integer.parseInt(s.substring(3, 4));
            String valueTwo = s.substring(4, 5);

            switch(s.charAt(2))
            {
                case ':': //This is binding between 2 properties
                    for(int i = 0; i < 5; i++)
                    {
                        if(!optionExists(indexOne, i, valueOne) && optionExists(indexTwo, i, valueTwo))
                        {
                            removeOptionFromCell(indexTwo, i, valueTwo);
                        }

                        if(!optionExists(indexTwo, i, valueTwo) && optionExists(indexOne, i, valueOne))
                        {
                            removeOptionFromCell(indexOne, i, valueOne);
                        }

                        if((optionExists(indexOne, i, valueOne) && data[indexOne][ i].length == 1) && data[indexTwo][i].length != 1)
                        {
                            data[indexTwo][i] = new String[]{valueTwo};
                        }

                        if((optionExists(indexOne, i, "!" + valueOne) && data[indexOne][ i].length == 1) && data[indexTwo][i].length != 1)
                        {
                            data[indexTwo][i] = new String[]{valueTwo};
                        }

                        if((optionExists(indexTwo, i, valueTwo) && data[indexTwo][ i].length == 1) && data[indexOne][i].length != 1)
                        {
                            data[indexOne][i] = new String[]{valueOne};
                        }

                        if((optionExists(indexTwo, i, "!" + valueTwo) && data[indexTwo][ i].length == 1) && data[indexOne][i].length != 1)
                        {
                            data[indexOne][i] = new String[]{valueOne};
                        }
                    }
                    break;
                case 'L': //This means left of
                    removeOptionFromCell(indexOne, 4, valueOne);
                    removeOptionFromCell(indexTwo, 0, valueTwo);
                    for(int i = 0; i < 5; i++)
                    {
                        if(i < 4 && !optionExists(indexOne, i, valueOne) && optionExists(indexTwo, i + 1, valueTwo))
                        {
                            removeOptionFromCell(indexTwo, i + 1, valueTwo);
                        }

                        if(i > 0 && !optionExists(indexTwo, i, valueTwo) && optionExists(indexOne, i - 1, valueOne))
                        {
                            removeOptionFromCell(indexOne, i - 1, valueOne);
                        }

                        if(i < 4 && (optionExists(indexOne, i, valueOne) && data[indexOne][ i].length == 1) && data[indexTwo][i + 1].length != 1)
                        {
                            data[indexTwo][i + 1] = new String[]{valueTwo};
                        }

                        if(i < 4 && (optionExists(indexOne, i, "!" + valueOne) && data[indexOne][ i].length == 1) && data[indexTwo][i + 1].length != 1)
                        {
                            data[indexTwo][i + 1] = new String[]{valueTwo};
                        }

                        if(i > 0 &&(optionExists(indexTwo, i, valueTwo) && data[indexTwo][ i].length == 1) && data[indexOne][i - 1].length != 1)
                        {
                            data[indexOne][i - 1] = new String[]{valueOne};
                        }

                        if(i > 0 &&(optionExists(indexTwo, i, "!" + valueTwo) && data[indexTwo][ i].length == 1) && data[indexOne][i - 1].length != 1)
                        {
                            data[indexOne][i - 1] = new String[]{valueOne};
                        }
                    }
                    break;
                case 'E': //This tells exactly which house a property belongs to
                    data[indexOne][indexTwo] = new String[]{valueOne};
                    break;
                case 'A': //This means the two properties are adjacent
                    break;
            }
            eliminateOptions();
        }
    }

    /**
     * This method will read the current data array and see if any cells can be definitively proven
     * @param null
     * @return void
     */
    private static void eliminateOptions()
    {
        for(int i = 0; i < 5; i++)
        {
            for(String[] cell: data[i])
            {
                if(cell.length == 1 && cell[0].length() == 1)
                {
                    String value = cell[0];
                    cell[0] = "!" + value; //All finalized cells will have an exclamation point to indicate they are finished
                    for(int j = 0; j < 5; j++)
                    {
                        removeOptionFromCell(i, j, value);
                    }
                    displayData();
                }
            }
        }

        int[][] optionQuantities = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        for(int i = 0; i < 5; i++)
        {
            if(optionExists(0, i, "B"))
            {

            }
        }
    }

    private static boolean optionExists(int i, int j, String value)
    {
        ArrayList<String> options = new ArrayList<>(Arrays.asList(data[i][j]));
        return options.contains(value);
    }

    private static void removeOptionFromCell(int i, int j, String value)
    {
        ArrayList<String> options = new ArrayList<>(Arrays.asList(data[i][j]));
        if(options.contains(value))
        {
            options.remove(value);
        }
        String[] arr = new String[options.size()];
        arr = options.toArray(arr);
        data[i][j] = arr;
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
            if(data[4][i][0].equals("!F"))
            {
                houseNumber = i;
                return true;
            }
        }
        return false;
    }
}
