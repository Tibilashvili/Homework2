import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class Result {


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

        Collections.sort(list, new Comparator<Map.Entry<String, Double>>() {
            @Override
            public int compare(Map.Entry<String, Double> o1, Map.Entry<String, Double> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        String FilePath = "src/results/first.json";
        write(list, FilePath);

    }


    public void allVilators(List<Ticket> ticketList) throws IOException {
        Map<String, MyTicket> TicketMap = new TreeMap<>();

        String fullName;


        for (Ticket ticket : ticketList) {
            fullName = ticket.getFirst_name() + " " + ticket.getLast_name();
            if (TicketMap.containsKey(fullName)) {
                MyTicket myTicket = TicketMap.get(fullName);
                myTicket.setName(fullName);
                myTicket.setCount(myTicket.getCount() + 1);
                myTicket.setPrice(myTicket.getPrice() + ticket.getFine_amount());
                myTicket.setAveragePrice(myTicket.getPrice() / myTicket.getCount());


            } else {
                MyTicket myTicket = new MyTicket();
                myTicket.setName(fullName);
                myTicket.setCount(1);
                myTicket.setPrice(ticket.getFine_amount());
                myTicket.setAveragePrice(ticket.getFine_amount());
                TicketMap.put(fullName, myTicket);

            }
        }

        List<Map.Entry<String, MyTicket>> myList = new ArrayList<>(TicketMap.entrySet());
         String FilePath = "src/results/second.json";
        write(myList, FilePath);


    }

    public void write(List list, String FilePath) throws IOException{

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        File file = new File(FilePath);
        FileWriter fileWriter = new FileWriter(file, false);
        objectMapper.writeValue(fileWriter, list);

    }
}
