package bitcoinTransaction;
import com.google.gson.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

// Models the data

public class BTCCurrency {
    double price;

    private static String url = "https://min-api.cryptocompare.com/data/histominute?aggregate=0&e=CCCAGG&extraParams=CryptoCompare&fsym=BTC&limit=1&tryConversion=false&tsym=USD";

    public static String readFromAPI(){
        String contents = "";
        try {
            URL address = new URL(url);
            InputStreamReader reader = new InputStreamReader(address.openStream());
            BufferedReader buffer = new BufferedReader(reader);

            String line = "";
            while ((line = buffer.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }
                contents += line;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return contents;
    }

    public void getCurrency(){
        try {
            String from_api = readFromAPI();
            Gson gson = new Gson();

            JsonObject response = gson.fromJson(from_api, JsonObject.class);
            JsonArray data = response.getAsJsonArray("Data");

            for(JsonElement item : data ){
                JsonObject item_as_hashmap = item.getAsJsonObject();
                this.price = item_as_hashmap.get("close").getAsDouble();
            }

        }catch(Exception exc){
            exc.printStackTrace();
        }
    }
}