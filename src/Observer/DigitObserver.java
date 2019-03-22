public class DigitObserver implements Observer {

	public void update(NumberGenerator generator) {
		System.out.println("DigitObserver:" + generator.getNumber());
	}
}
