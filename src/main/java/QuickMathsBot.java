import com.asprise.ocr.Ocr;
import listeners.MessageListener;
import listeners.ShutdownListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDABuilder;

import javax.security.auth.login.LoginException;

public class QuickMathsBot {

    public static void main(String[] args) {
        Ocr.setUp();
        Ocr ocr = new Ocr();
        ocr.startEngine("eng", Ocr.SPEED_SLOW);
        try {
            new JDABuilder(AccountType.CLIENT)
                    .setToken(args[0])
                    .addEventListener(new MessageListener(ocr))
                    .addEventListener(new ShutdownListener(ocr))
                    .build();
        } catch (LoginException e) {
            e.printStackTrace();
            ocr.stopEngine();
        }
    }
}
