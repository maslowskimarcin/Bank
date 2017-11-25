public class Temp {

    public Object logto(){
        LogTo temp=new LogTo();
        temp.login="";
        temp.password="k2";
        return temp;
    }

    public Object addNewAccountRequest(){
        PersonalData personal=new PersonalData();
        personal.firstName="Jurek";
        personal.lastName="Owsiak";
        personal.pesel="95020658911";
        personal.city="Kraków";
        personal.street="łąkowa 4/32";
        personal.zipCode="30-022";
        personal.idNumber="AYD231986";
        personal.phoneNumber="238123675";
        personal.email="kn@gmail.com";
        return personal;
    }
}
