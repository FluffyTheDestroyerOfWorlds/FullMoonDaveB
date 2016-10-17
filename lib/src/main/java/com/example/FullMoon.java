package com.example;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class FullMoon {
    public static void main(String[] args) {
        String[] MoonPhase = {"01/23/2016", "02/22/2016","03/23/2016","04/22/2016","05/21/2016","06/20/2016","07/19/2016","08/18/2016","09/16/2016","10/15/2016","11/14/2016","12/13/2016","01/12/2017","02/10/2017","03/12/2017","04/11/2017","05/10/2017","06/09/2017","07/08/2017","08/07/2017","09/06/2017","10/05/2017","11/03/2017","12/03/2017"};
        String sInput = "";
        Boolean Found = false;
        Date HoldDate = null, PrevDate = null, CurrDate = null;
        Integer xPhase = 0;
        Integer nMDY[] = {0, 0, 0};
        boolean FirstTime = false;
       // Date FullMoon[] = {2000 Jan 21 04:40,2000 Feb 19 2016:27}

        Scanner in = new Scanner(System.in);

        while (!sInput.equalsIgnoreCase("End")) {
            System.out.println("Please enter a date (2016-2017) mm/dd/yyyy - (enter 'End' to quit)");
            sInput = in.nextLine();
            FirstTime = false;
            Found = false;

            if(!sInput.equalsIgnoreCase("End")) {
                HoldDate = string2date(sInput);
                PrevDate = HoldDate;
                int i = 0;
                while (!Found && i < MoonPhase.length) {
                    CurrDate = string2date(MoonPhase[i]);
                   // System.out.println("P: " + PrevDate + "--C: " + CurrDate + "--H: " + HoldDate);
                    if (CurrDate.compareTo(HoldDate) > 0) {
                        Found = true;
                       // System.out.println("true");
                    } else {
                        PrevDate = CurrDate;
                        i++;
                      //  System.out.println(i);
                    }
                }

                System.out.println("the full moon date is " + CurrDate);
            }
            // xPhase = WhenIsFullMoon(202016,10,17);
            //  System.out.println(xPhase);

          /*  while (!Found) {
                nMDY = breakOutDate(HoldDate);
                if (xPhase == 15 && FirstTime) {  // 1 = 4 2 = 15

                    System.out.println("the full moon date is " + HoldDate);
                    Found = true;
                } else {
                    FirstTime = true;
                    HoldDate = AddXDays(HoldDate, 1);
                }
                xPhase = WhenIsFullMoon2(nMDY[2], nMDY[0], nMDY[1]);
                  System.out.println(xPhase + ":--" + HoldDate);
            }
            // System.out.println("the full moon date is" + HoldDate );
*/

        }
    }

    public static Integer[] breakOutDate(Date xDate){
    Integer mdy[] = {0,0,0};
        Calendar cal = Calendar.getInstance();
        cal.setTime(xDate);

        mdy[0]= cal.get(Calendar.MONTH);
        mdy[1]= cal.get(Calendar.DAY_OF_MONTH);
        mdy[2]= cal.get(Calendar.YEAR);

        return mdy;
    }

    public static Date string2date(String dInput) {
        DateFormat formatIt = null;
        Date HoldDate = null;
        try {
            formatIt = new SimpleDateFormat("MM/dd/yyyy");
            HoldDate = (Date) formatIt.parse(dInput);
        } catch (ParseException x) {

        }
        return HoldDate;
    }


    public static Date AddXDays(Date dInput,Integer X) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(dInput);
        cal.add(Calendar.DATE,X);
        return cal.getTime();
    }



    private static int WhenIsFullMoon(int y, int m, int d)
    {
    /*
      http://www.voidware.com/moon_phase.htm
      calculates the moon phase (0-7), accurate to 1 segment.
      0 = > new moon.
      4 => full moon.
      */

        Double c,e;
        Double jd, temp;
        int b;

        if (m < 3) {
            y--;
            m += 12;
        }
        ++m;

        c = 365.25 * y;
        e = 30.6001 * m; // + .5;
        jd = c + e + d - 694039.09;  /* jd is total days elapsed */
        jd /= 29.531;           /* divide by the moon cycle (29.53 days) */
        b = jd.intValue();		   /* int(jd) -> b, take integer part of jd */
        jd -= b;		   /* subtract integer part to leave fractional part of original jd */
        temp = jd*8 + 0.5;	   /* scale fraction from 0-8 and round by adding 0.5 */
        b = temp.intValue();
       // b = b & 7;		   /* 0 and 8 are the same so turn 8 into 0 */

        return b;
    }

    public static Integer WhenIsFullMoon2(Integer year, Integer month, Integer day) {
        Double n;
        Double RAD;
        Double t, t2, as,am, xtra, i;
        Double j1, jd, temp;

        n = Math.floor(12.37 * (year -1900 + ((1.0 * month - 0.5)/12.0)));
        RAD = 3.14159265/180.0;
        t = n / 1236.85;
        // t squared
        t2 = t * t;
        // appacture sun
        as = 359.2242 + 29.105356 * n;
        // appacture moon
        am = 306.0253 + 385.816918 * n + 0.010730 * t2;

        xtra = 0.75933 + 1.53058868 * n + ((1.178e-4) - (1.55e-7) * t) * t2;
        xtra += (0.1734 - 3.93e-4 * t) * Math.sin(RAD * as) - 0.4068 * Math.sin(RAD * am);
        i = (xtra > 0.0 ? Math.floor(xtra) :  Math.ceil(xtra - 1.0));
        //get the julian day
        j1 = julday(year,month,day);
        jd = (2415020 + 28 * n) + i;
        temp = (j1-jd + 30)%30;
        return temp.intValue();
    }

    public static Double julday(Integer year, Integer month, Integer day) {
        Double jul, ja;

        if (year < 0) { year ++; }
        Integer jy = year;
        Integer jm = month +1;
        if (month <= 2) {jy--;	jm += 12;	}
        jul = Math.floor(365.25 *jy) + Math.floor(30.6001 * jm) + day + 1720995;
        if (day+31*(month+12*year) >= (15+31*(10+12*1582))) {
            ja = Math.floor(0.01 * jy);
            jul = jul + 2 - ja + Math.floor(0.25 * ja);
        }

        return jul;
    }

}
