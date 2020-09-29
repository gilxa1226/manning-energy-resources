import server.GridServer;

public class GridServerApplication {

  public static void main(String[] args) {

    try {
      new GridServer().run(args);
    } catch (Exception e) {
      e.printStackTrace();
    }

  }
}
