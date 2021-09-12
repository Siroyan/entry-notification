package net.siroyan.dev.mc.plugin.entrynotification;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import org.bukkit.entity.Player;

public class DiscordGateway {
    private String discordWebhookUrl;

    public DiscordGateway(String discordWebhookUrl) {
        this.discordWebhookUrl = discordWebhookUrl;
    }

    public String getDiscordWebhookUrl() {
        return discordWebhookUrl;
    }

    public void setDiscordWebhookUrl(String discordWebhookUrl) {
        this.discordWebhookUrl = discordWebhookUrl;
    }

    public String postJoinMessage(Player p) throws IOException {
        String uuid = p.getUniqueId().toString();
        String json = "{ \"username\": \"" + p.getName() + "\" ,\"avatar_url\": \"" + "https://crafatar.com/avatars/" + uuid + "\" ,\"content\": \"サーバーに入りました！\" }";
        return sendMsgToDiscord(json);
    }

    public String postCommandMessage(Player p, String msg) throws IOException {
        String uuid = p.getUniqueId().toString();
        String json = "{ \"username\": \"" + p.getName() + "\" ,\"avatar_url\": \"" + "https://crafatar.com/avatars/" + uuid + "\" ,\"content\": \"" + msg + "\" }";
        return sendMsgToDiscord(json);
    }

    private String sendMsgToDiscord(String msg) throws IOException {
        HttpURLConnection con = null;
        StringBuffer result = new StringBuffer();
        try {
            URL url = new URL(this.discordWebhookUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setDoOutput(true);
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", "Android");
            con.setRequestProperty("Accept-Language", "jp");
            // データがJSONであること、エンコードを指定する
            con.setRequestProperty("Content-Type", "application/JSON; charset=utf-8");
            // POSTデータの長さを設定
            con.setRequestProperty("Content-Length", String.valueOf(msg.length()));
            // リクエストのbodyにJSON文字列を書き込む
            OutputStreamWriter out = new OutputStreamWriter(con.getOutputStream());
            out.write(msg);
            out.flush();
            con.connect();

            // HTTPレスポンスコード
            final int status = con.getResponseCode();
            if (status == HttpURLConnection.HTTP_OK) {
                final InputStream in = con.getInputStream();
                String encoding = con.getContentEncoding();
                if (null == encoding) {
                    encoding = "UTF-8";
                }
                final InputStreamReader inReader = new InputStreamReader(in, encoding);
                final BufferedReader bufReader = new BufferedReader(inReader);
                String line = null;
                while ((line = bufReader.readLine()) != null) {
                    result.append(line);
                }
                bufReader.close();
                inReader.close();
                in.close();
            } else {
                System.out.println(status);
            }

        } catch (Exception e1) {
            e1.printStackTrace();
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return result.toString();
    }
}
