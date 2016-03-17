package ru.leonid.messageSystem;

import ru.leonid.base.Address;
import ru.leonid.base.AddressService;

public class AddressServiceImpl implements AddressService {
    private Address addressFrontend;
    private Address addressDB;
    private Address addressGM;

    public Address getAddressDB() {
            return addressDB;
    }
    public void setAddressDB(Address addressAS) {
            this.addressDB = addressAS;
    }
    public Address getAddressFrontend() {
            return addressFrontend;
    }
    public void setAddressFrontend(Address addressFrontend) {
            this.addressFrontend = addressFrontend;
    }
    public Address getAddressGM() {
       return addressGM;
    }   
    public void setAddressGM(Address addressGM){
        this.addressGM = addressGM;
    }
}
