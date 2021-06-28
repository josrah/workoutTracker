public class Lift {
    private Exercise exercise;
    private int sets;
    private int reps;
    private double weight;
    private Date date;

    Lift(Date date, Exercise exercise, int sets, int reps, double weight){
        this.exercise = exercise;
        this.sets = sets;
        this.reps = reps;
        this.weight = weight;
        this.date = date;
    }

    Exercise getExercise() {
        return exercise;
    }

    int getSets() {
        return sets;
    }

    int getReps() {
        return reps;
    }

    double getWeight() {
        return weight;
    }

    Date getDate() {
        return date;
    }

    @Override
    public String toString(){
        return date + ", " + exercise + ", " + sets + "x" + reps + " @ " + weight + "kg" ;
    }
}
