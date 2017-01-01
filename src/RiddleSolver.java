import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by arun on 12/31/16.
 */
public class RiddleSolver {

    public static String[][][] data;

    /**
     * This is the main method where all of the solving will be happening
     * @param String[]
     * @return void
     */
    public static void main(String[] args) {
        data = new String[5][5][];
        for(int i = 0; i < 5; i++)
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

        displayData();
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

    }

    /**
     * This method will check to see if the fish has been found
     * @param null
     * @return boolean
     */
    private boolean gameWon()
    {
        for(String[] cell: data[4])
        {
            if(cell.length == 1 && cell[0].equals("Fish"))
            {
                return true;
            }
        }
        return false;
    }
}
