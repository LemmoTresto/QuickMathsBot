package listeners;

import com.asprise.ocr.Ocr;
import net.dv8tion.jda.core.events.ShutdownEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ShutdownListener extends ListenerAdapter {

    private Ocr ocr;

    public ShutdownListener(Ocr ocr) {
        this.ocr = ocr;
    }

    @Override
    public void onShutdown(ShutdownEvent event) {
        ocr.stopEngine();
    }
}
