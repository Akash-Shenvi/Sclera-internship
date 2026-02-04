package Day2;

public class objectcreation {
    String obje;
    public void print(String obje){
        this.obje=obje;
        System.out.println("printed "+this.obje);
    }

    public static void main(String[] args) {
        objectcreation obj=new objectcreation();

        obj.print("Hii");
    }

}
