import java.util.Comparator;

class DateComparator implements Comparator<Lift> {
    public int compare(Lift l1, Lift l2) {
        return l1.getDate().compareTo(l2.getDate());
    }
}