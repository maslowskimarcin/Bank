package Base;

import java.io.Serializable;
import java.util.List;

public class TransferData implements Serializable{
    public String error;
    public List<Transfer> transferList;

    @Override
    public String toString() {
        return "TransferData{" +
                "error='" + error + '\'' +
                ", transferList=" + transferList +
                '}';
    }
}
