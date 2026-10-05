import java.io.*;

//java -jar server/build/libs/server.jar


// public class Fibonacci{

//   public List<Integer> getFib(n){
  
//     int[] fib_nums = new int[n];
    
//     fib_nums[0] = 0;
//     fib_nums[1] = 1;
    
//     for(int i = 0; i <= n; i++){
    
//       fib_nums[i] = fib_nums[i-1] + fib_nums[i-2]; 

//     }
    
//       return fib_nums;

//   }
// }

public class Server
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<String>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.server",extraArgs))
        {
            if(!extraArgs.isEmpty())
            {
                System.err.println("too many arguments");
                for(String v:extraArgs){
                    System.out.println(v);
                }
            }
            com.zeroc.Ice.ObjectAdapter adapter = communicator.createObjectAdapter("Printer");
            com.zeroc.Ice.Object object = new PrinterI();
            adapter.add(object, com.zeroc.Ice.Util.stringToIdentity("SimplePrinter"));
            adapter.activate();
            communicator.waitForShutdown();
        }
    }

    public static void f(String m)
    {
        String str = null, output = "";

        InputStream s;
        BufferedReader r;

        try {
            Process p = Runtime.getRuntime().exec(m);

            BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream())); 
            while ((str = br.readLine()) != null) 
            output += str + System.getProperty("line.separator"); 
            br.close(); 
        }
        catch(Exception ex) {
        }
    }

}