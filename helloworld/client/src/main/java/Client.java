import java.net.InetAddress;
import Demo.FibAndTime;

public class Client
{
    public static void main(String[] args)
    {
        java.util.List<String> extraArgs = new java.util.ArrayList<>();

        try(com.zeroc.Ice.Communicator communicator = com.zeroc.Ice.Util.initialize(args,"config.client",extraArgs))
        {
            Demo.PrinterPrx printer = Demo.PrinterPrx.checkedCast(
                communicator.propertyToProxy("Printer.Proxy")).ice_twoway().ice_secure(false);

            if(printer == null)
            {
                throw new Error("Invalid proxy");
            }

            java.util.Scanner scanner = new java.util.Scanner(System.in);
            String username = System.getProperty("user.name");
            String hostname;

            try {
                hostname = InetAddress.getLocalHost().getHostName();
            } catch (Exception e) {
                hostname = "UnknownHost"; 
            }

            while(true) {

                String text = scanner.nextLine();

                long startTotalElapsed = System.nanoTime(); //total elapsed timer start

                if(text.equals("exit")) {
                    printer.printString(String.format("Exiting program for user: %s on host: %s", username, hostname));
                    break;
                }
                
                String message = String.format("%s:%s:%s", username, hostname, text);
                
                long startClientIR = System.nanoTime(); //client invocation&respone timer start

                FibAndTime result = printer.printString(message);
                
                long endClientIR = System.nanoTime(); //client invocation&respone timer end
                long clientIResponseTime = endClientIR - startClientIR; // client invocation&response time
                double msClientResponseTime = clientIResponseTime/1_000_000.0; //client invocation&respone time in miliseconds 


                if (result.fibResult > 0){
                    System.out.println("Result: " + result.fibResult);
                }

                long endTotalElapsed = System.nanoTime(); //total elapsed timer end
                long totalElapsedTime = endTotalElapsed - startTotalElapsed; // total elapsed time
                double msTotalElapsedTime = totalElapsedTime/1_000_000.0; // total elapsed time in milliseconds

                long serverExecutionTime = result.serverExecTime; // get the server execution time from result
                double msServerExecutionTime = serverExecutionTime/1_000_000.0; // server execution time in milliseconds
                
                long middlewareTime = clientIResponseTime - serverExecutionTime; // middleware transmission time is client responsre - server execution
                double msMiddlewareTime = middlewareTime/1_000_000.0; // middleware transmission time in milliseconds

                System.out.println("Server Service Execution Time: " + msServerExecutionTime + "ms");
                System.out.println("Client Invocation & Response Reception Time: " + msClientResponseTime + "ms");
                System.out.println("Network & Middleware Transmission Time: " + msMiddlewareTime + "ms");
                System.out.println("Total End-to-End Elapsed Time: " + msTotalElapsedTime + "ms");

            }
        }
    }
}