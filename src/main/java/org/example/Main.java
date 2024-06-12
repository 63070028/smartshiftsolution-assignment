package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        BufferedReader reader;
        double profit = 0;
        Map<String,LinkedList<Coin>> pockets = new HashMap<>();

        try {
            reader = new BufferedReader(new FileReader("crypto_tax.txt"));
            String line = reader.readLine();
            while (line != null) {
//                System.out.println(line);
                // read next line
                String[] data = line.split(" ");
                String action = data[0];
                String name = data[1];
                double price = Double.parseDouble(data[2]);
                double qty = Double.parseDouble(data[3]);



                if(action.equals("B")){

                    if(!pockets.containsKey(name)){
                        LinkedList<Coin> coins = new LinkedList<>();
                        coins.add(new Coin(price, qty));
                        pockets.put(name, coins);

                    }else{
                        pockets.get(name).add(new Coin(price, qty));

                    }

                }else if(action.equals("S")){

                    if(!pockets.containsKey(name) || pockets.get(name).isEmpty()){
                        System.out.println("Error");
                        break;
                    }else{
                        while(qty > 0){
                            Coin target = pockets.get(name).getFirst();

                            if(target.qty <= qty){
                                if(target.price <= price){
                                    profit += (price - target.price) * target.qty;
                                }else{
                                    profit -= (target.price - price) * target.qty;
                                }
                                 qty -= target.qty;
                                 pockets.get(name).removeFirst();
                            }else{
                                if(target.price <= price){
                                    profit += (price - target.price) * qty;
                                }else{
                                    profit -= (target.price - price) * qty;
                                }
                                target.qty -= qty;
                                qty = 0;
                            }
                        }

                    }

                }else{
                    System.out.println("Error");
                    break;
                }

                System.out.printf("Profit: %s\n", profit);

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}



/*

      price:qty

      pockets = [
        BTC : [100:2, 200:3],
        ETH : []
      ];
*/