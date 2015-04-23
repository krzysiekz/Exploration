package com.exploration.logreader;

import com.exploration.model.HTTPMethod;
import com.exploration.model.LogEntry;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LogReaderImpl implements LogReader {

    public static final int NUM_FIELDS = 7;
    private static final String LOG_ENTRY_PATTERN = "^([A-Za-z0-9-.]+) (\\S+) (\\S+) \\[([\\w:/]+\\s[+\\-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+)";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd/MMM/yyyy:HH:mm:ss Z", Locale.US);
    private BufferedReader bufferedReader;

    public LogReaderImpl(InputStream inputStream) {
        this.bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
    }

    @Override
    public LogEntry read() throws IOException, ParseException {
        String line = bufferedReader.readLine();
        if(line != null) {
            Pattern p = Pattern.compile(LOG_ENTRY_PATTERN);
            Matcher matcher = p.matcher(line);
            if (!matcher.matches() || NUM_FIELDS != matcher.groupCount()) {
                return read();
            } else {
                LogEntry logEntry = new LogEntry();
                logEntry.setUser(matcher.group(1));
                logEntry.setTime(DATE_FORMAT.parse(matcher.group(4)));
                String[] quoted = matcher.group(5).split(" ");
                if(quoted.length == 3) {
                    logEntry.setMethod(HTTPMethod.valueOf(quoted[0]));
                    logEntry.setPath(quoted[1]);
                    logEntry.setProtocol(quoted[2]);
                } else {
                    return read();
                }
                logEntry.setStatusCode(Integer.parseInt(matcher.group(6)));
                return logEntry;
            }
        } else {
            bufferedReader.close();
            return null;
        }
    }
}
