package utils;

public class RunLuaServer {
    public static void run() {
        new Thread(
                () -> {
                    String luaServerFile = "luacpp.exe";
                    try {
                        ProcessBuilder pb = new ProcessBuilder(luaServerFile);
                        pb.inheritIO();
                        Process process = pb.start();
                        process.waitFor();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }).start();

    }
}
