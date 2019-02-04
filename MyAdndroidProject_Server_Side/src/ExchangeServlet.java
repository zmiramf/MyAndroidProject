import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class ExchangeServlet extends javax.servlet.http.HttpServlet {
    protected void doPost(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {

    }

    protected void doGet(javax.servlet.http.HttpServletRequest request, javax.servlet.http.HttpServletResponse response) throws javax.servlet.ServletException, IOException {
        String currencyFrom = request.getParameter("currencyFrom");
        String currencyTo = request.getParameter("currencyTo");
        double amount = Double.valueOf(request.getParameter("amount"));
        System.out.println(currencyFrom + " " + currencyTo + " " + amount);
        JSONObject jsonExchangeResult = new JSONObject();
        try{
            //calculate the result
            Double res = whatChoosed(currencyFrom, currencyTo, amount);
            if(res != null) {
                jsonExchangeResult.put("exchangeResult", res);
                System.out.println(res);
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
        response.setContentType("application/json");
        response.getWriter().write(jsonExchangeResult.toString());
    }

    public static Double whatChoosed (String currencyFrom, String currencyTo, double amount){
        Double exchangeResult = null;
        switch (currencyFrom){
            case "Dollar":
                exchangeResult = choosedDollar(currencyTo, amount);
                break;
            case "Israeli shekel":
                exchangeResult = choosedIsraeliShekel(currencyTo, amount);
                break;
            case "Euro":
                exchangeResult = choosedEuro(currencyTo, amount);
                break;
            case "Pound Sterling":
                exchangeResult = choosedPoundStterling(currencyTo, amount);
                break;
            case "Canadian dollar":
                exchangeResult = choosedCanadianDollar(currencyTo, amount);
                break;
            case "Thai baht":
                exchangeResult = choosedThaiBath(currencyTo, amount);
                break;
            case "Japanese Yen":
                exchangeResult = choosedJapaneseYen(currencyTo, amount);
                break;
            case "Russian Ruble":
                exchangeResult = choosedRussianRuble(currencyTo, amount);
                break;
        }
        return exchangeResult;
    }

    //dollar option
    public static Double choosedDollar (String currencyTo, double amount){
        Double result = null;
        switch (currencyTo){
            case "Dollar":
                result = amount;
                break;
            case "Israeli shekel":
                result = amount * 3.6377;
                break;
            case "Euro":
                result = amount * 0.8728;
                break;
            case "Pound Sterling":
                result = amount * 0.7639;
                break;
            case "Canadian dollar":
                result = amount * 1.3103;
                break;
            case "Thai baht":
                result = amount * 31.3037;
                break;
            case "Japanese Yen":
                result = amount * 109.495;
                break;
            case "Russian Ruble":
                result = amount * 65.479;
                break;
        }
        return result;
    }

    //israeli shekel option
    public static Double choosedIsraeliShekel (String currencyTo, double amount){
        Double result = null;
        switch (currencyTo){
            case "Dollar":
                result = amount * 0.2749;
                break;
            case "Israeli shekel":
                result = amount;
                break;
            case "Euro":
                result = amount * 0.2399;
                break;
            case "Pound Sterling":
                result = amount * 0.21;
                break;
            case "Canadian dollar":
                result = amount * 0.3602;
                break;
            case "Thai baht":
                result = amount * 8.6054;
                break;
            case "Japanese Yen":
                result = amount * 30.1004;
                break;
            case "Russian Ruble":
                result = amount * 18.0003;
                break;
        }
        return result;
    }

    //euro option
    public static Double choosedEuro (String currencyTo, double amount){
        Double result = null;
        switch (currencyTo){
            case "Dollar":
                result = amount * 1.1457;
                break;
            case "Israeli shekel":
                result = amount * 4.1678;
                break;
            case "Euro":
                result = amount;
                break;
            case "Pound Sterling":
                result = amount * 0.8752;
                break;
            case "Canadian dollar":
                result = amount * 1.5012;
                break;
            case "Thai baht":
                result = amount * 35.866;
                break;
            case "Japanese Yen":
                result = amount * 125.4533;
                break;
            case "Russian Ruble":
                result = amount * 75.0222;
                break;
        }
        return result;
    }

    //pound sterling option
    public static Double choosedPoundStterling (String currencyTo, double amount){
        Double result = null;
        switch (currencyTo){
            case "Dollar":
                result = amount * 1.3091;
                break;
            case "Israeli shekel":
                result = amount * 4.7621;
                break;
            case "Euro":
                result = amount * 1.1426;
                break;
            case "Pound Sterling":
                result = amount;
                break;
            case "Canadian dollar":
                result = amount * 1.7152;
                break;
            case "Thai baht":
                result = amount * 40.9795;
                break;
            case "Japanese Yen":
                result = amount * 143.3397;
                break;
            case "Russian Ruble":
                result = amount * 85.7184;
                break;
        }
        return result;
    }

    //canadian dollar
    public static Double choosedCanadianDollar (String currencyTo, double amount){
        Double result = null;
        switch (currencyTo){
            case "Dollar":
                result = amount * 0.7632;
                break;
            case "Israeli shekel":
                result = amount * 2.7763;
                break;
            case "Euro":
                result = amount * 0.6661;
                break;
            case "Pound Sterling":
                result = amount * 0.583;
                break;
            case "Canadian dollar":
                result = amount;
                break;
            case "Thai baht":
                result = amount * 23.8914;
                break;
            case "Japanese Yen":
                result = amount * 83.5681;
                break;
            case "Russian Ruble":
                result = amount * 49.9745;
                break;
        }
        return result;
    }


    //thai bath option
    public static Double choosedThaiBath (String currencyTo, double amount){
        Double result = null;
        switch (currencyTo){
            case "Dollar":
                result = amount * 0.0319;
                break;
            case "Israeli shekel":
                result = amount * 0.1162;
                break;
            case "Euro":
                result = amount * 0.0279;
                break;
            case "Pound Sterling":
                result = amount * 0.0244;
                break;
            case "Canadian dollar":
                result = amount * 0.0419;
                break;
            case "Thai baht":
                result = amount;
                break;
            case "Japanese Yen":
                result = amount * 3.4978;
                break;
            case "Russian Ruble":
                result = amount * 2.0917;
                break;
        }
        return result;
    }

    //Japanese yen option
    public static Double choosedJapaneseYen (String currencyTo, double amount){
        Double result = null;
        switch (currencyTo){
            case "Dollar":
                result = amount * 0.0091;
                break;
            case "Israeli shekel":
                result = amount * 0.0332;
                break;
            case "Euro":
                result = amount * 0.008;
                break;
            case "Pound Sterling":
                result = amount * 0.007;
                break;
            case "Canadian dollar":
                result = amount * 0.012;
                break;
            case "Thai baht":
                result = amount * 0.2859;
                break;
            case "Japanese Yen":
                result = amount;
                break;
            case "Russian Ruble":
                result = amount * 0.598;
                break;
        }
        return result;
    }


    //russian ruble option
    public static Double choosedRussianRuble (String currencyTo, double amount){
        Double result = null;
        switch (currencyTo){
            case "Dollar":
                result = amount * 0.0153;
                break;
            case "Israeli shekel":
                result = amount * 0.0556;
                break;
            case "Euro":
                result = amount * 0.0133;
                break;
            case "Pound Sterling":
                result = amount * 0.0117;
                break;
            case "Canadian dollar":
                result = amount * 0.02;
                break;
            case "Thai baht":
                result = amount * 0.4781;
                break;
            case "Japanese Yen":
                result = amount * 1.6722;
                break;
            case "Russian Ruble":
                result = amount;
                break;
        }
        return result;
    }
}


