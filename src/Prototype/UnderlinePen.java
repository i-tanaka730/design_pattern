import framework.Product;

public class UnderlinePen implements Product {

	private char ulchar;

	public UnderlinePen(char ulchar) {
        this.ulchar = ulchar;
    }

	public void use(String s) {

		int length = s.getBytes().length;

		System.out.println(s);

        for (int i = 0; i < length; i++) {
            System.out.print(ulchar);
        }

        System.out.println("");
    }

	public Product createClone() {
        Product p = null;
        try {
            p = (Product)clone();
        } catch (CloneNotSupportedException e) {
        	// オブジェクトのクラスがCloneableインタフェースを実装していない場合にスローされる例外
            e.printStackTrace();
        }
        return p;
    }
}
