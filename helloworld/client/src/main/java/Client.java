import java.net.InetAddress;

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

                if(text.equals("exit")) {
                    printer.printString(String.format("Exiting program for user: %s on host: %s", username, hostname));
                    break;
                }

                String message = String.format("%s:%s:%s", username, hostname, text);
                long result = printer.printString(message);
                if (result > 0){
                    System.out.println("Result: " + result);
                }
            }
        }
    }
}