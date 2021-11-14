import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.Callable;


@NoArgsConstructor
@AllArgsConstructor
public class GetFiles implements Callable<List<Ticket>> {

    private File file;

       public static File[] getFileJSON() {
        File file = new File("src/traffic violations");
        return file.listFiles((dir, name) -> name.endsWith(".json"));
    }


    public List<Ticket> getList(File file) throws IOException {
        byte[] mapData;
        Ticket[] ticketsArr;
        List<Ticket> newTicketList;
        mapData = Files.readAllBytes(Paths.get(String.valueOf(file)));
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        ticketsArr = objectMapper.readValue(mapData, Ticket[].class);
        newTicketList = Arrays.asList(ticketsArr);
        return newTicketList;
        }


    @Override
    public List<Ticket> call() throws Exception {
        List<Ticket> ticketList = getList(file);
        return ticketList;
    }

}




