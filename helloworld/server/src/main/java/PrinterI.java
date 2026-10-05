public class PrinterI implements Demo.Printer
{
    public long printString(String s, com.zeroc.Ice.Current current)
    {
        String[] parts = s.split(":", 3);
        String prefix = (parts.length >= 2) ? parts[0] + ":" + parts[1] + ": " : "";
        String msg = (parts.length == 3) ? parts[2].trim() : s.trim();

        try {
            int n = Integer.parseInt(msg);
            if (n > 0) {
                long a = 1, b = 1;
                System.out.print(prefix);
                for (int i = 1; i <= n; i++) {
                    System.out.print(a + (i < n ? ", " : "\n"));
                    long next = a + b;
                    a = b;
                    b = next;
                }
                return (n == 1) ? 1 : (b - a);
            }
        } catch (Exception ignored) {}

        System.out.println(prefix + msg);
        return 0;
    }
}