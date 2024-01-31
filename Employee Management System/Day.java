public class Day implements Cloneable, Comparable<Day>  {
    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";
    private int day;
    private int month;
    private int year;
 
    public void set(String sDay) {
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]);
        this.year = Integer.parseInt(sDayParts[2]);
        this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
    }
 
    public Day(String sDay) {
        set(sDay);
    }
 
    public Day getNextDate() {
        int day = this.day + 1;
        int month = this.month;
        int year = this.year;
 
        int maxDay = 31;
 
        if (month == 4 || month == 6 || month == 9 || month == 11) {
            maxDay = 30;
        } else if (month == 2) {
            if ((year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)) {
                maxDay = 29;
            } else {
                maxDay = 28;
            }
        }
 
        if (day > maxDay) {
            day = 1;
            month++;
            if (month > 12) {
                month = 1;
                year++;
            }
        }
        String[] monthNames = {
            "Jan", "Feb", "Mar", "Apr", "May", "Jun",
            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
        };
 
        return new Day(String.format("%02d-%s-%04d", day, monthNames[month - 1], year));
    }
 
    @Override
    public String toString() {
        String monthName = MonthNames.substring((month - 1) * 3, month * 3);
        return day + "-" + monthName + "-" + year;
    }

    @Override
    public int compareTo(Day other) {
        if (this.year != other.year) {
            return Integer.compare(this.year, other.year);
        } else if (this.month != other.month) {
            return Integer.compare(this.month, other.month);
        } else {
            return Integer.compare(this.day, other.day);
        }
    }
 
    @Override
    public Day clone() {
        try {
            Day copy = (Day) super.clone();
            return copy;
        } catch (CloneNotSupportedException e) {
            return null;
        }
    }
}