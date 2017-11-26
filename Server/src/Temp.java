public class Temp {

    public Object logto(){
        LogTo temp=new LogTo();
        temp.login="";
        temp.password="k2";
        return temp;
    }

    public Object addNewAccountRequest(){
        PersonalData personal=new PersonalData();
        personal.firstName="Mariola";
        personal.lastName="Półka";
        personal.pesel="95020658914";
        personal.city="Kraków";
        personal.street="ulica 4/32";
        personal.zipCode="30-022";
        personal.idNumber="AYD231986";
        personal.phoneNumber="123456987";
        personal.email="kn@gmail.com";
        return personal;
    }

    public Object decisionAddAcc(){
        AddAccReqDecision req=new AddAccReqDecision();
        req.decision="y";
        req.id_req="1";
        return req;
    }
}
