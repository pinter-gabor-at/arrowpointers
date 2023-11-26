package eu.pintergabor.arrowpointers;

public class ArrowRegistry {

	private ArrowRegistry() {
		// static class
	}

	public static ArrowVariant arrowVariant;

	public static void init() {
		arrowVariant = new ArrowVariant();
		arrowVariant.register();
		arrowVariant.registerClient();
	}
}