package driver;

public class Main {

	public static void main(String[] args) {
		String exp = "4^2+18/(9-3)";
		System.out.println(exp);
		TokenList list = ShuntngYard.ParseFromExp(exp);
		System.out.println(list);
		TokenList queue = ShuntngYard.BuildFromTokens(list);
		System.out.println(queue);
		String result = ShuntngYard.Process(queue);
		System.out.println(result);
	}

}
