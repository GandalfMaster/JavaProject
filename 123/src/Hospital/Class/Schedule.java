package Hospital.Class;

import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule {
    public LocalDate getToday() {
        return today;
    }

    public void setToday(LocalDate today) {
        this.today = today;
    }

    private LocalDate today;

    private boolean morning;
    private LocalTime mstart;
    private LocalTime mend;
    private int mTotalNumber;
    private int mAppointNumber;

    private boolean afternoon;
    private LocalTime astart;
    private LocalTime aend;
    private int aTotalNumber;
    private int aAppointNumber;




}
