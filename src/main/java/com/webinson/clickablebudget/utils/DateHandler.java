package com.webinson.clickablebudget.utils;

import org.exolab.castor.mapping.GeneralizedFieldHandler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Slavo on 14.09.2016.
 */
public class DateHandler extends GeneralizedFieldHandler {

    //private static final Log logger = LogFactory.getLog(DateHandler.class);

    private static final String FORMAT = "yyyy-MM-dd";

    private SimpleDateFormat formatter = new SimpleDateFormat(FORMAT);

    public Object convertUponGet(Object value) {
        if (value == null) {
            return "13-07-1974";    // default value if null date
        }
        Date date = (Date) value;
        return formatter.format(date);
    }

    public Object convertUponSet(Object value) {
        Date date = null;
        try {
            date = formatter.parse((String) value);
        } catch (ParseException px) {
            //logger.error("Parse Exception (bad date format) : " + (String) value);
            return null;  // default value for empty/incorrect date
        }
        return date;
    }

    public Class<?> getFieldType() {
        return Date.class;
    }

    public Object newInstance(Object parent) throws IllegalStateException {
        return null;
    }

}
