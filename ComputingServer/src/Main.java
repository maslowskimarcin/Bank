
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
    public static void main(String[] args) throws SQLException {

        Temp temp = new Temp();
//        LogTo logto = (LogTo) temp.logto(); //object from base serve
//        ConnectDB connectDB = new ConnectDB(logto);  //create connection
//        Object finish = connectDB.getObject(); //result
//        LogFrom logFrom = (LogFrom) finish;  //display res
//        System.out.println(logFrom); //display res

        PersonalData personalData=(PersonalData)temp.addNewAccountRequest();
        ConnectDB connectDB=new ConnectDB(personalData);
        Object finish = connectDB.getObject(); //result
        String err = (String ) finish;  //display res
        System.out.println(err); //display res
    }

    public static void age() throws ParseException {
        String pesel="1611179345";

        String year=pesel.substring(0,2);
        String month=pesel.substring(2,4);
        String day=pesel.substring(4,6);

        Integer yearP=Integer.parseInt(year);
        Integer monthP=Integer.parseInt(month);
        Integer dayP=Integer.parseInt(day);

        System.out.println(yearP);
        System.out.println(monthP);

        String birthDate="";
        if (yearP>=0 && monthP>20 && dayP<=31){ //someone born after 2000
            birthDate="20"+year+"/"+(monthP-20)+"/"+day;
            // System.out.println(birthDate);
        }else if(yearP>=17 && monthP<20 && dayP<=31){
            birthDate="19"+year+"/"+(monthP)+"/"+day;
            //   System.out.println(birthDate);
        }else {
            System.out.println("za stary;");
        }


        DateFormat format = new SimpleDateFormat("yyyy/MM/dd");
        Date dateb = format.parse(birthDate);
        System.out.println(dateb);

        // DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        System.out.println(format.format(date));

        long diff=date.getTime()-dateb.getTime();
        long d=(1000l*60*60*24*365);
        long years = Math.round(diff / d);
        int age=(int) years;
        System.out.println("AGE "+age);}



    public static void transfer(){
        List<String> list=new ArrayList<>() ;
        list.add("przelew"); //what
        list.add("3"); //from
        list.add("11111h111"); //to
        list.add("20.00"); //amount
    }
}
