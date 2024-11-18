package singleton;

public class PrincipalMultiHilo {

	static class Hilo1 implements Runnable{

		@Override
		public void run() {
			Singleton singleton = Singleton.getInstance("Rojo");
			System.out.println("Singleton de color " + singleton.getColor());
		}
	}
	
	static class Hilo2 implements Runnable{

		@Override
		public void run() {
			Singleton singleton = Singleton.getInstance("Azul");
			System.out.println("Singleton de color " + singleton.getColor());
		}
	}
	
	public static void main(String[] args) {
		Thread hilo1 = new Thread(new Hilo1());
		Thread hilo2 = new Thread(new Hilo2());
		
		hilo1.start();
		//try {
			//hilo2.sleep(1000);
			hilo2.start();
		//} catch (InterruptedException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		

	}

}
