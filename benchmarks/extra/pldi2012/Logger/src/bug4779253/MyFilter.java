package bug4779253;

import java141.util.logging.Filter;
import java141.util.logging.LogRecord;


public class MyFilter implements Filter {

    @Override
    public boolean isLoggable(LogRecord record) {
        return false;
    }
}
