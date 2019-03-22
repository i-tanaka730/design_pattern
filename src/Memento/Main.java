import game.Gamer;
import game.Memento;

public class Main {

	public static void main(String[] args) {
		// 最初の所持金は100
		Gamer gamer = new Gamer(100);
		// 最初の状態を保存しておく
		Memento memento = gamer.createMemento();

		for (int i = 0; i < 10; i++) {
			System.out.println("==== " + i);
			System.out.println("現状:" + gamer);

			// ゲームを進める
			gamer.bet();

			System.out.println("所持金は" + gamer.getMoney() + "円になりました。");

			if (gamer.getMoney() > memento.getMoney()) {
				System.out.println("    （だいぶ増えたので、現在の状態を保存しておこう）");
				memento = gamer.createMemento();
			} else if (gamer.getMoney() < memento.getMoney() / 2) {
				System.out.println("    （だいぶ減ったので、以前の状態に復帰しよう）");
				gamer.restoreMemento(memento);
			}
		}
	}
}
