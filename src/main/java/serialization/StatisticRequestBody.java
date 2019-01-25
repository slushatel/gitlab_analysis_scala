package serialization;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class StatisticRequestBody {
    private String metric;
    private String repository;
    private String technology;
    private Date reportDate;
    private Float value;
}
