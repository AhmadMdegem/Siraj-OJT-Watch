import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

interface WatchState {
    public void buttonA(WatchStateContext ctx);
    public void buttonB();
    public void buttonC();
}

class WatchStateContext {
    private WatchState currentState;
    private WatchState[] States = { new TimeMode(), new DateMode(), new StoperMode(), new BrandMode() };
    public WatchStateContext() {
        currentState = States[0];
    }
    public void setState(WatchState state) {
        currentState = state;
    }
    public void buttonA() {
        currentState.buttonA(this);
    }
    public void buttonB() {
        currentState.buttonB();
    }
    public void buttonC() {
        currentState.buttonC();
    }
    public WatchState getState(int index) {
        return States[index];
    }
}


class TimeMode implements WatchState {
    private DateFormat dateFormat;
    public TimeMode() {
        dateFormat = new SimpleDateFormat("hh:mm a");
    }
    @Override
    public void buttonA(WatchStateContext ctx) {
        ctx.setState(ctx.getState(1));
    }
    @Override
    public void buttonB() {
        String dateString = dateFormat.format(new Date()).toString();
        System.out.println(dateString);
    }
    @Override
    public void buttonC() {
        System.out.println("Light");
    }
}
class DateMode implements WatchState {
    private DateFormat dateFormat;
    public DateMode() {
        dateFormat = new SimpleDateFormat("EEEE");
    }
    @Override
    public void buttonA(WatchStateContext ctx) {
        ctx.setState(ctx.getState(2));
    }
    @Override
    public void buttonB() {
        System.out.println(Calendar.getInstance().get(Calendar.YEAR));
    }
    @Override
    public void buttonC() {
        System.out.println(dateFormat.format(new Date()));
    }
}
class StoperMode implements WatchState {
    Scanner sc = new Scanner(System.in); // Create a Scanner object
    long startTime;
    long stopTime;
    long diffTime;

    public StoperMode() {
    }

    public void alert() {
        System.out.println("00:00:00");
    }

    public void Start() {
        startTime = System.currentTimeMillis();
    }

    public void Pause() {
        stopTime = System.currentTimeMillis();
        diffTime = stopTime - startTime + diffTime;
        System.out.print("Pause mode:  ");
        PrintTime();
    }

    public void Stop() {
        diffTime = 0;
    }

    public void PrintTime() {
        long seconds = (diffTime/1000)%60;
        long minute = (diffTime / (1000 * 60)) % 60;
        long hour = (diffTime / (1000 * 60 * 60)) % 24;
        String time = String.format("%02d:%02d:%02d", hour, minute, seconds);
        System.out.println(time);
    }
    public void Resume(){
        Start();
    }
    @Override
    public void buttonA(WatchStateContext ctx) {
        ctx.setState(ctx.getState(3));
    }
    @Override
    public void buttonB() {
        System.out.print("Initial Mode:  ");
        PrintTime();
        System.out.println("Please enter s to start");
        char value = sc.next().charAt(0);
        Start();
        while (true) {
            System.out.println("Please enter p to pause");
            value = sc.next().charAt(0);
            Pause();
            System.out.println("Please enter r to resume or e to exit");
            value = sc.next().charAt(0);
            if(value == 'e') {
                System.out.print("resume mode : ");
                PrintTime();
                return;
            }
            Resume();
        }
    }
    @Override
    public void buttonC() {
        Stop();
        System.out.print("reset mode : ");
        alert();
    }
}

class BrandMode implements WatchState {
    @Override
    public void buttonA(WatchStateContext ctx) {
        ctx.setState(ctx.getState(0));
    }
    @Override
    public void buttonB() {
        try {
            FileWriter Writer = new FileWriter("output.txt");
            Writer.write("The One s2!");
            Writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    @Override
    public void buttonC() {
    }
}

public class TheOneS2 {
    public static void main(String[] args) {
        WatchStateContext watch = new WatchStateContext();
        watch.buttonB();
        watch.buttonC();
        watch.buttonA();
        watch.buttonB();
        watch.buttonC();
        watch.buttonA();
        watch.buttonB();
        watch.buttonB();
    }
}
