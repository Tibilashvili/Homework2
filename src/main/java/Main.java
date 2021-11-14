import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.*;


public class Main {
    public static void main(String[] args) throws IOException, InterruptedException, ExecutionException {
        GetFiles getFiles;
        Result results = new Result();

        File[] fileArray = GetFiles.getFileJSON();

        ExecutorService executor = Executors.newFixedThreadPool(fileArray.length);
        List<Ticket> result = new ArrayList<>();

        for (File file : fileArray) {
            getFiles = new GetFiles(file);
            Callable<List<Ticket>> callable = getFiles;
            result.addAll(executor.submit(callable).get());

        }
        executor.shutdown();
        results.sumTicketAmountAllYears(result);
        results.allVilators(result);

    }

}






