package listfactory;

import factory.Factory;
import factory.Link;
import factory.Page;
import factory.Tray;

public class ListFactory extends Factory {
	public Link createLink(String caption, String url) {
		return new ListLink(caption, url);
	}

	public Tray createTray(String caption) {
		return new ListTray(caption);
	}

	public Page createPage(String title) {
		return new ListPage(title);
	}
}
