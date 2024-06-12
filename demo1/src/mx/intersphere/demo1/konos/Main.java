package mx.intersphere.demo1.konos;

public class Main {
	public static void main(String[] args) {
		Demo1Box box = new Demo1Box(args);
		io.intino.magritte.framework.Graph graph = new io.intino.magritte.framework.Graph().loadStashes("Demo1");
		box.start();
		Runtime.getRuntime().addShutdownHook(new Thread(box::stop));
	}
}