module Demo
{

    struct FibAndTime{
        long fibResult;
        long serverExecTime;
    }

    interface Printer
    {
        
        FibAndTime printString(string s);

    }
}