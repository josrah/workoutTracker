public class Date implements Comparable<Date> {
    private int year;
    private int month;
    private int day;

    Date(int year, int month, int day){
        this.year = year;
        this.month = month;
        this.day = day;
    }

    static Date parseDate(String string){
        String[] tokens = string.split("-");
        int year = Integer.parseInt(tokens[0]);
        int month = Integer.parseInt(tokens[1]);
        int day = Integer.parseInt(tokens [2]);
        return new Date(year, month, day);
    }

    @Override
    public int compareTo(Date other) {
        int cmp = other.year - year;
        if (cmp != 0) return cmp;

        cmp = other.month - month;
        if (cmp != 0) return cmp;

        return other.day - day;
    }

    @Override
    public String toString(){
        return year + "-" + month + "-" + day;
    }
}
