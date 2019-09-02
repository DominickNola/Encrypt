import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.*;
import java.lang.*;
import java.util.List;

public class Main {

    final static int DeltaOne = 0x11111111;
    final static int DeltaTwo = 0x22222222;
    final static long K[] = new long[4];
    static long L[] = new long[3];
    static long R[] = new long[3];

    public static void main(String[] args) throws FileNotFoundException, IOException {

        L[1] = 0x00000000;
        L[2] = 0x00000000;
        R[1] = 0x00000000;
        R[2] = 0x00000000;

        System.out.println("\nEncryption:");
        PrintStream obj = new PrintStream(new File("tst_Encryption.txt"));
        List<String> list = new ArrayList<>();
        Scanner stdin = new Scanner(System.in);

        for(int i = 0; i < 4; i++) {
            // input 8-bit Hex strings for K values
            System.out.println("Please input K[" + i + "] in Hex String(w/o '0x'): ");
            String input = stdin.next();
            list.add(input);
            Long y = Long.parseLong(input, 16);
            K[i] = y;
            System.out.println(K[i]);
        }

        System.out.println("Please input L[0] in Hex String(w/o '0x'): ");
        String inputL = stdin.next();
        // create 0xhexstring
        String X = "0x" + inputL;
        Long y = Long.parseLong(inputL, 16);
        L[0] = y;
        System.out.println("L[0] = " +  L[0]);

        System.out.println("Please input R[0] in Hex String(w/o '0x'): ");
        String inputR = stdin.next();
        // create 0xhexstring
        String T = "0x" + inputR;
        Long q = Long.parseLong(inputR, 16);
        R[0] = q;
        System.out.println("R[0] = " +  R[0]);

        stdin.close();

        String[] arr = list.toArray(new String[0]);
        System.out.println();
        System.out.println("K Array is " + Arrays.toString(arr));

        encrypt();

        // print to txt file
        for(int i = 0; i < 3; i++) {
            obj.println("L[" + (i) + "] = " + Long.toHexString(L[i]) +
                    "    R[" + (i) + "] = " + Long.toHexString(R[i]));
        }

        obj.close();

        File file = new File("tst_Encryption.txt");
    }

    private static void encrypt() {
        System.out.println("L[0] = " + Long.toHexString(L[0]) + "    R[0] = " + Long.toHexString(R[0]));
        int j = 0;

        for(int i = 0; i < 2; i++) {
            long a = (R[i] << 4) + K[i + j]; // K[0] and K[2]
            long b;
            // toglle between them
            if(j == 0) {
                b = R[i] + DeltaOne;
            } else {
                b = R[i] + DeltaTwo;
            }
            long c = (R[i] >> 5) + K[i + 1 + j]; // K[1] and K[3]
            long d = a ^ b ^ c;
            long e = L[i] + d;
            R[i + 1] = e;
            L[i + 1] = R[i];
            System.out.println("L[" + (i+1) + "] = " + Long.toHexString(L[i+1]) +
                               "    R[" + (i+1) + "] = " + Long.toHexString(R[i+1]));
            if(j == 0) {
                j = 1;
            } else {
                j = 0;
            }
        }
        return;
    }

}
