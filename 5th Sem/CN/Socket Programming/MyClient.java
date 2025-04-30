import java.io.*;
import java.net.*;

public class MyClient {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost" , 5000);

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader userinput = new BufferedReader(new InputStreamReader(System.in));

            String message;

            while(true){
                System.out.println("You : ");
                message = userinput.readLine();
                out.println(message);
                if(message.equalsIgnoreCase("bye")){
                    System.out.println("TERIMNATED");
                    break;
                }

                message = in.readLine();

                if(message.equalsIgnoreCase("bye")){
                    System.out.println("TERIMNATED");
                    break;
                }
            }

            System.out.println("System : " + message);

             in.close();
             out.close();
             userinput.close();
             s.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
