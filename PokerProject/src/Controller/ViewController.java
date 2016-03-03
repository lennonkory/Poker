package Controller;

import GUI.View;
import Game.PlayerInfo;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author kory
 */
public class ViewController {
    
    private final Map<String,View> views;
    private int size;
    
    public ViewController(){
        views = new HashMap<>();
        size = 0;
    }
    
    public void addView(String key, View v){
        views.put(key, v);
        size++;
    }
    
    public void update(String key, String message){
        View v = views.get(key);
        v.updateView(message);
    }

    public void dealFlop(String key, String cards[]){
        View v = views.get(key);
        v.dealFlop(cards);
    }

    public void dealPreflop(String key, int start, List<Boolean> inhand){
        View v = views.get(key);
        v.dealCards(start, inhand);
    }

    public void dealTurn(String key, String card){
        View v = views.get(key);
        v.dealTurn(card);
    }

    public void dealRiver(String key, String card){
        View v = views.get(key);
        v.dealRiver(card);
    }

    public void updatePot(String key, int pot){
        View v = views.get(key);
        v.updatePot(pot);
    }
    
    public void clearCards(String key){
        View v = views.get(key);
        v.clearCards();
    }
    
    public void removeCards(String key, int id){
        View v = views.get(key);
        v.removeCards(id);
    }
    
    public void setPlayerInfo(String key, PlayerInfo info[]){    
        View v = views.get(key);
        v.updatePlayerList(info);        
    }
    
    public String getType(String key){
        View v = views.get(key);
        return v.getType();
    }
    
    public void update(String message){
        Collection<View> v = views.values();
        v.stream().forEach((vi)->{
            vi.updateView(message);
        });
    }

    public void dealFlop(String cards[]){
        views.values().stream().forEach((v)->{
            v.dealFlop(cards);
        });
    }

    public void dealPreflop(int start, List<Boolean> inhand){
        views.values().stream().forEach((v)->{
            v.dealCards(start, inhand);
        });
    }

    public void dealTurn(String card){
        views.values().stream().forEach((v)->{
            v.dealTurn(card);
        });
    }

    public void dealRiver(String card){
        views.values().stream().forEach((v)->{
            v.dealRiver(card);
        });
    }

    public void updatePot(int pot){
        views.values().stream().forEach((v)->{
            v.updatePot(pot);
        });
    }
    
    public void clearCards(){
        views.values().stream().forEach((v)->{
            v.clearCards();
        });
    }
    
    public void removeCards(int id){
        views.values().stream().forEach((v)->{
            v.removeCards(id);
        });
    }
    
    public String[] getType(){
        
        String types [] = new String[size];
        Collection<View> v = views.values();
        int i = 0;
        
        v.stream().forEach((vi)->{
            types[i] = vi.getType();
        });
        
        return types;
        
    }
              
    public void setPlayerInfo(PlayerInfo info[]){
        views.values().stream().forEach((v)->{
            v.updatePlayerList(info);
        });
    }

    public void setTable(){
        views.values().stream().forEach((v)->{
            System.out.println(v.getType());
            v.setTable();
        });
    }
    
    public void addPlayer(PlayerInfo info){
        views.values().stream().forEach((v)->{
            v.addPlayer(info);
        });
    }
    
}
