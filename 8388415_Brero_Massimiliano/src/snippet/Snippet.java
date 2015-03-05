package snippet;

public class Snippet {
	public static void main(String[] args) {

		// get a variable x which is equal to PI/2
		double x = 1;

		// get a variable y which is equal to PI/3
		double y = -1;


		// get the polar coordinates
		System.out.println("Math.atan2(" + x + "," + y + ")=" + Math.toDegrees(Math.atan2(y, x)));

	}
}
