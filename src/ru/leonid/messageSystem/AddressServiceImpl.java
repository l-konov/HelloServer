package ru.leonid.messageSystem;

import ru.leonid.base.Address;
import ru.leonid.base.AddressService;

public class AddressServiceImpl implements AddressService {
	private Address addressFrontend;
	private Address addressAS;
	
	public Address getAddressAS() {
		return addressAS;
	}
	public void setAddressAS(Address addressAS) {
		this.addressAS = addressAS;
	}
	public Address getAddressFrontend() {
		return addressFrontend;
	}
	public void setAddressFrontend(Address addressFrontend) {
		this.addressFrontend = addressFrontend;
	}
}
