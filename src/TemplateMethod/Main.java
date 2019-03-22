public class Main {
    public static void main(String[] args) {
    	AbstractMonster slime = new Slime("スライムくん");
    	AbstractMonster dragon = new Dragon("ドラゴンさん");
    	slime.showInfo();
    	dragon.showInfo();
    }
}
