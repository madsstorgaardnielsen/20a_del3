package model;
import model.*;
import view.*;
import controller.*;
public class PropertyField extends Field {
    public int price;
    public boolean owned;
    public Player ownedBy;

    public PropertyField(String name, int price, boolean owned, Player ownedBy, boolean goToJail) {
        super(name, goToJail);
        this.price = price;
        this.owned = owned;
        this.ownedBy = ownedBy;
    }

    @Override
    public Field landOnField(Player player) {
        if (goToJail){
            player.setPlayerGoToJail(true);
        }
        if (!owned) {
            setFieldOwner(player);
            payForProperty(player);
        } else {
            payRent(player);
        }
        return this;
    }

    public void payRent(Player player) {
        player.account.setBalance(player.account.getBalance() - price);
        ownedBy.account.setBalance(ownedBy.account.getBalance() + price);
    }

    public void setFieldOwner(Player player) {
        ownedBy = player;
        owned = true;
    }

    public Player getOwnedBy(Player player) {
        return ownedBy;
    }

    public void payForProperty(Player player) {
        player.account.setBalance(player.account.getBalance() - price);
    }

    public void sellField(Player player) {
        if (ownedBy == player) {
            player.account.getPaid(price);
            owned = false;
        }
    }

    public boolean isOwned() {
        return owned;
    }

    public int getPrice() {
        return price;
    }
}
