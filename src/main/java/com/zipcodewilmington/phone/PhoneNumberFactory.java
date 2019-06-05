package com.zipcodewilmington.phone;

import com.zipcodewilmington.exceptions.InvalidPhoneNumberFormatException;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by leon on 5/1/17.
 */
public final class PhoneNumberFactory {
    private static final Logger logger = Logger.getGlobal();

    private PhoneNumberFactory() {
        /** This constructor is private
         *  This class is uninstantiable */
    }

    /**
     * @param phoneNumberCount - number of PhoneNumber objects to instantiate
     * @return array of randomly generated PhoneNumber objects
     */
    public static PhoneNumber[] createRandomPhoneNumberArray(int phoneNumberCount) {
        ArrayList<PhoneNumber> numberz = new ArrayList<>();
        for(int i = 0; i < phoneNumberCount; i++){
            numberz.add(createRandomPhoneNumber());
        }
        return numberz.toArray(new PhoneNumber[numberz.size()]);
    }

    /**
     * @return an instance of PhoneNumber with randomly generated phone number value
     */
    public static PhoneNumber createRandomPhoneNumber() {
        Random r = new Random();
        String area = "", office = "", line = "";
        for(int i = 0; i < 4; i++){
            line += r.nextInt(9) + 1;
            if (i == 3) break;
            office += r.nextInt(9) + 1;
            area += r.nextInt(9) + 1;
        }
        return createPhoneNumberSafely(Integer.parseInt(area), Integer.parseInt(office), Integer.parseInt(line));
    }


    /**
     * @param areaCode          - 3 digit code
     * @param centralOfficeCode - 3 digit code
     * @param phoneLineCode     - 4 digit code
     * @return a new phone number object
     */
    public static PhoneNumber createPhoneNumberSafely(int areaCode, int centralOfficeCode, int phoneLineCode) {
        String phoneNumberString = "";
        phoneNumberString = "(" + areaCode + ")-" + centralOfficeCode + "-" + phoneLineCode;
        try{
            return createPhoneNumber(phoneNumberString);
        } catch (InvalidPhoneNumberFormatException xcep){
            logger.log(Level.FINE, phoneNumberString + " is not a valid phone number");
            return null;
        }
    }

    /**
     * @param phoneNumberString - some String corresponding to a phone number whose format is `(###)-###-####`
     * @return a new phone number object
     * @throws InvalidPhoneNumberFormatException - thrown if phoneNumberString does not match acceptable format
     */
    public static PhoneNumber createPhoneNumber(String phoneNumberString) throws InvalidPhoneNumberFormatException{
        try {
            logger.log(Level.FINE, "Attempting to create a new PhoneNumber object with a value of " + phoneNumberString);
            return new PhoneNumber(phoneNumberString);
        } catch (InvalidPhoneNumberFormatException xcep) {
            throw new InvalidPhoneNumberFormatException();
        }
    }
}
