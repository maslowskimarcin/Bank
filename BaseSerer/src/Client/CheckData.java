package Client;

public class CheckData
{

    private boolean textHasCapital(String text)
    {
        if(text.equals(text.toLowerCase()))
            return false;
        return true;
    }

    private boolean textHasLower(String text)
    {
        if(text.equals(text.toUpperCase()))
            return false;
        return true;
    }

    private boolean textHasSpecial(String text)
    {
        if (text.matches(".*[-!@#$,<.>/?;:'%^&*()`~}{|_=+].*") ||
            text.contains("]") ||
            text.contains("[") ||
            text.contains("\"") ||
            text.contains("\\"))
            return true;
        return false;
    }

    private boolean textHasNumber(String text)
    {
        if (text.matches(".*[0-9].*"))
            return true;
        return false;
    }

    private boolean textHasCharacter(String text)
    {
        if (text.matches(".*[a-zA-Z].*"))
            return true;
        return false;
    }

    public boolean checkPassword(String password)
    {
        if(password == null)
            return false;

        return !password.isEmpty() && textHasCapital(password) && !textHasSpecial(password) && textHasNumber(password);
    }

    public boolean checkIfOnlyNum(String text)
    {
        if(text == null)
            return false;

        return !text.isEmpty() && !textHasCharacter(text) && !textHasSpecial(text) && textHasNumber(text);
    }

    public boolean checkIfOnlyChars(String text)
    {
        if(text == null)
            return false;

        return !text.isEmpty() && textHasCharacter(text) && !textHasSpecial(text) && !textHasNumber(text);
    }

    public boolean checkCompanyName(String text)
    {
        if(text == null)
            return false;

        return  !text.isEmpty() &&
                !text.matches(".*[!@#$,<>/?;:'%^&*()`~}{|_=+].*") &&
                !text.contains("]")&&
                !text.contains("[") &&
                !text.contains("\"")&&
                !text.contains("\\");
    }

    public boolean checkTransferData(String accNoTo, String amount, String amountAfterComma, String transferTitle)
    {
        boolean isAccNoToCorrect;
        boolean isAmountCorrect;
        boolean isAmountAfterCommaCorrect;
        boolean isTransferTitleCorrect;

        if(accNoTo == null || amount == null || amountAfterComma == null || transferTitle == null)
            return false;

        isAccNoToCorrect = accNoTo.length() == 9  &&
                !textHasCharacter(accNoTo) &&
                !textHasSpecial(accNoTo);

        isAmountCorrect = !amount.isEmpty() &&
                !textHasCharacter(amount) &&
                !textHasSpecial(amount);

        isAmountAfterCommaCorrect = amountAfterComma.length() == 2 &&
                !textHasCharacter(amountAfterComma) &&
                !textHasSpecial(amountAfterComma);

        isTransferTitleCorrect = !transferTitle.isEmpty() &&
                !textHasSpecial(amount);

        return isAccNoToCorrect && isAmountAfterCommaCorrect && isAmountCorrect && isTransferTitleCorrect;
    }

    public boolean checkPersonalData(String name, String lastName, String pesel, String city, String street, String zipCode, String idNumber, String phoneNum, String email)
    {
        boolean isNameCorrect;
        boolean isLastNameCorrect;
        boolean isPeselCorrecct;
        boolean isCityCorrect;
        boolean isStreetCorrect;
        boolean isZipCodeCorrect;
        boolean isIDNumberCorrect;
        boolean isIDSeriesCorrect;
        boolean isPhoneNumCorrect;
        boolean isEmailCorrect;

        String idNumOnly;
        String idSeriesOnly;

        if(name == null || lastName == null || pesel == null || city == null || street == null || zipCode == null || idNumber == null || phoneNum == null || email == null)
            return false;

        isNameCorrect = !name.isEmpty() &&
                !textHasNumber(name) &&
                !textHasSpecial(name);

        isLastNameCorrect = !lastName.isEmpty() &&
                !textHasNumber(lastName) &&
                !textHasSpecial(lastName);

        if(pesel.length()==11)
        {
            isPeselCorrecct = !pesel.isEmpty() &&
                    !textHasSpecial(pesel) &&
                    !textHasCharacter(pesel);
        }
        else
            isPeselCorrecct = false;

        isCityCorrect = !city.isEmpty() &&
                !textHasNumber(city) &&
                !textHasSpecial(city);


        // has only "/"
        isStreetCorrect = !street.isEmpty() &&
                textHasNumber(street) &&
                !street.matches(".*[!@#$,<>?;:'%^&*()`~}{|_=+].*") &&
                !street.contains("]")&&
                !street.contains("[") &&
                !street.contains("\"")&&
                !street.contains("\\");

        // has only "-"
        isZipCodeCorrect = !zipCode.isEmpty() &&
                !(zipCode.matches(".*[!@#$,<.>/?;:'%^&*()`~}{|_=+].*")) &&
                !textHasCharacter(zipCode) &&
                zipCode.contains("-")&&
                !zipCode.contains("]")&&
                !zipCode.contains("[") &&
                !zipCode.contains("\"")&&
                !zipCode.contains("\\");

        if(idNumber.isEmpty() || !(idNumber.length() == 9))
        {
            isIDNumberCorrect = false;
            isIDSeriesCorrect = false;
        }
        else
        {   idSeriesOnly = idNumber.substring(0,3);
            idNumOnly = idNumber.substring(3);

            isIDNumberCorrect = !textHasCharacter(idNumOnly) &&
                    !textHasSpecial(idNumOnly);
            isIDSeriesCorrect = !textHasNumber(idSeriesOnly) &&
                    !textHasLower(idSeriesOnly);
        }

        isPhoneNumCorrect = !phoneNum.isEmpty() &&
                !textHasCharacter(phoneNum) &&
                !textHasSpecial(phoneNum);

        isEmailCorrect = !email.isEmpty() &&
                !email.matches(".*[!#$,<>/?;:'%^&*()`~}{|_=+].*") &&
                !email.contains("]")&&
                !email.contains("[") &&
                !email.contains("\"")&&
                !email.contains("\\")&&
                email.contains("@")&&
                email.contains(".");

        return isCityCorrect && isIDNumberCorrect && isIDSeriesCorrect && isLastNameCorrect && isNameCorrect && isPeselCorrecct && isPhoneNumCorrect && isStreetCorrect && isZipCodeCorrect && isEmailCorrect;
    }

}
