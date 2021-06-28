public enum Exercise{

    BANDABDUCTEDGOBLETSQUAT("Band Abducted Goblet Squat"),
    BARBELLROW("Barbell Row"),
    BARBELLLUNGES("Barbell Lunges"),
    BARBELLSPLITSQUAT("Barbell Split Squat"),
    BENCHPRESS("Bench Press"),
    BSTANCEDBRDL("BStance DB RDL"),
    CABLECURLS("Cable Curls"),
    CABLELATPULLOVER("Cable Lat Pullover"),
    CABLEPULLTHROUGH("Cable Pull Through"),
    CABLETRICEPEXTENSION("Cable Tricep Extension"),
    CHESTSUPPORTEDROW("Chest Supported Row"),
    CLOSEGRIPBENCHPRESS("Close Grip Bench Press"),
    DBBENCHPRESS("DB Bench Press"),
    DBBICEPCURLS("DB Bicep Curls"),
    DBFLYES("DB Flyes"),
    DBHAMMERCURLS("DB Hammer Curls"),
    DBLUNGES("DB Lunges"),
    DBOVERHEADPRESS("DB Overhead Press"),
    DBROMANIANDEADLIFT("DB Romanian Deadlift"),
    DBROWS("DB Rows"),
    DBSTIFFLEGGEDDEADLIFT("DB Stiff Legged Deadlift"),
    DEADLIFT("Deadlift"),
    FRONTSQUAT("Front Squat"),
    FRONTPAUSESQUAT("Front Pause Squat"),
    GOBLETSQUAT("Goblet Squat"),
    HAMSTRINGCURLS("Hamstring Curls"),
    HEELELEVATEDGOBLETSQUAT("Heel Elevated Goblet Squat"),
    INCLINEBENCHPRESS("Incline Bench Press"),
    INCLINEDBBENCHPRESS("Incline DB Bench Press"),
    INCLINEDBFLYES("Incline DB Flyes"),
    LATPULLDOWN("Lat Pulldown"),
    LATERALRAISES("Lateral Raises"),
    LEGEXTENSIONS("Leg Extensions"),
    LEGPRESS("Leg Press"),
    NEUTRALGRIPLATPULLDOWN("Neutral Grip Lat Pulldown"),
    NEUTRALGRIPSEATEDROW("Neutral Grip Seated Row"),
    OHDBTRICEPEXTENSION("OH DB Tricep Extension"),
    OHGRIPSEATEDROW("OH Grip Seated Row"),
    OVERHEADPRESS("Overhead Press"),
    PAUSEBENCH("Pause Bench"),
    PAUSESQUAT("Pause Squat"),
    REVERSEGRIPLATPULLDOWN("Reverse Grip Lat Pulldown"),
    REVERSEHYPER("Reverse Hyper"),
    ROMANIANDEADLIFT("Romanian Deadlift"),
    RUSSIANTWIST("Russian Twist"),
    SPOTOPRESS("Spoto Press"),
    SQUAT("Squat"),
    STIFFLEGGEDDEADLIFT("Stiff Legged Deadlift"),
    THREECOUNTPAUSEBENCH("Three Count Pause Bench"),
    TWOCOUNTPAUSEBENCHPRESS("Two Count Pause Bench Press"),
    TWOCOUNTPAUSEDEADLIFT("Two Count Pause Deadlift"),
    TWOCOUNTPAUSESQUAT("Two Count Pause Squat"),
    WIDEGRIPLATPULLDOWN("Wide Grip Lat Pulldown"),
    WIDEGRIPSEATEDROW("Wide Grip Seated Row"),
    ZERCHERSQUAT("Zercher Squat")
    ;

    private final String name;

    Exercise(String name) {
        this.name = name;

    }

    @Override
    public String toString() {
        return name;
    }
}

