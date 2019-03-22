package game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

public class Gamer {

	private int money;
	private ArrayList<String> fruits = new ArrayList<String>();
	private Random random = new Random();
	private static String[] fruitsname = {
			"リンゴ", "ぶどう", "バナナ", "みかん",
	};

	public Gamer(int money) {
		this.money = money;
	}

	public int getMoney() {
		return money;
	}

	 // 賭ける…ゲームの進行
	public void bet() {
		// サイコロを振る
		int dice = random.nextInt(6) + 1;

		if (dice == 1) {
			// 1の目…所持金が増える
			money += 100;
			System.out.println("所持金が増えました。");
		} else if (dice == 2) {
			// 2の目…所持金が半分になる
			money /= 2;
			System.out.println("所持金が半分になりました。");
		} else if (dice == 6) {
			// 6の目…フルーツをもらう
			String f = getFruit();
			System.out.println("フルーツ(" + f + ")をもらいました。");
			fruits.add(f);
		} else {
			// それ以外…何も起きない
			System.out.println("何も起こりませんでした。");
		}
	}

	// スナップショットをとる
	public Memento createMemento() {
		Memento m = new Memento(money);
		Iterator it = fruits.iterator();
		while (it.hasNext()) {
			String f = (String) it.next();
			if (f.startsWith("おいしい")) {
				// フルーツはおいしいものだけ保存
				m.addFruit(f);
			}
		}
		return m;
	}

	// アンドゥを行う
	public void restoreMemento(Memento memento) {
		this.money = memento.money;
		this.fruits = memento.getFruits();
	}

	public String toString() {
		return "[money = " + money + ", fruits = " + fruits + "]";
	}

	private String getFruit() {
		String prefix = "";
		if (random.nextBoolean()) {
			prefix = "おいしい";
		}
		return prefix + fruitsname[random.nextInt(fruitsname.length)];
	}
}
