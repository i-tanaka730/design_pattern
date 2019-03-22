package factory;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

public abstract class Page {
	protected String title;
	protected ArrayList content = new ArrayList();

	public Page(String title) {
		this.title = title;
	}

	public void add(Item item) {
		content.add(item);
	}

	public void output() {
		try {
			String filename = title + ".html";
			Writer writer = new FileWriter(filename);
			writer.write(this.makeHTML());
			writer.close();
			System.out.println(filename + " を作成しました。");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract String makeHTML();
}
