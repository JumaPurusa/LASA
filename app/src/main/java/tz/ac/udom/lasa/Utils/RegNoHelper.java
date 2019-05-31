package tz.ac.udom.lasa.Utils;

public class RegNoHelper {

    public static boolean isValid(String regNo){

        if(regNo.matches("[T][/][U][D][O][M][/][2][0][0-9][0-9][/][0-9][0-9][0-9][0-9][0-9]"))
            return true;
        else
            return false;
    }
}
