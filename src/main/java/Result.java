import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Result {


    // метод содержащий записи, описывающие общую сумма штрафа по каждому типу нарушений за все года, отсортированные по сумме.
    public void sumTicketAmountAllYears(List<Ticket> ticketList) throws IOException {
        Map<String, Double> mapTicketAmount = new TreeMap<>();
        double price;


        for (Ticket ticket : ticketList) {
            if (mapTicketAmount.containsKey(ticket.getType())) {

                price = mapTicketAmount.get(ticket.getType()) + ticket.getFine_amount();
                mapTicketAmount.put(ticket.getType(), price);
            } else {
                mapTicketAmount.put(ticket.getType(), ticket.getFine_amount());
            }
        }


        List list = new ArrayList<>(mapTicketAmount.entrySet());

        Collections.sort(list, (Comparator<Map.Entry<String, Double>>) (o1, o2) -> o1.getValue().compareTo(o2.getValue()));

        String filePath = "src/results/first.json";
        write(list, filePath);

    }


    // метод содержащий список нарушителей за все годы: по каждому нарушителю видно общее количество нарушений, суммарный штраф и средняя сумма штрафа.
    public void allVilators(List<Ticket> ticketList) throws IOException {
        Map<String, MyTicket> ticketMap = new TreeMap<>();

        String fullName;


        for (Ticket ticket : ticketList) {
            fullName = ticket.getFirst_name() + " " + ticket.getLast_name();
            if (ticketMap.containsKey(fullName)) {
                MyTicket myTicket = ticketMap.get(fullName);
                myTicket.setName(fullName);
                myTicket.setCount(myTicket.getCount() + 1);
                myTicket.setPrice(myTicket.getPrice() + ticket.getFine_amount());
                myTicket.setAveragePrice(myTicket.getPrice() / myTicket.getCount());


            } else {
                MyTicket myTicket = new MyTicket();
                myTicket.setCount(1);
                myTicket.setPrice(ticket.getFine_amount());
                myTicket.setAveragePrice(ticket.getFine_amount());
                ticketMap.put(fullName, myTicket);

            }
        }

        List<Map.Entry<String, MyTicket>> myList = new ArrayList<>(ticketMap.entrySet());
        String filePath = "src/results/second.json";
        write(myList, filePath);


    }

    // метод записывающий результат другого метода в json-файл
    public void write(List list, String filePath) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        File file = new File(filePath);
        FileWriter fileWriter = new FileWriter(file, false);
        objectMapper.writeValue(fileWriter, list);

    }
}
