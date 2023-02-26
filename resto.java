import java.util.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.*;
class  foodorder{
    Scanner sc=new Scanner(System.in);
    ArrayList<ArrayList<String>> 
    arr1=new ArrayList<ArrayList<String>>();
    ArrayList<ArrayList<String>> arr2=new ArrayList<ArrayList<String>>();
    ArrayList<Integer> arrId=new ArrayList<>();
    ArrayList<Integer> arrq=new ArrayList<>();
    void readingfiles(){
    try {
        Scanner string1=new Scanner(new FileReader("menuList.csv"));
        Scanner string2=new Scanner(new FileReader("billingDetails.csv"));
        String s;
        while(string1.hasNext()){
            s=string1.nextLine();
            String[] str=s.split(",");
            List<String> oddetails=Arrays.asList(str);
            ArrayList<String> array1=new ArrayList<>(oddetails);
            
            
        arr1.add(array1);
        }
        while(string2.hasNext()){
            String ss=string2.nextLine();
            String[] str1=ss.split(",");
            List<String> foodod=Arrays.asList(str1);
            ArrayList<String> array2=new ArrayList<>(foodod);
            arr2.add(array2);

        }
    }
    catch (Exception e) {
        System.out.println("RuntimeError");
    }
    }
    void display(ArrayList<ArrayList<String>>arr1){
        int n=arr1.size();
        for(int i=0;i<n;i++){
            ArrayList<String> food=arr1.get(i);
            for (String string : food) {
                System.out.print(string+" ");                
            }
            System.out.println();
        }
    }
    void menu(){
        String arr[]={"Generate the new bill","View the total collection for today","Cancel the bill"};
        int n=arr.length;
        for(int i=0;i<n;i++){
            System.out.print(i+1+"-"+arr[i]+"\n");
        }
        System.out.print("Enter Your Choice: ");
    }
    void generateBill(){
        System.out.println("Generate the new bill");
        display(arr1);
        order();
    }
    void Daycollection(){
        sc.nextLine();
        System.out.println("View the total collection for today");
        System.out.print("Enter date(in format D-M-YY)::");
        String s=sc.nextLine();
        Double Daycollection=0.0;
        for(int i=0;i<(arr2.size());i++){
            ArrayList<String> string1=arr2.get(i);
            if((string1.get(1)).equals(s)){
                Double b=Double.parseDouble(string1.get(2));
                Daycollection+=b;
                System.out.println(string1+"\n");
            }  
        }
        System.out.println("Total collection of day: "+Daycollection);

    }
    void cancelBill(){
        System.out.println("Cancel the bill:: ");
        display(arr2);
        System.out.print("Enter the id in above list:: ");
        int n=sc.nextInt();
        int t=arr2.size();
        if(n>t){
            System.out.println("Enter Valid Id");
        }
        else{
            n=n-1;
            (arr2.get(n)).set(4, "cancelled");
            try {
                FileWriter billobj=new FileWriter("billingDetails.csv",false);
                for(int i=0;i<(arr2.size());i++){
                    ArrayList<String>str1=arr2.get(i);
                    String Bill=String.join(",",str1);
                    Bill+="\n";
                    FileWriter billdetailsobj=new FileWriter("billingDetails.csv",true);
                    billdetailsobj.write(Bill);
                    billdetailsobj.close();
                }
                    billobj.close();
                    display(arr2);
                    System.out.println("Order Cancelled");
            } catch (Exception e) {
                System.out.println("Error");
            }
        }


    }
    void details(){
        int n=sc.nextInt();
        switch (n) {
            case 1:
                generateBill();
                break;
            case 2:
                Daycollection();
                break;
            case 3:
                cancelBill();
                break;
        
            default:
                System.out.println("Enter Valid Number");
                break;
        }
    }
    void order(){
        System.out.print("Enter Order Id: ");
        int n=sc.nextInt();
        System.out.print("Enter Order Quantity: ");
        int m=sc.nextInt();
        arrId.add(n);
        arrq.add(m);
        System.out.print("If you want to order again (Yes->y) or (No->n): ");
        char ch=sc.next().charAt(0);
        if(ch=='y'|| ch=='Y'){
            order();
        }
        else{
            int ordercount=arrId.size();
            Double total=0.0;
            System.out.print("check your details:: ");
            for(int i=0;i< ordercount;i++){
                int k=arrId.get(i);
                int l=arrq.get(i);
                ArrayList<String> food1=arr1.get(k-1);
                Double a=Double.parseDouble(food1.get(2));
                total+=a*l;
                System.out.println(arrId.get(i)+" ");
            }
            System.out.println("Total:  "+total);
            System.out.print("Do you want to confirm order (Yes-y) or (No-n): ");
            char ch1=sc.next().charAt(0);
            if(ch1=='y'||ch1=='Y'){
                String s=",";
                LocalDate date1= LocalDate.now();
                DateTimeFormatter obj22=DateTimeFormatter.ofPattern("dd-MMM-yy");
                String date=date1.format(obj22);
                int x=arr2.size()+1;
                String a=x+s+date+s+total+.00+s;
                for(int i=0;i<(arrId.size());i++){
                    a+=arrId.get(i)+" ";
                    a+=arrq.get(i)+" ";
                }
                a+=s+"Approved";
                try {
                    File newn=new File("billingDetails.csv");
                    FileWriter obj2=new FileWriter(newn,true);
                    obj2.write("\n"+a);
                    obj2.close();
                } catch (Exception e) {
                    System.out.println("error");
                }
                
                System.out.println("Thank You Visit Again");
            }
            else{
                menu();
                details();
            }      
            
        }
    }
    
}
public class resto {
 public static void main(String[] args) {
    
foodorder obj=new foodorder();
    System.out.println("Welcome to Delight Resto");
    obj.readingfiles();
    obj.menu();
    obj.details();  
    
 }   
}