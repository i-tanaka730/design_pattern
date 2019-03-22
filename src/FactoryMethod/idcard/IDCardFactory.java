package idcard;

import java.util.ArrayList;

import framework.Factory;
import framework.Product;

public class IDCardFactory extends Factory {

	private ArrayList<String> owners = new ArrayList<String>();

	protected Product createProduct(String owner) {
		return new IDCard(owner);
	}

	protected void registerProduct(Product product) {
		IDCard icCard = (IDCard) product;
		String owner = icCard.getOwner();
		owners.add(owner);
	}

	public ArrayList<String> getOwners() {
		return owners;
	}
}
