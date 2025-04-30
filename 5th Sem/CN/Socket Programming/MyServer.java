import java.io.*;
import java.net.*;    

public class MyServer{
    public static void main(String[] agrs){
        try{
            ServerSocket ss = new ServerSocket(5000);
            System.out.println("Server Started...");
            Socket s = ss.accept();
            System.out.println("Server Started"+s.getInetAddress());

            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);
            BufferedReader userinput = new BufferedReader(new InputStreamReader(System.in));

            String message;

            while (true) { 
             message = in.readLine();
             if(message.equalsIgnoreCase("bye")){
                System.out.println("TERMINATED");
                break;
             }   
             System.out.println(message);
             System.out.println("You : ");
            
             message = userinput.readLine();

             out.println();

             if(message.equalsIgnoreCase("bye")){
                System.out.println("TERMINATED");
                break;
             }

             in.close();
             out.close();
             userinput.close();
             s.close();
             ss.close();

            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}