public abstract class AbstractMonster {
	public String name;
	public abstract int getAttack();
	public abstract int getDefense();
	public final void showInfo() {
		System.out.print("名前：");
		System.out.println(name);
		System.out.print("攻撃力：");
		System.out.println(getAttack());
		System.out.print("守備力：");
		System.out.println(getDefense());
		System.out.println();
	}
}
