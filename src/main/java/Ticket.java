import lombok.*;
import java.util.Date;

@Getter
@Setter
public class Ticket {
    private Date date_time;
    private String first_name;
    private String last_name;
    private  String type;
    private double fine_amount;

}
