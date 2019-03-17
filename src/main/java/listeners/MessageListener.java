package listeners;

import com.asprise.ocr.Ocr;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import utils.MathUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.net.URLConnection;

public class MessageListener extends ListenerAdapter {

    private Ocr ocr;

    public MessageListener(Ocr ocr) {
        this.ocr = ocr;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (!event.getGuild().getId().equals("164280494874165248")) return; //needs to be help chat
        if (!event.getChannel().getId().equals("553748967125549056")) return; //needs to be quickmaths channel
        if (!event.getAuthor().getId().equals("553767943301890063")) return; //needs to be harry

        MessageEmbed msg = event.getMessage().getEmbeds().get(0);

        if (msg == null) return;

        if (!msg.getFooter().getText().equalsIgnoreCase("try to solve this equation to win!"))
            return; //make sure it is the correct embed

        try {

            URL url = new URL(msg.getImage().getProxyUrl());
            URLConnection connection = url.openConnection();
            connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.75 Safari/537.36");
            BufferedImage image = ImageIO.read(connection.getInputStream());

            String equation = ocr.recognize(image, Ocr.RECOGNIZE_TYPE_TEXT, Ocr.OUTPUT_FORMAT_PLAINTEXT);
            System.out.println(equation);

            if (equation == null || equation.equals("")) return;

            double answer = MathUtil.evalExpression(equation);
            System.out.println(answer);

            event.getChannel().sendMessage(String.valueOf(answer)).queue();

        } catch (java.io.IOException ignored) {
        }
    }
}
