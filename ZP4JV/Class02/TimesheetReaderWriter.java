package Class02;

import java.io.*;

public interface TimesheetReaderWriter {
    
    // Nacte ze streamu XML soubor a dle nej vytvori prislusny objekt timesheet
    public Timesheet loadTimesheet(InputStream input) throws Exception;
    
    // Ulozi do prislusneho streamu XML soubor predstavujici dany timesheet 
    public void storeTimesheet(OutputStream output, Timesheet timesheet) throws Exception;
}
