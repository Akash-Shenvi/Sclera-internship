package Day3.task;

import java.util.Scanner;

public class Inertialarray {

    public static int isInertial(int[] arr) {
        int max = arr[0];
        boolean hasOdd = false;

        for (int num : arr) {
            if (num > max) {
                max = num;
            }


            if (num % 2 != 0) {
                hasOdd = true;
            }
        }

        if (!hasOdd){
            return 0;
        }

        if (max % 2 != 0) {
            return 0;
        }

        for (int odd : arr) {
            if (odd % 2 != 0) {
                for (int even : arr) {
                    if (even % 2 == 0 && even != max) {
                        if (odd <= even) {
                            return 0;
                        }
                    }
                }
            }
        }

        return 1;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size: ");
        int n = sc.nextInt();

        int[] arr = new int[n];
        System.out.println("Enter array elements:");
        for (int i = 0; i < n; i++) {
            arr[i] = sc.nextInt();
        }

        System.out.println(isInertial(arr));
    }
}

