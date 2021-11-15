import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException{
        Result results = new Result();

        File[] fileArray = JsonFileReader.getFileJSON();

        ExecutorService executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

       List<Ticket> result = new ArrayList<>();

        for (File file : fileArray) {
            Future<List<Ticket>> future = executor.submit(new JsonFileReader(file));
            result.addAll(future.get());
        }

        executor.shutdown();
        results.sumTicketAmountAllYears(result);
        results.allVilators(result);

    }
}






