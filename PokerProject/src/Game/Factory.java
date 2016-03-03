package Game;

import Controller.LocalController;
import Controller.InputListener;
import Controller.PlayerListener;
import Controller.TableListener;
import Controller.ViewController;
import GUI.GUIView;
import GUI.View;
import GUI.TextView;
import java.util.List;
import java.util.Scanner;

/**
 * @author kory
 */
public class Factory {

    private Factory() {
    }

    public static LocalController createNewTextTexas() {

        Game game = new Texas();
        View view = new TextView();

        LocalController c = new LocalController(game, view);

        game.setTableListener(new TableListener() {

            @Override
            public void update(String message) {
                view.updateView(message);
            }

            @Override
            public void dealFlop(String[] cards) {
                view.dealFlop(cards);
            }

            @Override
            public void dealPreflop(int start, List<Boolean> inhand) {
                view.dealCards(start, inhand);
                c.updatePlayer();
            }

            @Override
            public void dealTurn(String card) {
                view.dealTurn(card);
            }

            @Override
            public void dealRiver(String card) {
                view.dealRiver(card);
            }

            @Override
            public void clearCards() {
                view.clearCards();
            }

            @Override
            public String getType() {
                return "TEXT";
            }

            @Override
            public void removeCards(int id) {
                view.removeCards(id);
            }

            @Override
            public void updatePot(int pot) {
                view.updatePot(pot);
            }

            @Override
            public void setPlayerInfo() {
            }
        });

        addPlayerView("Kory", 0, "text", game, view, c);
        addPlayerView("Bob", 1, "text", game, view, c);
        addPlayerView("Mike", 2, "text", game, view, c);

        return c;

    }

    public static LocalController createNewGUITexas() {

        Game game = new Texas();
        View view = new GUIView(9, 2);

        game.setTableListener(setTableListenerForGUI(view, game));

        addPlayerView("Kory", 0, "gui", game, view, null);
        addPlayerView("Bob", 1, "gui", game, view, null);
        addPlayerView("Mike", 2, "gui", game, view, null);
        addPlayerView("James", 5, "gui", game, view, null);

        LocalController c = new LocalController(game, view);

        view.addInputListener(new InputListener() {

            @Override
            public void input(String message) {
                c.command(message);
            }

            @Override
            public void input(String message, int bet) {
                System.out.println("NOT NULL");
                //c.command(message + " " + bet);
            }

            @Override
            public void notifyPlayer() {
                c.updatePlayer();
            }
        });

        view.setListener();

        return c;

    }

    public static TableListener setTableListenerForGUI(View view, Game game) {

        TableListener tl = new TableListener() {

            @Override
            public void update(String message) {
                view.updateView(message);
            }

            @Override
            public void dealFlop(String[] cards) {
                view.dealFlop(cards);
            }

            @Override
            public void dealPreflop(int start, List<Boolean> inhand) {
                view.dealCards(start, inhand);
            }

            @Override
            public void dealTurn(String card) {
                view.dealTurn(card);
            }

            @Override
            public void dealRiver(String card) {
                view.dealRiver(card);
            }

            @Override
            public void clearCards() {
                view.clearCards();
            }

            @Override
            public String getType() {
                return "GUI";
            }

            @Override
            public void removeCards(int id) {
                view.removeCards(id);
            }

            @Override
            public void updatePot(int pot) {
                view.updatePot(pot);
            }

            @Override
            public void setPlayerInfo() {

                PlayerInfo info[] = new PlayerInfo[game.players.size()];
                int i = 0;

                for (Player p : game.players) {
                    info[i] = p.getInfo();
                    i++;
                }

                view.updatePlayerList(info);

            }
        };

        return tl;
    }

    private static void addPlayerView(String name, int id, String viewType, Game game, View view, LocalController c) {

        Player p = new Player(name, id);

        PlayerListener vl = null;

        if (viewType.equals("text")) {

            vl = new PlayerListener() {

                @Override
                public void update(String message) {
                    System.out.println(message);
                }

                @Override
                public void updateButtons(int call, int raise) {
                    System.out.println("Call: " + call + " Bet: " + raise);
                }

                @Override
                public void viewCards(int id, String[] cards) {
                    System.out.print(p.name + ": ");
                    for (String c : cards) {
                        System.out.print(c + " ");
                    }
                    System.out.println();
                }

                @Override
                public void activatePlayer(PlayerInfo info) {
                    System.out.print(p.name + " Command: ");
                    c.command(new Scanner(System.in).nextLine());
                }

                @Override
                public void updatePlayer(PlayerInfo info) {
                }
            };
        } else {
            vl = new PlayerListener() {

                @Override
                public void update(String message) {
                    view.updateView(message);
                }

                @Override
                public void updateButtons(int call, int raise) {
                    view.updateButtons(call, call);
                }

                @Override
                public void viewCards(int id, String[] cards) {
                    view.viewCards(id, cards);
                }

                @Override
                public void activatePlayer(PlayerInfo info) {
                    view.updatePlayer(info);
                }

                @Override
                public void updatePlayer(PlayerInfo info) {
                    view.updatePlayerInfo(info);
                }
            };
        }

        p.setViewListener(vl);
        game.addPlayer(p);

    }

}
