public class Main {
	public static void main(String[] args) {

		if (args.length != 1) {
			System.exit(0);
		}
		if (args[0].equals("plain")) {
			TextBuilder textbuilder = new TextBuilder();
			Guide guide = new Guide(textbuilder);
			guide.construct();
			String result = textbuilder.getResult();
			System.out.println(result);
		} else if (args[0].equals("html")) {
			HTMLBuilder htmlbuilder = new HTMLBuilder();
			Guide guide = new Guide(htmlbuilder);
			guide.construct();
			String filename = htmlbuilder.getResult();
			System.out.println(filename + "が作成されました。");
		} else {
			System.exit(0);
		}
	}
}