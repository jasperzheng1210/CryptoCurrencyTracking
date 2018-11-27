package bitcoinTransaction;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Controller {
    BTCCurrency my_price;
    @FXML // fx:id="price_label";
            Label price_label;
    ScheduledExecutorService exec = Executors.newScheduledThreadPool(1);

    public Controller(){
        my_price = new BTCCurrency();
        Controller ctrl = this;
        exec.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                ctrl.refreshCurrency();
            }
        }, 0, 1, TimeUnit.MINUTES);
    }

    public void refreshCurrency(){
        System.out.println("Refreshing...");

        CompletableFuture<Double> future = new CompletableFuture<Double>();
        future.supplyAsync(()->{
            my_price.getCurrency();
            Platform.runLater(()->{
                price_label.setText(new Double(my_price.price).toString());
            });

            return 1.0;
        });
    }
}
