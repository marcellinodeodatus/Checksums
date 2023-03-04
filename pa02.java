/*=============================================================================
| Assignment: pa02 - Calculating an 8, 16, or 32 bit
| checksum on an ASCII input file
|
| Author: Deodatus Marcellino
| Language: Java
|
| To Compile: javac pa02.java
| gcc -o pa02 pa02.c
| g++ -o pa02 pa02.cpp
|
| To Execute: java -> java pa02 inputFile.txt 8
| or c++ -> ./pa02 inputFile.txt 8
| or c -> ./pa02 inputFile.txt 8
| where inputFile.txt is an ASCII input file
| and the number 8 could also be 16 or 32
| which are the valid checksum sizes, all
| other values are rejected with an error message
| and program termination
|
| Note: All input files are simple 8 bit ASCII input
|
| Class: CIS3360 - Security in Computing - Fall 2021
| Instructor: McAlpin
| Due Date: per assignment
|
+=============================================================================*/
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class pa02 {
    public static void main(String[] Args) throws Exception {

        // variables
        File inFile = new File(Args[0]); 
        String input = new String();
        // read file
        input = reading_files(input, inFile);

        String s;
        int checksum_bytesize;

        // get byte size
        do {
            s = new String(Args[1]);
            checksum_bytesize = Integer.parseInt(s);
            try
            {    
                if(checksum_bytesize == 8 || checksum_bytesize == 16 || checksum_bytesize == 32)
                    System.out.println("");
            }
            catch(Exception e)
            {
                System.err.println("Valid checksum sizes are 8, 16, or 32\n");
                checksum_bytesize = 0;
            }
        } while (checksum_bytesize == 0);

        switch (checksum_bytesize) {
            case 8: // 8-bit checksum
                eightbit eight = new eightbit();
                eight.algorithm(input, input.length());

                break;
        
            case 16: // 16-bit checksum
                sixteenbit sixteen = new sixteenbit();
                sixteen.algorithm(input, input.length());

                break;

            case 32: // 32-bit checksum
                thirtytwobit thirtytwo = new thirtytwobit();
                thirtytwo.algorithm(input, input.length());

                break;
            }
        
    }
    static String reading_files(String str, File inFile)
    {
        try
        {
            BufferedReader br = new BufferedReader(new FileReader(inFile));
            String line = new String();

            while((line = br.readLine()) != null)
            {
                str = str+line;
            }

            br.close();
        }
        catch ( Exception e ) {e.printStackTrace();}
        
        return str;
    } // end reading_files

}// end class pa02

class sixteenbit implements moduloSum {

    @Override
    public String padding(String s, int x) {

        //hex digits *2 of the ascii length
        int result = x % 4;
        int pad = 0;
        switch (result) {
            case 1:
                pad = 3;
                break;
            case 2:
                pad = 2;
                break;
            case 3:
                pad = 1;
                break;
            default:
                pad = 0;
                break;
        }
        while(pad != 0)
        {
            s += 'X';
            pad--;
        }
        return s;
    }

    public int generate_checksum(String s){
        String my_hex_val = new String();
        int x, i, result = 0;
        //repeating twice
        for (i = 0; i < s.length() - 2; i = i + 2){
            x = (int) (s.charAt(i));
            my_hex_val = Integer.toHexString(x);
            x = (int) (s.charAt(i + 1));
            my_hex_val += Integer.toHexString(x);

            x = Integer.parseInt(my_hex_val, 16);
            result += x;
        }
        return result;
    }

    @Override
    public void algorithm(String s, int x) {
        String newString = padding(s, x);
        int checksum = generate_checksum(s);
        System.out.println("16-bit checksum is "+newString+" for all "+checksum+" chars");
    }

    @Override
    public void print(String s, int x) {
        System.out.println("16-bit checksum is "+s+" for all"+x+" chars");

    }

}//end class sixteenbit


class thirtytwobit implements moduloSum {

    @Override
    public String padding(String s, int x) {

        int result = x % 8;
        int pad = 0;
        switch (result) {
            case 1:
                pad = 3;
                break;
            case 2:
                pad = 2;
                break;
            case 3:
                pad = 1;
                break;
            default:
                pad = 0;
                break;
        }
        while(pad != 0)
        {
            s += 'X';
            pad--;
        }
        return s;
    }

    public int generate_checksum(String s){
        String my_hex_val = new String();
        int x, i, result = 0;
        //Repeating 4 times
        for (i = 0; i < s.length() - 4; i = i + 4){
            x = (int) (s.charAt(i));
            my_hex_val = Integer.toHexString(x);
            x = (int) (s.charAt(i + 1));
            my_hex_val += Integer.toHexString(x);
            x = (int) (s.charAt(i + 2));
            my_hex_val += Integer.toHexString(x);
            x = (int) (s.charAt(i + 3));

            x = Integer.parseInt(my_hex_val, 16);
            result += x;
        }
        return result;
    }

    @Override
    public void algorithm(String s, int x) {
        String newString = padding(s, x);
        int checksum = generate_checksum(s);
        System.out.println("32-bit checksum is "+newString+" for all "+checksum+" chars");
    }

    @Override
    public void print(String s, int x) {
        System.out.println("32-bit checksum is "+s+" for all"+x+" chars");
    
    }
}// end class thirtytwobit

class eightbit implements moduloSum {

    @Override
    public String padding(String s, int x) {
        // no padding for 8bit, as hex values will always be even.
        int result = x % 2;
        int pad = 0;
        switch (result) {
            case 1:
                pad = 3;
                break;
            case 2:
                pad = 2;
                break;
            case 3:
                pad = 1;
                break;
            default:
                pad = 0;
                break;
        }
        while(pad != 0)
        {
            s += 'X';
            pad--;
        }
        return s;
    }
    public int generate_checksum(String s){
        String my_hex_val = new String();
        int x, i, result = 0;
        for (i = 0; i < s.length(); i++){
            x = (int) (s.charAt(i));
            my_hex_val = Integer.toHexString(x);
            x = Integer.parseInt(my_hex_val, 16);
            result += x;
        }
        return result;
    }
    @Override
    public void algorithm(String s, int x) {
        String newString = padding(s, x);
        int checksum = generate_checksum(s);
        System.out.println("8-bit checksum is "+newString+" for all "+checksum+" chars");
    }
    
    @Override
    public void print(String s, int x) {
        System.out.println("8-bit checksum is "+s+" for all"+x+" chars");
    
    }

}//end class eightbit

interface moduloSum {
    public void algorithm (String s, int x);
    public String padding (String s, int x);
    public int generate_checksum(String s);
    public void print (String s, int x);
}//end interface moduloSum


class checksum {
    private int length;
    private int bytesize;
    private String input;

    public checksum() {
    }

    public checksum(int length, int bytesize, String input) {
        this.length = length;
        this.bytesize = bytesize;
        this.input = input;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getBytesize() {
        return this.bytesize;
    }

    public void setBytesize(int bytesize) {
        this.bytesize = bytesize;
    }

    public String getInput() {
        return this.input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public checksum length(int length) {
        setLength(length);
        return this;
    }

    public checksum bytesize(int bytesize) {
        setBytesize(bytesize);
        return this;
    }

    public checksum input(String input) {
        setInput(input);
        return this;
    }

}//end class checksum

/*=============================================================================
| I [Deodatus Marcellino] ([de960692]) affirm that this program is
| entirely my own work and that I have neither developed my code together with
| any another person, nor copied any code from any other person, nor permitted
| my code to be copied or otherwise used by any other person, nor have I
| copied, modified, or otherwise used programs created by others. I acknowledge
| that any violation of the above terms will be treated as academic dishonesty.
+=============================================================================*/

