package utils;

import java.net.SocketAddress;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Player {
    private SocketAddress socketAddress;
    private String name;

    public Player(SocketAddress socketAddress, String request) {
        this.socketAddress = socketAddress;
        this.name = processName(request);
    }

    public String processName(String request) {
        Pattern pattern = Pattern.compile(":(\\S+)");
        Matcher matcher = pattern.matcher(request);

        if (matcher.find()) {
            return matcher.group(1);
        }
        return "unknown";
    }

    public SocketAddress getSocketAddress() {
        return socketAddress;
    }

    public void setSocketAddress(SocketAddress socketAddress) {
        this.socketAddress = socketAddress;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
