package tasks;

import java.util.Scanner;

public class prime {
    static boolean isPrime(int N){
        if (N<=1){
            return false;
        }
        else {
            for(int i=2;i<Math.sqrt(N);i++){
                if (N%i==0) {
                    return false;
                }
            }
            return true;
        }
    }
    public static void main(String[] args) {
        System.out.println("Printing N Prime Numbers");
        Scanner sc =new Scanner(System.in);
        System.out.println("Enter The Value Of N");
        int N= sc.nextInt();
        System.out.println("Prime Numbers form 1 to "+N+"are: ");
        for (int i=2;i<=N;i++){
            if (isPrime(i)) {
                System.out.print(i + " ");
            }
        }


    }
}
