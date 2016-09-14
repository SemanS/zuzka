package com.webinson.clickablebudget.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Slavo on 13.09.2016.
 */
public class MonthFormatter {

    public String monthFormat(Date date) {


        String datum = new String();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        Integer month = cal.get(Calendar.MONTH);

        switch (month)

        {
            case 0:
                datum = "Leden";
                break;
            case 1:
                datum = "Únor";
                break;
            case 2:
                datum = "Březen";
                break;
            case 3:
                datum = "Duben";
                break;
            case 4:
                datum = "Květen";
                break;
            case 5:
                datum = "Červen";
                break;
            case 6:
                datum = "Červenec";
                break;
            case 7:
                datum = "Srpen";
                break;
            case 8:
                datum = "Září";
                break;
            case 9:
                datum = "Říjen";
                break;
            case 10:
                datum = "Listopad";
                break;
            case 11:
                datum = "Prosinec";
                break;
        }
        return datum;
    }
}
