import server.resources.DeviceStream;

public class StreamProcesserApplication {

    public static void main(String[] args) {

        try {
            new DeviceStream().run(args);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
