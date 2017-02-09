import javax.swing.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Arun on 12/31/16.
 */
public class RiddleSolver {

    public static String[][][] data;
    public static int answer;
    public static String[] clues;
    /**
     * This is the main method where all of the solving will be happening
     * @param String[]
     * @return void
     */
    public static void main(String[] args) {
        //data = new String[5][5][];
        answer = -1;

        /*int numClues = Integer.parseInt(JOptionPane.showInputDialog("How many clues are there?"));
        clues = new String[numClues];
        for (int i = 0; i < numClues; i++) {
            String clue = JOptionPane.showInputDialog("Enter Clue " + (i+1) + ":");
            clues[i] = clue;
        }*/

        int numOptions = Integer.parseInt(JOptionPane.showInputDialog("How many options are there?"));
        int numProperties = Integer.parseInt(JOptionPane.showInputDialog("How many properties are there?"));
        String[][] options = new String[numProperties][numOptions];
        for (int i = 0; i < numProperties; i++) {
            for (int j = 0; j < numOptions; j++) {
                String option = JOptionPane.showInputDialog("Enter Option " + (j+1) + " of Property " + (i+1) + ": ");
                options[i][j] = option;
                System.out.println(option);
            }
        }

        data = new String[numProperties][numOptions][];

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

        displayData();

        while (true)
        {
            readRules();

            eliminateOptions();

            //Checks to see if the game was won
            if(gameWon())
            {
                displayData();
                System.out.println("You have cracked the case! The fish is in House " + (answer + 1) + "!");
                break;
            }

            displayData();
            System.out.println("Press enter to continue.");
            new Scanner(System.in).nextLine();
        }
    }

    private static void displayData()
    {
        fixingSyntax();
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
        clues = new String[]{"1B:0R", "1S:4D", "1D:3T", "0GL0W", "0G:3C", "2U:4B",
                "0Y:2D", "3ME22", "1NE00", "2BA4C", "4HA2D", "2C:3B", "1G:2P", "1NA0B", "2BA3W"};
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
                        if(i < 4 && !optionExists(indexOne, i, valueOne) && !optionExists(indexOne, i, "!" + valueOne) && optionExists(indexTwo, i + 1, valueTwo))
                        {
                            removeOptionFromCell(indexTwo, i + 1, valueTwo);
                        }

                        if(i > 0 && !optionExists(indexTwo, i, valueTwo) && !optionExists(indexTwo, i, "!" + valueTwo) && optionExists(indexOne, i - 1, valueOne))
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
                    if(optionExists(indexOne, 0, valueOne) && data[indexOne][0].length == 1)
                    {
                        data[indexTwo][1] = new String[]{valueTwo};
                    }

                    if(optionExists(indexOne, 0, "!" + valueOne))
                    {
                        data[indexTwo][1] = new String[]{valueTwo};
                    }

                    if(optionExists(indexOne, 4, valueOne) && data[indexOne][4].length == 1)
                    {
                        data[indexTwo][3] = new String[]{valueTwo};
                    }

                    if(optionExists(indexOne, 4, "!" + valueOne))
                    {
                        data[indexTwo][3] = new String[]{valueTwo};
                    }

                    if(optionExists(indexTwo, 0, valueTwo) && data[indexTwo][0].length == 1)
                    {
                        data[indexOne][1] = new String[]{valueOne};
                    }

                    if(optionExists(indexTwo, 0, "!" + valueTwo))
                    {
                        data[indexOne][1] = new String[]{valueOne};
                    }

                    if(optionExists(indexTwo, 4, valueTwo) && data[indexTwo][4].length == 1)
                    {
                        data[indexOne][3] = new String[]{valueOne};
                    }

                    if(optionExists(indexTwo, 4, "!" + valueTwo))
                    {
                        data[indexOne][3] = new String[]{valueOne};
                    }

                    for (int i = 1; i < 4; i++) {
                        if(optionExists(indexOne, i, valueOne))
                        {
                            if(!(optionExists(indexTwo, i - 1, valueTwo)) && !(optionExists(indexTwo, i + 1, valueTwo)) && !(optionExists(indexTwo, i - 1, "!" + valueTwo)) && !(optionExists(indexTwo, i + 1, "!" + valueTwo)))
                            {
                                removeOptionFromCell(indexOne, i, valueOne);
                            }
                        }

                        if(optionExists(indexTwo, i, valueTwo))
                        {
                            if(!(optionExists(indexOne, i - 1, valueOne)) && !(optionExists(indexOne, i + 1, valueOne)) && !(optionExists(indexOne, i - 1, "!" + valueOne)) && !(optionExists(indexOne, i + 1, "!" + valueOne)))
                            {
                                removeOptionFromCell(indexTwo, i, valueTwo);
                            }
                        }


                        if(optionExists(indexOne, i, "!" + valueOne))
                        {
                            if(!(optionExists(indexTwo, i - 1, valueTwo)) && (optionExists(indexTwo, i + 1, valueTwo)))
                            {
                                data[indexTwo][i + 1] = new String[]{valueTwo};
                            }

                            if((optionExists(indexTwo, i - 1, valueTwo)) && !(optionExists(indexTwo, i + 1, valueTwo)))
                            {
                                data[indexTwo][i - 1] = new String[]{valueTwo};
                            }
                        }

                        if(optionExists(indexTwo, i, "!" + valueTwo))
                        {
                            if(!(optionExists(indexOne, i - 1, valueOne)) && (optionExists(indexOne, i + 1, valueOne)))
                            {
                                data[indexOne][i + 1] = new String[]{valueOne};
                            }

                            if((optionExists(indexOne, i - 1, valueOne)) && !(optionExists(indexOne, i + 1, valueOne)))
                            {
                                data[indexOne][i - 1] = new String[]{valueOne};
                            }
                        }
                    }

                    break;
            }
        }
    }

    /**
     * This makes sure that one cell arrays have exclamation marks
     * @param null
     * @return void
     */
    private static void fixingSyntax()
    {
        for(String[][] row : data)
        {
            for(String[] cell: row)
            {
                if(cell.length == 1)
                {
                    if(cell[0].charAt(0) != '!')
                    {
                        cell[0] = "!" + cell[0];
                    }
                }
            }
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
                }
            }
        }

        int[][] optionQuantities = {{0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}, {0, 0, 0, 0, 0}};
        for(int i = 0; i < 5; i++)
        {
            if(optionExists(0, i, "B"))
            {
                optionQuantities[0][0]++;
            }

            if(optionExists(0, i, "G"))
            {
                optionQuantities[0][1]++;
            }

            if(optionExists(0, i, "R"))
            {
                optionQuantities[0][2]++;
            }

            if(optionExists(0, i, "W"))
            {
                optionQuantities[0][3]++;
            }

            if(optionExists(0, i, "Y"))
            {
                optionQuantities[0][4]++;
            }

            if(optionExists(1, i, "B"))
            {
                optionQuantities[1][0]++;
            }

            if(optionExists(1, i, "D"))
            {
                optionQuantities[1][1]++;
            }

            if(optionExists(1, i, "G"))
            {
                optionQuantities[1][2]++;
            }

            if(optionExists(1, i, "N"))
            {
                optionQuantities[1][3]++;
            }

            if(optionExists(1, i, "S"))
            {
                optionQuantities[1][4]++;
            }

            if(optionExists(2, i, "B"))
            {
                optionQuantities[2][0]++;
            }

            if(optionExists(2, i, "C"))
            {
                optionQuantities[2][1]++;
            }

            if(optionExists(2, i, "D"))
            {
                optionQuantities[2][2]++;
            }

            if(optionExists(2, i, "U"))
            {
                optionQuantities[2][3]++;
            }

            if(optionExists(2, i, "P"))
            {
                optionQuantities[2][4]++;
            }

            if(optionExists(3, i, "B"))
            {
                optionQuantities[3][0]++;
            }

            if(optionExists(3, i, "C"))
            {
                optionQuantities[3][1]++;
            }

            if(optionExists(3, i, "M"))
            {
                optionQuantities[3][2]++;
            }

            if(optionExists(3, i, "T"))
            {
                optionQuantities[3][3]++;
            }

            if(optionExists(3, i, "W"))
            {
                optionQuantities[3][4]++;
            }

            if(optionExists(4, i, "B"))
            {
                optionQuantities[4][0]++;
            }

            if(optionExists(4, i, "C"))
            {
                optionQuantities[4][1]++;
            }

            if(optionExists(4, i, "D"))
            {
                optionQuantities[4][2]++;
            }

            if(optionExists(4, i, "F"))
            {
                optionQuantities[4][3]++;
            }

            if(optionExists(4, i, "H"))
            {
                optionQuantities[4][4]++;
            }
        }

        if(optionQuantities[0][0] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "B"))
                {
                    data[0][i] = new String[]{"B"};
                }
            }
        }

        if(optionQuantities[0][1] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "G"))
                {
                    data[0][i] = new String[]{"G"};
                }
            }
        }

        if(optionQuantities[0][2] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "R"))
                {
                    data[0][i] = new String[]{"R"};
                }
            }
        }

        if(optionQuantities[0][3] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "W"))
                {
                    data[0][i] = new String[]{"W"};
                }
            }
        }

        if(optionQuantities[0][4] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "Y"))
                {
                    data[0][i] = new String[]{"Y"};
                }
            }
        }

        if(optionQuantities[1][0] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "B"))
                {
                    data[0][i] = new String[]{"B"};
                }
            }
        }

        if(optionQuantities[1][1] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "D"))
                {
                    data[0][i] = new String[]{"D"};
                }
            }
        }

        if(optionQuantities[1][2] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "G"))
                {
                    data[0][i] = new String[]{"G"};
                }
            }
        }

        if(optionQuantities[1][3] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "N"))
                {
                    data[0][i] = new String[]{"N"};
                }
            }
        }

        if(optionQuantities[1][4] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "S"))
                {
                    data[0][i] = new String[]{"S"};
                }
            }
        }

        if(optionQuantities[2][0] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "B"))
                {
                    data[0][i] = new String[]{"B"};
                }
            }
        }

        if(optionQuantities[2][1] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "C"))
                {
                    data[0][i] = new String[]{"C"};
                }
            }
        }

        if(optionQuantities[2][2] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "D"))
                {
                    data[0][i] = new String[]{"D"};
                }
            }
        }

        if(optionQuantities[2][3] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "U"))
                {
                    data[0][i] = new String[]{"U"};
                }
            }
        }

        if(optionQuantities[2][4] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "P"))
                {
                    data[0][i] = new String[]{"P"};
                }
            }
        }

        if(optionQuantities[3][0] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "B"))
                {
                    data[0][i] = new String[]{"B"};
                }
            }
        }

        if(optionQuantities[3][1] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "C"))
                {
                    data[0][i] = new String[]{"C"};
                }
            }
        }

        if(optionQuantities[3][2] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "M"))
                {
                    data[0][i] = new String[]{"M"};
                }
            }
        }

        if(optionQuantities[3][3] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "T"))
                {
                    data[0][i] = new String[]{"T"};
                }
            }
        }

        if(optionQuantities[3][4] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "W"))
                {
                    data[0][i] = new String[]{"W"};
                }
            }
        }

        if(optionQuantities[4][0] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "B"))
                {
                    data[0][i] = new String[]{"B"};
                }
            }
        }

        if(optionQuantities[4][1] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "C"))
                {
                    data[0][i] = new String[]{"C"};
                }
            }
        }

        if(optionQuantities[4][2] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "D"))
                {
                    data[0][i] = new String[]{"D"};
                }
            }
        }

        if(optionQuantities[4][3] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "F"))
                {
                    data[0][i] = new String[]{"F"};
                }
            }
        }

        if(optionQuantities[4][4] == 1)
        {
            for (int i = 0; i < 5; i++) {
                if (optionExists(0, i, "H"))
                {
                    data[0][i] = new String[]{"H"};
                }
            }
        }

        /*
        for(int[] arr: optionQuantities)
        {
            System.out.println(Arrays.toString(arr));
        }*/
    }

    private static boolean optionExists(int i, int j, String value)
    {
        ArrayList<String> options = new ArrayList<>(Arrays.asList(data[i][j]));
        return (options.contains(value) || options.contains("!" + value));
    }

    private static void removeOptionFromCell(int i, int j, String value)
    {
        if(data[i][j].length == 1)
        {
            return;
        }
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
                answer = i;
                return true;
            }
        }
        return false;
    }
}
