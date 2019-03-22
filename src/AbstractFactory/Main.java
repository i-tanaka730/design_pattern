import factory.Factory;
import factory.Link;
import factory.Page;
import factory.Tray;

public class Main {
	public static void main(String[] args) {

		Factory factory = Factory.getFactory("listfactory.ListFactory");

		Link qiita = factory.createLink("Qiita", "https://qiita.com//");
		Link dot = factory.createLink("ドットインストール", "https://dotinstall.com/");

		Link yahoo = factory.createLink("Yahoo!Japan", "http://www.yahoo.co.jp/");
		Link excite = factory.createLink("Excite", "http://www.excite.com/");
		Link google = factory.createLink("Google", "http://www.google.com/");

		Tray pgTray = factory.createTray("プログラミング");
		pgTray.add(qiita);
		pgTray.add(dot);

		Tray searchTray = factory.createTray("検索サイト");
		searchTray.add(yahoo);
		searchTray.add(excite);
		searchTray.add(google);

		Page page = factory.createPage("お気に入り");
		page.add(pgTray);
		page.add(searchTray);
		page.output();
	}
}
